package me.vaziak.sensation.client.impl.combat;

import java.util.*;

import org.lwjgl.input.Keyboard;

import me.vaziak.sensation.Sensation;
import net.minecraft.entity.*;
import me.vaziak.sensation.utils.*;
import me.vaziak.sensation.utils.client.ChatUtils;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import me.vaziak.sensation.utils.math.*;
import me.vaziak.sensation.utils.math.Vector;
import me.vaziak.sensation.client.api.*;
import me.vaziak.sensation.client.api.property.impl.*;
import me.vaziak.sensation.client.api.event.events.*;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.potion.Potion;
import net.minecraft.util.*;
import optfine.Reflector;

public class KillAura extends Module {
    public StringsProperty mode = new StringsProperty("Mode", "The killaura mode", null, false, true, new String[] {"Switch", "Multi"});
	public BooleanProperty targetOptions = new BooleanProperty("Target Options", "Opens up options to use for targeting",false);
    public StringsProperty targetPriority = new StringsProperty("Priority", "How the priority target will be selected.", () -> targetOptions.getValue(), false, true, new String[]{"Highest Health", "Lowest Health", "Most Armor", "Least Armor", "Furthest", "Closest"});
	public StringsProperty targetEntities = new StringsProperty("Targets", "The entites that will be targetted.", () -> targetOptions.getValue(), true, false, new String[]{"Players", "Monsters", "Animals", "Villagers", "Golems"});
	public StringsProperty targetEntityChecks = new StringsProperty("Checks", "The checks that will be performed on every potential mc.thePlayer.", () -> targetOptions.getValue(), true, false, new String[]{"Distance", "Dead", "0/Negative HP"});
	public BooleanProperty prioritisePlayers = new BooleanProperty("Prioritize Players", "Always hit players before monsters.", () -> targetEntities.getValue().get("Players") && targetOptions.getValue() && targetEntities.getValue().size() > 2, false);
	public BooleanProperty miAmigos = new BooleanProperty("Friends", "Don't atack Rachel and Joey", false);
	public BooleanProperty rotationOptions = new BooleanProperty("Rotation Options", "Change the way the killaura rotates",false);
    public StringsProperty aimMode = new StringsProperty("Aim Mode", "Killaura Aim Mode", () -> rotationOptions.getValue(), false, true, new String[] {"Normal", "Smooth", "Adjustion", "Developer-Cancer", "No Aim"});
    public BooleanProperty deviate = new BooleanProperty("Deviate", "Deviate away from the center of the entity (Avoid aiming at the middle)", ()-> rotationOptions.getValue(), false);
    public DoubleProperty deviateTicks = new DoubleProperty("Deviation Ticks", "Deviate away every (50ms * setting), delays the time between aiming away", ()-> rotationOptions.getValue() && deviate.getValue(), 6, 2, 20, 1, "Ticks");
    public BooleanProperty silentRotate = new BooleanProperty("Force Rotatons", "Force client side yaw and pitch to align with fake rotations", ()-> !aimMode.getValue().get("No Aim") && rotationOptions.getValue(), false);
    public BooleanProperty hitbox = new BooleanProperty("Hitbox", "Only hit when aiming over the entity", ()-> !aimMode.getValue().get("No Aim") && rotationOptions.getValue(), false);
    public BooleanProperty mxz = new BooleanProperty("MotionXZ Bypass", "Bypass shitty MXZ checks - Fuck you Itz_Lucky", ()-> !aimMode.getValue().get("No Aim") && rotationOptions.getValue(), false);
    public DoubleProperty smoothing = new DoubleProperty("Smoothing", "The amount the aura will be smoothed", () -> aimMode.getValue().get("Smooth") || aimMode.getValue().get("Adjustion"), 10, 1, 100, 1, null);
    public StringsProperty autoBlockMode = new StringsProperty("AutoBlock Mode", "The autoblock mode", null, false, true, new String[] {"NCP", "Falcon", "None"});
    public DoubleProperty reach = new DoubleProperty("Attack Distance", "The distance you will attack an entity at", null, 4.0, 3.0, 5, 0.01, null);
    public DoubleProperty distance = new DoubleProperty("Target Distance", "The distance you will target an entity at", null, 4.0, 3.0, 10, 0.01, null);
    public BooleanProperty noSwing = new BooleanProperty("No Swing", "Don't swing or send swing packets", false);
    public DoubleProperty minValue = new DoubleProperty("Minimum CPS", "Minimum cps the aura will click", null, 10, 1.0, 20, 1, null);
    public DoubleProperty maxValue = new DoubleProperty("Maximum CPS", "Maximum cps the aura will click", null, 10, 1.0, 20, 1, null);
	public ArrayList<Float> recordedYawRandoms;
	public ArrayList<Float> recordedPitchRandoms;
	public ArrayList<EntityLivingBase> targetList;
    public TimerUtil clickStopwatch = new TimerUtil();
    public float lastYaw, lastPitch;
    public AngleUtility angleUtility;
    public boolean blocking, animation;
	public Entity lastAimedTarget;
	public EntityLivingBase target;
	public boolean decreasingarea;
	public int targetedarea;
	public PatternUtil autoblockXZPattern;
	public PatternUtil autoblockYPattern;
	public int targetIndex;
	public long time;
	public boolean groundTicks;
	public float currentYaw;
	public float currentPitch;
	public int attacks;
	public int current;
	public boolean resetting;
	public boolean recording;
	public boolean doneRecording;
	public float properYaw;
	public float properPitch;
	public int delay;
	public int blockTime;
	public float value;
	public boolean up;

    public KillAura() {
        super("Kill Aura", Category.COMBAT);
        registerValue(mode, targetOptions,targetPriority, targetEntities, targetEntityChecks, prioritisePlayers, miAmigos, rotationOptions, silentRotate, aimMode, deviate, deviateTicks, smoothing, hitbox, mxz, autoBlockMode, noSwing, reach, distance, minValue, maxValue);
        angleUtility = new AngleUtility(60, 250, 40, 200);
    	autoblockXZPattern = new PatternUtil(-0.12549442809386618, -0.12549252161289132, -0.12549692291468545, -0.12549441001856848, -0.12549234759563205, -0.12549642457479876, -0.12549037575109195, -0.12549338181843722, -0.1254918454367771, -0.12549027859432044, -0.12549118534689568, -0.12549007897387582, -0.1254960817357687, -0.1254952771494618, -0.12549043895733575, -0.1254903878091727, -0.12549048985447825, -0.12549009426339125, -0.12549051222713842, -0.12549257321943968, -0.1254922000983288, -0.125489942217705 );
	   	autoblockYPattern = new PatternUtil(-0.12549908517649327, -0.12550150062025295, -0.12550456522949843, -0.12550099594073344, -0.12549987756965947, -0.12549963994506924, -0.1254915201769154, -0.12549703659470196, -0.12550072489994363, -0.12549382251136648, -0.12549385068887658, -0.12549219412249385, -0.12550387337869778, -0.12549869418153775, -0.12549604007727339, -0.12549290208135516, -0.12549153928676332, -0.1254942804724169, -0.12549212778758828, -0.1254940248809862, -0.12549818887310266, -0.12549442419940987);
	   	recordedPitchRandoms = new ArrayList<>();
	   	recordedYawRandoms = new ArrayList<>();
		targetList = new ArrayList<>();
		targetIndex = 0;
    }

    protected void onEnable() {
    	attacks = 0;
    	value = 0;
        clickStopwatch.reset();
        endAutoBlock();
        animation = false;    				
        decreasingarea = false;
        recordedYawRandoms.clear();
        recordedPitchRandoms.clear();
		targetedarea = 0;
		targetIndex = 0;
		current = 0;
		resetting = false;
		doneRecording = false;
    }

    @Collect
    public void onStrafe(EventStrafe event) {
    	if (mxz.getValue()) {
    		float yaw = mc.thePlayer.rotationYaw;
    		if (silentRotate.getValue()) {
    			yaw = mc.thePlayer.rotationYaw;
    		} else {
    			yaw = currentYaw;
    		}
    		float strafe = event.strafe;
    		float forward = event.forward;
    		float friction = event.friction;
    		float f = strafe * strafe + forward * forward;
    		if (f >= 1.0E-4F) {
    			f = MathHelper.sqrt_float(f);
    			if (f < 1.0F)
    				f = 1.0F;
    			
    			f = friction / f;
    			strafe *= f;
    			forward *= f;

    			float yawSin = MathHelper.sin((float)(yaw * Math.PI / 180));
    			float yawCos = MathHelper.cos((float)(yaw * Math.PI / 180));
    			mc.thePlayer.motionX += strafe * yawCos - forward * yawSin;
    			mc.thePlayer.motionZ += forward * yawCos + strafe * yawSin;
    		}
    		event.setCancelled(true);
    	}
    }
    
    @Collect
    public void onBlockPush(BlockPushEvent e) {
    	
    	e.setCancelled(!mc.thePlayer.isCollidedHorizontally && !Sensation.instance.cheatManager.isModuleEnabled("Phase") && !Sensation.instance.cheatManager.isModuleEnabled("Fly"));
    }
    
    @Collect
    public void onEvent(PlayerUpdateEvent event) {
    	if (Sensation.instance.cheatManager.isModuleEnabled("Scaffold"))return;
        setMode(mode.getSelectedStrings().get(0));
        float diffYaw = Math.abs(mc.thePlayer.rotationYaw - lastYaw);
        float diffPitch = Math.abs(mc.thePlayer.rotationPitch - lastPitch);
 
        lastPitch = mc.thePlayer.rotationPitch;
        lastYaw = mc.thePlayer.rotationYaw;
        if ((aimMode.getSelectedStrings().get(0) == "Developer-Cancer")) {
	        if (recording) {
	            if (diffYaw > 0.0 && !recordedYawRandoms.contains(mc.thePlayer.rotationYaw)) {
	                recordedYawRandoms.add(mc.thePlayer.rotationYaw);
	                ChatUtils.log(recordedYawRandoms.size() + " Yaws: " + mc.thePlayer.rotationYaw);
	            }
	
	            if (diffPitch > 0.0 && !recordedPitchRandoms.contains(mc.thePlayer.rotationPitch)) {
	                recordedPitchRandoms.add(mc.thePlayer.rotationPitch);
	                ChatUtils.log(recordedPitchRandoms.size() + " Pitches: " + mc.thePlayer.rotationPitch);
	            } 
	            
	        }
        }
        if (Sensation.instance.cheatManager.isModuleEnabled("Anti Bot")) {
			AntiBot antibot = (AntiBot) Sensation.instance.cheatManager.getCheatRegistry().get("Anti Bot");
			antibot.antibot();
			antibot = null;
		}
        
    	updateTargetList();

		if (targetList.isEmpty() || targetList.size() - 1 < targetIndex) {
			reset(-1,event);
			return;
		}

		if (targetIndex == -1) {
			reset(0,event);
			return;
		}

		if (!isValidTarget(targetList.get(targetIndex))) {
			reset(-1,event);
			return;
		}
		target = targetList.get(targetIndex);

        if (mode.getValue().get("Switch")) {
			if (event.isPre()) {

				aim(event, target); 
				Criticals crits = (Criticals) Sensation.instance.cheatManager.getCheatRegistry().get("Criticals");
				if (target instanceof EntityPlayer) {
			        if (Sensation.instance.cheatManager.isModuleEnabled("Criticals")) {
						crits.critical(event, "new");
					}
				}
				
				if (!recording) {
					if (autoBlockMode.getValue().get("Falcon")) {
						if (mc.thePlayer.ticksExisted % 25 == 0) {
							delay = 9;
						} else if (delay <= 0 && (!isHoldingSword() || !isBlocking())){
							executeAttacking(event, crits);
						}
						if (delay == 8) {
							startAutoBlock();
						} else if (delay == 3) {
							endAutoBlock();
						}
						if (delay != 0) {
							delay--;
						}
					} else {
						endAutoBlock();
						executeAttacking(event , crits);
						   boolean cancrit = !Sensation.instance.cheatManager.isModuleEnabled("Long Jump") && !Sensation.instance.cheatManager.isModuleEnabled("Fly") && !Sensation.instance.cheatManager.isModuleEnabled("Speed")  && mc.thePlayer.fallDistance == .0 && !mc.gameSettings.keyBindJump.isKeyDown() && mc.thePlayer.isCollidedVertically && mc.thePlayer.onGround && !mc.thePlayer.isInWater() && !crits.nearGround && crits.getMode() == "Watchdog";
						  
						   if (cancrit && Sensation.instance.cheatManager.isModuleEnabled("Criticals") && crits.criticalHits.getSelectedStrings().get(0) == "Watchdog") {
							   if (crits.groundTicks > (onServer("cubecraft") ? 4 : 3)) {

								   event.setPosY(mc.thePlayer.posY + (0.003999996185303445));

					               mc.thePlayer.onCriticalHit(target);
								   crits.groundTicks = 0;
							   }
						   }
					}
				}
            } else {
    			if (mc.thePlayer.ticksExisted % 3 == 0) {
    				value += up ? -MathUtils.getRandomInRange(.03, .03) : MathUtils.getRandomInRange(.08, .03);
    				if (value >= .9) {
    					up = true;
    				}
    				if (value <= -.15) {
    					up = false;
    				}
    			}
            	
                if (!autoBlockMode.getValue().get("Falcon")) {
                	startAutoBlock();
                }
            }
        } else if (mode.getValue().get("Multi")) {
        	for (Object o : mc.theWorld.loadedEntityList) {
        		if (o instanceof EntityLivingBase) {
        			EntityLivingBase entity = (EntityLivingBase) o;
	
        			if (isValidTarget(entity)) {
        				target = entity;
        				if (target == null) return;
        				if (event.isPre()) {
        					if (mc.thePlayer.getDistanceToEntity(entity) <= reach.getValue()) {
        						if (clickStopwatch.hasPassed(1000 / MathUtils.getRandomInRange(minValue.getValue(), maxValue.getValue()))) {
        							attack(event, target, Action.ATTACK);
        							clickStopwatch.reset();
        						}
        					}	
        				}
	           	  	}	
        		}	
        	}
        	if (target != null) {
	        	if (event.isPre()) {
					endAutoBlock();
	        	} else {
					startAutoBlock();
	        	}
        	}
        }
    }
    
    private void aim(PlayerUpdateEvent event, EntityLivingBase target) {
    	if (!(aimMode.getSelectedStrings().get(0) == "Developer-Cancer")) {

			Vector.Vector3<Double> enemyCoords = new Vector.Vector3<>(
					target.getEntityBoundingBox().minX
							+ (target.getEntityBoundingBox().maxX - target.getEntityBoundingBox().minX) / 2,
					(target instanceof EntityPig || target instanceof EntitySpider
							? target.getEntityBoundingBox().minY - target.getEyeHeight() / 1.5
							: target.posY - value) - Math.abs(target.posY - mc.thePlayer.posY) / (1.5 +Math.abs(target.posY - mc.thePlayer.posY)),
					target.getEntityBoundingBox().minZ
							+ (target.getEntityBoundingBox().maxZ - target.getEntityBoundingBox().minZ) / 2);
			Vector.Vector3<Double> myCoords = new Vector.Vector3<>(
					mc.thePlayer.getEntityBoundingBox().minX
							+ (mc.thePlayer.getEntityBoundingBox().maxX - mc.thePlayer.getEntityBoundingBox().minX) / 2,
					mc.thePlayer.posY,
					mc.thePlayer.getEntityBoundingBox().minZ
							+ (mc.thePlayer.getEntityBoundingBox().maxZ - mc.thePlayer.getEntityBoundingBox().minZ)
									/ 2);
			AngleUtility.Angle srcAngle = new AngleUtility.Angle(event.getLastYaw(), event.getLastPitch());
			AngleUtility.Angle dstAngle = angleUtility.calculateAngle(enemyCoords, myCoords);
			AngleUtility.Angle newSmoothing = angleUtility.smoothAngle(dstAngle, srcAngle,
					smoothing.getValue().floatValue() * 19, smoothing.getValue().floatValue() * 30);
		
			double x = target.posX - mc.thePlayer.posX + (target.lastTickPosX - target.posX) / 2;
			double z = target.posZ - mc.thePlayer.posZ + (target.lastTickPosZ - target.posZ) / 2;
			float yaw = 0;
			if (lastAimedTarget != target || !deviate.getValue()) {
				decreasingarea = false;
				groundTicks = false;
				targetedarea = 0;
			}

			lastAimedTarget = target;
			if (mc.thePlayer.ticksExisted % deviateTicks.getValue().intValue() == 0 && deviate.getValue()) {
				targetedarea += decreasingarea ? -.1 : .1;
				if (targetedarea >= 2) {
					decreasingarea = true;
				}
				if (targetedarea <= -2) {
					decreasingarea = false;
				}
			}
			double smooth = (1 + (smoothing.getValue().floatValue() * .01));
			yaw = constrainAngle(event.getLastYaw() - (float) -(Math.atan2(x, z) * (58 + targetedarea)));
			float pitch = newSmoothing.getPitch();
			boolean ticks = mc.thePlayer.ticksExisted % 20 == 0;
			
			switch (aimMode.getSelectedStrings().get(0)) {
			case "Normal":

				setYawGCD(event, dstAngle.getYaw(), ticks ? .243437f : .14357f);
				setPitchGCD(event, dstAngle.getPitch(),  ticks ? .243437f : .14327f);
				break;
			case "Smooth":
				yaw = (float) (event.getLastYaw() - yaw / smooth);
				setPitchGCD(event, pitch,  ticks ? .243437f : .14327f);
				setYawGCD(event, yaw, ticks ? .243437f : .14357f); 
				break;
			case "Adjustion": 
				smooth = smooth + (mc.thePlayer.getDistanceToEntity(target) * .325);
				yaw = (float) (event.getLastYaw() - yaw / smooth);
				setPitchGCD(event, pitch,  ticks ? .243437f : .14327f);
				setYawGCD(event, yaw, ticks ? .243437f : .14357f);
				break; 
			}
    	} else {
            if (!recording && doneRecording && recordedPitchRandoms.size() > 0 && recordedYawRandoms.size() > 0) {


            } else {            	
            	properYaw = getRotations(target).x;
            	properPitch = getRotations(target).y;
            	event.setPitch(properPitch);;
            	event.setYaw(properYaw);
            	recordedYawRandoms.forEach(yaw -> {
            		if (rayCast(yaw, getRotations(target).x) != null) {
            			properYaw = yaw;
                    	event.setYaw(properYaw);
            		}
            	});
            	recordedYawRandoms.forEach(pitch -> {
            		if (rayCast(getRotations(target).x, pitch) != null) {
            			properPitch = pitch;
                    	event.setPitch(properPitch);;
            		}
            	});
            }
    	}
	}
    public Vec2f getRotations(Entity entity) {
        return getRotations(mc.thePlayer.getPositionVector().addVector(0.0D, (double) mc.thePlayer.getEyeHeight(), 0.0D), entity.getPositionVector().addVector(0.0D, (double) (entity.getEyeHeight() / 2), 0.0D));
    } 
    public Vec2f getRotations(Vec3 origin, Vec3 position) {
        Vec3 org = new Vec3(origin.xCoord, origin.yCoord, origin.zCoord);
        Vec3 difference = position.subtract(org);
        double distance = difference.flat().lengthVector();
        float yaw = ((float) Math.toDegrees(Math.atan2(difference.zCoord, difference.xCoord)) - 90.0F);
        float pitch = (float) (-Math.toDegrees(Math.atan2(difference.yCoord, distance)));

        return new Vec2f(yaw, pitch);
    }
    public EntityLivingBase rayCast(Entity target, float yaw, float pitch) {
        if (mc.theWorld != null && mc.thePlayer != null) {
            Vec3 position = mc.thePlayer.getPositionEyes(mc.timer.renderPartialTicks);
            Vec3 lookVector = mc.thePlayer.getVectorForRotation(pitch, yaw);
            double reachDistance = mc.playerController.getBlockReachDistance();
            Entity pointedEntity = null;
            for (Entity currentEntity : mc.theWorld.getEntitiesWithinAABBExcludingEntity(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().addCoord(lookVector.xCoord * mc.playerController.getBlockReachDistance(), lookVector.yCoord * mc.playerController.getBlockReachDistance(), lookVector.zCoord * mc.playerController.getBlockReachDistance()).expand(1, 1, 1))) {
                if (currentEntity.canBeCollidedWith() && !currentEntity.isEntityEqual(target)) {
                    MovingObjectPosition objPosition = currentEntity.getEntityBoundingBox().expand((double) currentEntity.getCollisionBorderSize(), (double) currentEntity.getCollisionBorderSize(), (double) currentEntity.getCollisionBorderSize()).contract(0.1, 0.1, 0.1).calculateIntercept(position, position.addVector(lookVector.xCoord * reachDistance, lookVector.yCoord * reachDistance, lookVector.zCoord * reachDistance));
                    if (objPosition != null) {
                        double distance = position.distanceTo(objPosition.hitVec);
                        if (distance < reachDistance) {
                            if (currentEntity == mc.thePlayer.ridingEntity && !(Reflector.ForgeEntity_canRiderInteract.exists() && Reflector.callBoolean(currentEntity, Reflector.ForgeEntity_canRiderInteract)) && reachDistance == 0.0D) {
                                pointedEntity = currentEntity;
                            } else {
                                pointedEntity = currentEntity;
                                reachDistance = distance;
                            }
                        }
                    }
                }
            }
            if (pointedEntity != null && !pointedEntity.isEntityEqual(target) && (pointedEntity instanceof EntityLivingBase))
                return (EntityLivingBase) pointedEntity;
        }
        return null;
    }
 
    void executeAttacking(PlayerUpdateEvent event, Criticals crits) {
		boolean patternRequired = onServer("faithful") || onServer("minemen");
		List<Integer> delay = Arrays.asList(71, 73, 68, 53, 62, 58, 56, 51, 62, 56, 78, 74, 86, 74, 77, 55, 57, 54, 53, 89, 93, 79, 52, 76, 64, 50, 123, 130, 53, 153, 122, 129, 53, 126, 135, 200, 53, 86, 95, 98, 103, 220, 53, 185, 174, 124, 53, 96, 94, 53,85, 76);
		if (current++ >= (delay.size() -1)) {
			resetting = true;
		}
		
		if (resetting) {
			if (current > 0) {
				current--;
			} else {
				resetting = false;
			}
		}
		if (clickStopwatch.hasPassed(onServer("hypixel") || onServer("cubecraft")? crits.groundTicks > 3 || mc.thePlayer.fallDistance > .06 ? 49 : 55 - target.hurtTime / 2 : onServer("minemen") || patternRequired ? delay.get(current) + MathUtils.getRandomInRange(0, 3): 1000 / MathUtils.getRandomInRange(minValue.getValue(), maxValue.getValue()))) {
			if (hitbox.getValue()) {
				if (rayCast(event.getYaw(), event.getPitch()) != null) {
					attack(event, rayCast(event.getYaw(), event.getPitch()), attacks >= 5 && onServer("cubecraft")? Action.INTERACT : Action.ATTACK);
					groundTicks = false;
				} else {
					if (!noSwing.getValue() && !onServer("hypixel") && !onServer("cubecraft")) {
						mc.thePlayer.swingItem();
					}
				}
			} else {
				if (attacks > 6) {
					attack(event, target,Action.INTERACT);
				} else {

					attack(event, target,Action.ATTACK);
				}

			} 
			clickStopwatch.reset();
			if (attacks > 6) {
				attacks = 0;
			}
		} 
    }
    
    void setYawGCD(PlayerUpdateEvent event, float yaw, float offset) {

    	float theYaw = (float) MathUtils.round(yaw, 1) + offset;
    	event.setYaw(currentYaw = theYaw);
    	lastYaw = yaw;
		if (silentRotate.getValue()) {
			mc.thePlayer.rotationYaw = currentYaw;
		} else {
			event.setYaw(currentYaw);
		}
    }
    
    void setPitchGCD(PlayerUpdateEvent event, float pitch, float offset) {
    	float thePitch = (float) MathUtils.round(pitch, 1) + offset;
    	event.setPitch(currentPitch = thePitch);
    	lastPitch = pitch;
		if (silentRotate.getValue()) {
			mc.thePlayer.rotationPitch = currentPitch;
		} else {
			event.setPitch(currentPitch);
		}
    }
    
    EntityLivingBase rayCast(float yaw, float pitch) {
        if (mc.theWorld != null && mc.thePlayer != null) {
            Vec3 position = mc.thePlayer.getPositionEyes(mc.timer.renderPartialTicks);
            Vec3 lookVector = mc.thePlayer.getVectorForRotation(pitch, yaw);
            double reachDistance = reach.getValue();
            Entity pointedEntity = null;
            for (Entity currentEntity : mc.theWorld.getEntitiesWithinAABBExcludingEntity(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().addCoord(lookVector.xCoord * mc.playerController.getBlockReachDistance(), lookVector.yCoord * mc.playerController.getBlockReachDistance(), lookVector.zCoord * mc.playerController.getBlockReachDistance()).expand(1, 1, 1))) {
                if (currentEntity.canBeCollidedWith()) {
                    MovingObjectPosition objPosition = currentEntity.getEntityBoundingBox().expand((double) currentEntity.getCollisionBorderSize(), (double) currentEntity.getCollisionBorderSize(), (double) currentEntity.getCollisionBorderSize()).contract(0.1,0.1,0.1).calculateIntercept(position, position.addVector(lookVector.xCoord * reachDistance, lookVector.yCoord * reachDistance, lookVector.zCoord * reachDistance));
                    if (objPosition != null) {
                        double distance = position.distanceTo(objPosition.hitVec);
                        if (distance < reachDistance) {
                            if (currentEntity == mc.thePlayer.ridingEntity && !(Reflector.ForgeEntity_canRiderInteract.exists() && Reflector.callBoolean(currentEntity, Reflector.ForgeEntity_canRiderInteract)) && reachDistance == 0.0D) {
                                pointedEntity = currentEntity;
                            } else {
                                pointedEntity = currentEntity;
                                reachDistance = distance;
                            }
                        }
                    }
                }
            }
            if (pointedEntity != null && (pointedEntity instanceof EntityLivingBase))
                return (EntityLivingBase) pointedEntity;
        }
        return null;
    }
    
	float constrainAngle(float angle) {
		angle = angle % 360F;

		while (angle <= -180) {
			angle = angle + 360;
		}

		while (angle > 180) {
			angle = angle - 360;
		}
		return angle;
	}
    
    public boolean isHoldingSword() {
        if (mc.thePlayer != null && mc.theWorld != null && mc.thePlayer.getCurrentEquippedItem() != null
                && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword) {
            return true;
        }
        return false;
    }
    
   void attack(PlayerUpdateEvent e, Entity target, Action action) {
	   if (mc.thePlayer.getDistanceToEntity(target) <= reach.getValue()) {
		  if (!noSwing.getValue()) {
			   mc.thePlayer.swingItem();
		   }
           boolean flag = mc.thePlayer.fallDistance > 0.0F && !mc.thePlayer.onGround && !mc.thePlayer.isOnLadder() && !mc.thePlayer.isInWater() && !mc.thePlayer.isPotionActive(Potion.blindness) && mc.thePlayer.ridingEntity == null && target instanceof EntityLivingBase;

           float f = (float) mc.thePlayer.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
 
           float f1 = 0.0F;
           if (target instanceof EntityLivingBase) {
               f1 = EnchantmentHelper.func_152377_a(mc.thePlayer.getHeldItem(), ((EntityLivingBase) target).getCreatureAttribute());
           } else {
               f1 = EnchantmentHelper.func_152377_a(mc.thePlayer.getHeldItem(), EnumCreatureAttribute.UNDEFINED);
           }
           if (f1 > 0.0F) {
        	   mc.thePlayer.onEnchantmentCritical(target);
           }
		   mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, action));
	   } else if (mc.thePlayer.getDistanceToEntity(target) <= distance.getValue()){
		   if (!noSwing.getValue() && !onServer("hypixel")) {
			   mc.thePlayer.swingItem();
		   }
	   }
    }

   void startAutoBlock() {
	   if (!isHoldingSword() || isBlocking())return;
	   	setBlockState(true);
        if (autoBlockMode.getValue().get("NCP") || autoBlockMode.getValue().get("Falcon")) {

        	double value = 0;
            if (onServer("hypixel") || onServer("cubecraft")) {
            	value = MathUtils.getRandomInRange(-0.091133777774, -0.13333777774);
            } else {	
            	value = -1;
            }
            mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(value ,-1,value), 255, mc.thePlayer.getHeldItem(), 0,0,0));
        }
    }

    void endAutoBlock() {
    	if (!(isHoldingSword()) || !isBlocking())return;
    	setBlockState(false);
        if (autoBlockMode.getValue().get("NCP") || (autoBlockMode.getValue().get("Falcon"))) {
            mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            
        }
    }
    
    void setBlockState(boolean bool) {
    	blocking = bool;
        animation = bool;
    }

	boolean isValidTarget(EntityLivingBase entity) {
		if (entity == mc.thePlayer) {
			return false;
		}
		AntiBot antibot = (AntiBot) Sensation.instance.cheatManager.getCheatRegistry().get("Anti Bot");
		if (antibot.ignoredEntities.contains(entity) && antibot.getState()) return false;
		if (antibot.antiBot.getValue().get("Range") && !antibot.whitelistedEntity.contains(entity) && antibot.getState()) return false;
		boolean wlmode = antibot.antiBot.getValue().get("PvPTemple") || antibot.antiBot.getValue().get("Skidrizon");
		if (wlmode && !antibot.whitelistedEntity.contains(entity) && entity instanceof EntityPlayer && antibot.getState()) return false;

		if (entity != mc.thePlayer && entity instanceof EntityPlayer && targetEntities.getValue().get("Players")
				|| entity instanceof EntityMob && targetEntities.getValue().get("Monsters")
				|| entity instanceof EntitySlime && targetEntities.getValue().get("Monsters")
				|| entity instanceof EntityAnimal && targetEntities.getValue().get("Animals")
				|| entity instanceof EntityPig && targetEntities.getValue().get("Animals")
				|| entity instanceof EntityVillager && targetEntities.getValue().get("Villagers")
				|| entity instanceof EntityGolem && targetEntities.getValue().get("Golems")) {
			if (mc.thePlayer.getDistanceToEntity(entity) <= distance.getValue()
					&& (!targetEntityChecks.getValue().get("Dead") || !entity.isDead)
					&& (!targetEntityChecks.getValue().get("0/Negative HP") || entity.getHealth() > 0)
					&& (!miAmigos.getValue() || !(Sensation.instance.friendManager.getFriend(entity.getName()) != null))) {
				return true;
			}

		}
		return false;
	}

	void updateTargetList() {
		targetList.clear();
		mc.theWorld.getLoadedEntityList().forEach(entity -> {
			if (entity != null && entity instanceof EntityLivingBase) {
				if (isValidTarget((EntityLivingBase) entity)) {
					targetList.add((EntityLivingBase) entity);
				} else if (targetList.contains(entity)) {
					targetList.remove(entity);
				}

			}
		});

		if (targetList.size() > 1) {
			if (targetPriority.getValue().get("Highest Health")) {
				targetList.sort(Comparator.comparingDouble(EntityLivingBase::getHealth));
			} else if (targetPriority.getValue().get("Lowest Health")) {
				targetList.sort(Comparator.comparingDouble(EntityLivingBase::getHealth).reversed());
			} else if (targetPriority.getValue().get("Most Armor")) {
				targetList.sort(Comparator.comparingInt(EntityLivingBase::getTotalArmorValue).reversed());
			} else if (targetPriority.getValue().get("Least Armor")) {
				targetList.sort(Comparator.comparingInt(EntityLivingBase::getTotalArmorValue));
			} else if (targetPriority.getValue().get("Furthest")) {
				targetList.sort(Comparator.comparingDouble(mc.thePlayer::getDistanceToEntity));
			} else if (targetPriority.getValue().get("Closest")) {
				targetList.sort(Comparator.comparingDouble(mc.thePlayer::getDistanceToEntity));
			}
			if (prioritisePlayers.checkDependency() && prioritisePlayers.getValue()) {
				targetList.sort((e1, e2) -> Boolean.compare(e2 instanceof EntityPlayer, e1 instanceof EntityPlayer));
			}
		}
	}
	

	void reset(int i, PlayerUpdateEvent event) {
		targetIndex = i;
		endAutoBlock();
		decreasingarea = false;
		targetedarea = 0;
		Criticals crits = (Criticals) Sensation.instance.cheatManager.getCheatRegistry().get("Criticals");
		if (crits.groundTicks != 0 && Sensation.instance.cheatManager.isModuleEnabled("Criticals")) {
			mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, event.getYaw(), event.getPitch(), mc.thePlayer.onGround));
			crits.groundTicks = 0;
			crits.fallDistance = 0;
		}
	}

	public EntityLivingBase getCurrentTarget() {
		return !targetList.isEmpty() && targetIndex != -1 ? targetList.get(targetIndex) : null;
	}

    @Override
    protected void onDisable() {
        endAutoBlock();
        animation = false;
    }

    public boolean isBlocking() {
        return blocking;
    }

    public boolean isAnimation() {
        return animation;
    }
}
