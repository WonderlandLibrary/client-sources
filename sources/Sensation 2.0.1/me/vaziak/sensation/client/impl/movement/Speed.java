package me.vaziak.sensation.client.impl.movement;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.List;

import io.netty.buffer.Unpooled;
import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.BlockPushEvent;
import me.vaziak.sensation.client.api.event.events.EventStrafe;
import me.vaziak.sensation.client.api.event.events.PlayerMoveEvent;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.client.api.event.events.ProcessPacketEvent;
import me.vaziak.sensation.client.api.event.events.RunTickEvent;
import me.vaziak.sensation.client.api.property.impl.BooleanProperty;
import me.vaziak.sensation.client.api.property.impl.DoubleProperty;
import me.vaziak.sensation.client.api.property.impl.StringsProperty;
import me.vaziak.sensation.client.impl.combat.KillAura;
import me.vaziak.sensation.client.impl.player.AntiKnockback;
import me.vaziak.sensation.client.impl.player.Phase;
import me.vaziak.sensation.utils.client.ChatUtils;
import me.vaziak.sensation.utils.math.BlockUtils;
import me.vaziak.sensation.utils.math.MathUtils;
import me.vaziak.sensation.utils.math.TimerUtil;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;

/*
 * HOLY GRAEL OF DIRTY/MESSY CODE - FEEL FREE TO CLEAN THIS BITCH UP
 *
 *
 *  DONT REMOVE SHIT FROM IT - JUST ADD ONTO IT!
 *
 *  Notes:
 *  using setspeed with the ``speed`` double property needs to be done on pre, else it seems to not work!
 *
 *
 * */
public class Speed extends Module {
	public static boolean onSkywars;
	int stage;
	TimerUtil timer;
	private float air, groundTicks;
	private int stages, hops;
	double moveSpeed, lastDist;
	public int waitticks;
	StringsProperty prop_mode = new StringsProperty("Mode", "How this cheat will function.", null, false, true,
			new String[] { "Vanilla", "Spartan", "Sloth", "Mineplex", "NCP", "RevilsPort", "Sentinel", "FMCHop", "Kuih",
					"Janitor", "AGC", "Watchdog", "TickSpeed","AntiVirus", "AntiVirus2", "AAC", 
					"LongHop", "GodsEye", "OldNCP", "Kauri", "Holy", "Iris"});
	DoubleProperty prop_speed = new DoubleProperty("Speed", "The speed at which speed moves",
			() -> prop_mode.getValue().get("Janitor") || prop_mode.getValue().get("TickSpeed")
					|| prop_mode.getValue().get("NCP") || prop_mode.getValue().get("Vanilla") || prop_mode.getValue().get("AGC") || prop_mode.getValue().get("Holy"),
			2.0, 0.8, prop_mode.getValue().get("NCP") ? 2.1 : 9.999, 0.1, null);
	private BooleanProperty motY = new BooleanProperty("Custom MotionY", "Change value to bypass hypixel memedog updates at your pleasing", () -> prop_mode.getValue().get("Watchdog"), false);
	   
	private DoubleProperty customMotionY = new DoubleProperty("Custom MotionY", "WARNING: CHANGING THIS VALUE MAY RESULT IN BANS... Since hypixel devs, for some reason, patch speeds by MotionY... We now present you with an option to change it!",
			() -> prop_mode.getValue().get("Watchdog") && motY.getValue(),
			.40635, .38, .43, 0.0001, null);
	private DoubleProperty prop_ticks = new DoubleProperty("Ticks", "Ticks the speed boosts at",
			() -> prop_mode.getValue().get("TickSpeed"), 2.0, 2, 10, 1, null);
	 
	private float yaw, pitch;
	private boolean slow;
	private boolean retarded;
	private long lastScaffold;
	private boolean doit;
	private boolean decreasing;
	private double value, multi;
	private long lastNormalHop;
	private long lastStrafeHop;
	private int jumps;
	private boolean shouldslow;
	private Object movementSpeed;
	public static boolean onBedwars;

	public Speed() {
		super("Speed", Category.MOVEMENT);
		registerValue(prop_mode, prop_speed,  motY, customMotionY, prop_ticks);
		timer = new TimerUtil();
	}

	public void onDisable() {
		if (mc.thePlayer == null || mc.theWorld == null) return;

        mc.thePlayer.speedInAir = .02f;
		hops = 0;
		mc.timer.timerSpeed = 1.0F;
		mc.thePlayer.motionX = 0.0;
		mc.thePlayer.motionZ = 0.0;
		mc.thePlayer.setSpeed(0);
		mc.timer.timerSpeed = 1.0f;
		if (prop_mode.getValue().get("Sentinel")) {
            mc.thePlayer.motionX *= 0.5;
            mc.thePlayer.motionZ *= 0.5;
		}
	}

	public void onEnable() {
		doit = false; 
		decreasing = false;
		if (mc.thePlayer == null || mc.theWorld == null) return;
		value = .619;
		doit = false;
		stage = 2;
		air = 0;
		stage = 0;
		hops = 0;
		groundTicks = 0;
	}
	
	@Collect
    public void onBlockPush(BlockPushEvent e) {
		if (mc.thePlayer == null || mc.theWorld == null) return;
		if (Phase.isInsideBlock()) {
			value += .01;
			waitticks = 4;
		}
	}
	
	@Collect
	public void onPacketGet(ProcessPacketEvent e) {
		if (mc.thePlayer == null || mc.theWorld == null) return;
		if (e.getPacket() instanceof S08PacketPlayerPosLook) {
			if (prop_mode.getValue().get("Holy")) {
				S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook)e.getPacket();
				e.setCancelled(true);
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(packet.getX(), packet.getY(), packet.getZ(), packet.getYaw(), packet.getPitch(), true));
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(packet.getX(), packet.getY(), packet.getZ(), true));
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.onGround));
					
			}
			value += .02;
			if (Phase.isInsideBlock()) {
				waitticks = 4;
			}
		}
	}
	
	@Collect
	public void RunTick(RunTickEvent e) {
		if (mc.thePlayer == null || mc.theWorld == null) return;
		if (prop_mode.getValue().get("LongHop")) mc.timer.timerSpeed = .99f;
	}

	@Collect
	public void onPlayerUpdate(PlayerUpdateEvent event) { 
		if (mc.thePlayer == null || mc.theWorld == null) return;
		if (Sensation.instance.cheatManager.isModuleEnabled("Fly"))
			return;
		yaw = event.getYaw();
		pitch = event.getPitch();

		setMode(prop_mode.getSelectedStrings().get(0));

		double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
		double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
        lastDist = Math.sqrt(((mc.thePlayer.posX - mc.thePlayer.prevPosX) * (mc.thePlayer.posX - mc.thePlayer.prevPosX)) + ((mc.thePlayer.posZ - mc.thePlayer.prevPosZ) * (mc.thePlayer.posZ - mc.thePlayer.prevPosZ)));
		double offset = .99;
		Number playerYaw = getDir(mc.thePlayer.rotationYaw);
		boolean onground = !(mc.theWorld
				.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - .0001, mc.thePlayer.posZ))
				.getBlock() == Blocks.air);
		if (prop_mode.getValue().get("Watchdog")) { 
			boolean onice = mc.theWorld
					.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ))
					.getBlock() == Blocks.ice;
			List collidingList = mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer,
					mc.thePlayer.getEntityBoundingBox().offset(0, 0, 0));
			BlockPos blockPos = new BlockPos(event.getPosX(), event.getPosY(), event.getPosZ());
			lastDist = Math.sqrt(((mc.thePlayer.posX - mc.thePlayer.prevPosX) * (mc.thePlayer.posX - mc.thePlayer.prevPosX)) + ((mc.thePlayer.posZ - mc.thePlayer.prevPosZ) * (mc.thePlayer.posZ - mc.thePlayer.prevPosZ)));

	        if (mc.thePlayer.onGround) {
	            if (moveSpeed < 1.055f) moveSpeed += 0.55;
	        } else {
	        	
	        }
	        if (event.getPosY() % 0.015625 == 0.0) {
	        	event.setPosY(event.getPosY() + 8.485218952899E-5);
            }
            if (lastDist >= 5) {
            	lastDist = 0;
            }

		}
		
		if (prop_mode.getValue().get("Holy") && event.isPre()) {
			if (mc.thePlayer.fallDistance == 0 && mc.thePlayer.onGround) {
				if (timer.hasPassed(MathUtils.getRandomInRange(200, 450))) {
	                event.setPosY(MathUtils.getRandomInRange(-0.1, -0.01));
	                timer.reset();
	            } else {
	            	mc.thePlayer.setSpeed(prop_speed.getValue());
	            }
			} else {
	           mc.thePlayer.setSpeed(0);
			}
		}
		if (prop_mode.getValue().get("GodsEye")) {
			if (event.isPre()) {

				if (mc.thePlayer.isMoving()) {

					stage++;
					if (mc.thePlayer.onGround) {
						stage = 0;
						mc.thePlayer.motionY = .42f;
					} else if (mc.thePlayer.ticksExisted % 2 != 0 && stage > 4) {
						mc.thePlayer.setSpeed(.99);
						mc.thePlayer.motionY += .11;
					} else {
						mc.thePlayer.setSpeed(.27);
					}
				} else {
					mc.thePlayer.motionX = mc.thePlayer.motionZ = 0;
				}
			}
		}
		if (prop_mode.getValue().get("LongHop") && event.isPre()) { 
			if (mc.timer.timerSpeed != .99f) {
				mc.timer.timerSpeed = .99f;
			}
			
			if (mc.thePlayer.isMoving()) {
				stage++;
				if (mc.thePlayer.onGround) {
					stage = 0;
					mc.thePlayer.motionY = .42f;
				} else if (stage > 1) {
					mc.thePlayer.setSpeed(getBaseMoveSpeed() / 4 + (mc.timer.timerSpeed - stage *.015f));
				}
			} else {
				if (mc.thePlayer.onGround) {
					mc.thePlayer.setSpeed(0);
				}
			}
		}

		if (event.isPre() && prop_mode.getValue().get("RevilsPort")) {
			if (!mc.thePlayer.isMoving()) return;
			if (mc.thePlayer.onGround) {
				mc.thePlayer.motionY = .42f; 
				mc.thePlayer.setSpeed(.55);
			} else 
				if (mc.thePlayer.motionY < 0) {
					mc.thePlayer.motionY -= .0496; 
					mc.thePlayer.setSpeed(mc.thePlayer.getSpeed() * 1.34); 
				} else 
					mc.thePlayer.setSpeed(.311); 
		}
 
		if (prop_mode.getValue().get("Janitor")) {
			if (mc.thePlayer.ticksExisted % 2 == 0) {
				if (BlockUtils.getBlockAbovePlayer(mc.thePlayer, 1) instanceof BlockAir) {
					event.setPosY(event.getPosY() + (0.42 - .12));
				}
				mc.thePlayer.setSpeed(2);
			} else {
				mc.thePlayer.setSpeed(.1);
			}
		}

		if (prop_mode.getValue().get("Kuih") && mc.thePlayer.isMoving()) {
			if (mc.thePlayer.fallDistance <= 1.2) {
				event.setOnGround((mc.getCurrentServerData() == null)
						|| !mc.getCurrentServerData().serverIP.toLowerCase().contains("faithful") || (mc.thePlayer.onGround || (mc.thePlayer.fallDistance >= 2)));
				if (mc.thePlayer.onGround) {
					mc.thePlayer.motionY = 0.45;
					mc.thePlayer.setSpeed(mc.getCurrentServerData() != null
							&& mc.getCurrentServerData().serverIP.toLowerCase().contains("faithful") ? .55 : .47);
				} else {
					mc.thePlayer.setSpeed(mc.getCurrentServerData() != null
							&& mc.getCurrentServerData().serverIP.toLowerCase().contains("faithful")
									? .45 + getBaseMoveSpeed()
									: getBaseMoveSpeed() + (mc.thePlayer.ticksExisted % 3 == 0 ? 0.27 : .29));
					mc.thePlayer.motionY -= mc.thePlayer.fallDistance > 0.4 ? 0.03 : 0.001;
				}
			} else {
				mc.thePlayer.setSpeed(0.3);
			}
		}

		if (prop_mode.getValue().get("AntiVirus") && mc.thePlayer.isMoving()) {
			if (Sensation.instance.cheatManager.isModuleEnabled("Fly")) return;
			if (mc.thePlayer.onGround) { 
				event.setPosY(event.getPosY() + (mc.thePlayer.ticksExisted % 3 == 0 ? 0 : .0626));
				event.setOnGround(false);
				mc.thePlayer.setSpeed(getBaseMoveSpeed() + (1.0));
			} else {
				mc.thePlayer.setSpeed(.15);
			}
		}

		if (prop_mode.getValue().get("AntiVirus2") && mc.thePlayer.isMoving()) {
			if (mc.thePlayer.onGround) {
				moveSpeed = getBaseMoveSpeed();
				mc.thePlayer.motionY = .38f;
				stage = 0;
			}

			stage++;

			event.setOnGround(true);
			if (stage == 1) {
 
				moveSpeed += .23; // math failed me
			} else if (stage == 2) {
				moveSpeed += .26;
			} else if (stage == 3) {
				moveSpeed -= .35 * (moveSpeed - getBaseMoveSpeed());
			} else if (stage > 3){
				mc.thePlayer.motionY += .015;
				moveSpeed += .0005;
			}	
			
			moveSpeed = Math.max(getBaseMoveSpeed(), moveSpeed);
			mc.thePlayer.setSpeed(moveSpeed);
		} 
		if (prop_mode.getValue().get("TickSpeed")) {
			if (mc.thePlayer.isMoving() && mc.thePlayer.ticksExisted % prop_ticks.getValue().intValue() == 0) {
				mc.thePlayer.setSpeed(prop_speed.getValue());
			} else {
				mc.thePlayer.motionX = mc.thePlayer.motionZ = 0;
			}
		}
        if (prop_mode.getValue().get("Iris")) {
        	if (!decreasing) {
	        	if (mc.thePlayer.onGround) {
	        		mc.thePlayer.motionY = .42;
	        		stage += 1;
	        	}
	        	if (mc.thePlayer.onGround) {
	        		event.setOnGround(stage >= 6);
	        	}
        	} else {  
    			mc.thePlayer.motionY = .65;
    			mc.thePlayer.setSpeed(3);
        		decreasing = false;
        	}
        	if (mc.thePlayer.hurtTime == 9) {
        		stage = 0;
        		decreasing = true;
        	} 
        }
		if (prop_mode.getValue().get("AGC") && mc.thePlayer.hurtTime <= 0) {
			if (mc.thePlayer.onGround) {
				mc.thePlayer.speedOnGround = .15f;
			} else {

				mc.thePlayer.speedInAir = .019f;
			}
		}

		if (prop_mode.getValue().get("Kauri")) { 
			event.setOnGround(false);	     
			if (event.getPosY() % 0.015625 == 0.0) {
	        	event.setPosY(event.getPosY() + 8.5185218952899E-5);
            }
		}
	}
	
	@Collect
	public void onPlayerMove(PlayerMoveEvent e) {

		if (mc.thePlayer == null || mc.theWorld == null) return;
		if (Sensation.instance.cheatManager.isModuleEnabled("Fly"))
			return;
		boolean safe = !(mc.theWorld
				.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.2, mc.thePlayer.posZ))
				.getBlock() == Blocks.air);
		boolean onice = mc.theWorld
				.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ))
				.getBlock() == Blocks.ice;
		boolean onground = !(mc.theWorld
				.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - .0001, mc.thePlayer.posZ))
				.getBlock() == Blocks.air);
		List collidingList = mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer,
				mc.thePlayer.getEntityBoundingBox().offset(0, 0, 0));
		boolean flaggable = mc.thePlayer.isCollidedHorizontally || retarded || System.currentTimeMillis() - lastScaffold < 400;
		double yDistance = mc.thePlayer.posY - (int)mc.thePlayer.posY;
		boolean blocksbelow = !(mc.theWorld
				.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 2, mc.thePlayer.posZ))
				.getBlock() == Blocks.air);
		boolean isntonground = (mc.theWorld
				.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - .0626, mc.thePlayer.posZ))
				.getBlock() == Blocks.air);
		if (prop_mode.getValue().get("Sentinel")) {
            if (!mc.thePlayer.isMoving() || !safe || mc.thePlayer.fallDistance != 0) return;

            e.setMoveSpeed(mc.thePlayer.ticksExisted % 4 == 0 ? 2.4 : mc.thePlayer.ticksExisted %  3 == 0 ? .25f : .15);
            stage++;
		}

        if (prop_mode.getValue().get("Iris")) {
        	
				if (!mc.thePlayer.isMoving()) return;
				stage++;
				if (mc.thePlayer.onGround) {  
					stage = 0;
					e.y = mc.thePlayer.motionY = MathUtils.getRandomInRange(.42, .42);
					moveSpeed = (getBaseMoveSpeed() + .095) + (onice ? .1 : .005);   
					float f = mc.thePlayer.rotationYaw * 0.017453292F;
					e.x -= (double) (MathHelper.sin(f) * .5);
					e.z += (double) (MathHelper.cos(f) * .5); 
					
				} else { 
					if (stage == 2 && mc.thePlayer.motionY > .39) {   
						moveSpeed = getBaseMoveSpeed() + (onice ? .1 : 0.006);
					} else {  
						if (stage == 6 || stage == 8) { 
							moveSpeed += .009;
						}
						if (stage == 11 || stage == 10 || (!onice && stage == 12)) { 
							moveSpeed += stage == 12 ? .003 : .024;
							e.y = mc.thePlayer.motionY -= stage == 12 ? .15 : .2;
						}
						if (mc.thePlayer.onGround) {
							stage = 0;
							moveSpeed += (mc.thePlayer.hurtTime * .1f) / 4;
						} else {
							moveSpeed += (mc.thePlayer.hurtTime * .1f) / 4;
						}
						e.setMoveSpeed(moveSpeed = moveSpeed - moveSpeed / 35);
					}
				}
			
        }
		if (prop_mode.getValue().get("Vanilla")) {
			mc.thePlayer.setSpeed(mc.thePlayer.ticksExisted % 4 == 0 ? 0.8 : 0.52);
		}

		if (prop_mode.getValue().get("AGC")) {
 
			if (waitticks > 0) {
				waitticks-= 1;
			} else {
		        if(mc.thePlayer.isMoving()) {
		            if(mc.thePlayer.onGround) {
		                mc.thePlayer.jump();
		                e.y = mc.thePlayer.motionY = 0.375f;
		                stage = 1;
		            } else { 
		            	boolean sped =  mc.thePlayer.isPotionActive(Potion.moveSpeed);

		            	if (!safe) {
		            		e.x = e.z = 0;
		            	}
		            	if (mc.thePlayer.ticksExisted % 2 == 0 && mc.thePlayer.motionY < 0 && safe) {
			                e.y = mc.thePlayer.motionY += MathUtils.getRandomInRange(.015, .045) * (sped ? 1.012 : 1); 
	            		}
		            	if (mc.thePlayer.ticksExisted % 4 == 0 && mc.thePlayer.motionY < .34 && safe) {
		            		e.setMoveSpeed(.665 * (sped ? 1.008 : 1));

		            	} else { 
		            		e.setMoveSpeed(safe ? .3195 * (sped ? 1.005 : 1) : .1);
		            	}
		            }
		        }
			}
		}
		if (prop_mode.getValue().get("Spartan")) {
			if (mc.thePlayer.isMoving()) {
				stage++;
				if (mc.thePlayer.onGround) {  
					stage = 0;
					e.y = mc.thePlayer.motionY = .42F;
					moveSpeed = (getBaseMoveSpeed() + .09) + (onice ? .1 : .005);  
					float f = mc.thePlayer.rotationYaw * 0.017453292F;
					e.x -= (double) (MathHelper.sin(f) * .33);
					e.z += (double) (MathHelper.cos(f) * .3);
				} else { 
					if (stage == 2 && mc.thePlayer.motionY > .39) {   
						moveSpeed = getBaseMoveSpeed() + (onice ? .1 : 0.006);
					} else {  
						if (stage == 6 || stage == 8) { 
							moveSpeed += .009;
						}

						if (stage == 11 || stage == 10 || (!onice && stage == 12)) { 
							moveSpeed += stage == 12 ? .0063 : .034;
							e.y = mc.thePlayer.motionY -= stage == 12 ? .35 : .24;
						}
						if (mc.thePlayer.onGround) {
							stage = 0;
						}					
						if (mc.thePlayer.motionY < 0) {
							e.y = mc.thePlayer.motionY -= .078;
							moveSpeed += .0047;
						}
						e.setMoveSpeed( moveSpeed = moveSpeed - moveSpeed / 50);
					}
				}
			}
		}
		if (prop_mode.getValue().get("Kauri")) { 
			  boolean hasSpeed = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed) != null;

		        if (MathUtils.round(mc.thePlayer.posY - (int) mc.thePlayer.posY, 3) == MathUtils.round(0.138D, 3)) {
		            mc.thePlayer.motionY -= 1.0D;
		        }
		        if (mc.thePlayer.onGround || stage == 0) {

		            stage = 0;
		            e.setY((float) (mc.thePlayer.motionY = 0.42F));
		            movementSpeed = e.getBaseMoveSpeed() * (hasSpeed ? 2.1F : 2.55F);
		        } else if (stage == 1) {
		            e.y -= 0.00800;
		            movementSpeed = lastDist - (0.15 * (lastDist - e.getBaseMoveSpeed()));
		        } else {
		            e.y -= 0.00099;
		            movementSpeed = (lastDist - e.getBaseMoveSpeed() / 66.1);
		        }
		}
		if (prop_mode.getValue().get("OldNCP")) {
			stage++; 
			float f = mc.thePlayer.rotationYaw * 0.017453292F;
            if (mc.thePlayer.isMoving()) {
				if (stage == 2 || stage == 6) {

					e.x -= (double) (MathHelper.sin(f) * .2F);
					e.z += (double) (MathHelper.cos(f) * .2F);
				}
            }

            if (mc.thePlayer.moveForward == 0.0f && mc.thePlayer.moveStrafing == 0.0f) {
                moveSpeed = getBaseMoveSpeed();
            }
            if (stage == 1 && mc.thePlayer.isCollidedVertically && (mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f)) {
                moveSpeed = 1 + 0.0348 + getBaseMoveSpeed();
            }
            if (stage == 2 && mc.thePlayer.isCollidedVertically && (mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f)) {

				e.x -= (double) (MathHelper.sin(f) * .2F);
				e.z += (double) (MathHelper.cos(f) * .2F);
                if (mc.thePlayer.isPotionActive(Potion.jump)) {
					double mY = mc.thePlayer.isPotionActive(Potion.jump) ? .4026 : .4228;
					if (mc.thePlayer.isPotionActive(Potion.jump)) {
						mY += (mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.105;
					}
					e.setY(mY);
                } else {
                    e.setY((mc.thePlayer.motionY = 0.4228F));
                }
				if (mc.thePlayer.isPotionActive(Potion.jump)) {
					moveSpeed = getBaseMoveSpeed() * (1.4);
				} else {
					if (BlockUtils.getBlockAtPos(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ)).isFullCube() || BlockUtils.getBlockAtPos(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ)) instanceof BlockGlass) {
						double mult = onSkywars ? 2.14999 : mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 1.56 : 1.62;
						moveSpeed = getBaseMoveSpeed() * (mult);
						doit = true;
					} else { 
						doit = false;
						moveSpeed = getBaseMoveSpeed() * (onSkywars ? 1.5 : 1.0);
					}
				}
            } else {
                if (mc.thePlayer.onGround || onground || collidingList.size() > 0 && stage > 0) {
                    stage = ((mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f) ? 1 : 0);
                }
				if (slow) { 
					if (value >= (doit ? .64 : .66)) {
						decreasing = true;
					} 
					if (value <= .625) {
						decreasing = false;
					}
					value += decreasing  ? - .0025 : .0025; 
					if (!doit) {
						value = value + .09;
					}
					moveSpeed -= (onSkywars ? flaggable ? .67 : value : .68) * (moveSpeed - getBaseMoveSpeed());
					slow = false;
				} else {  
					moveSpeed -=  onSkywars ? (retarded || System.currentTimeMillis() - lastScaffold < 400) ? .0066 : .0041 : mc.thePlayer.isPotionActive(Potion.moveSpeed) ? .0084 : .0072;
				} 
            } 
			e.setMoveSpeed( moveSpeed);
		}
		if (prop_mode.getValue().get("Watchdog")) {
			if (Sensation.instance.cheatManager.isModuleEnabled("Scaffold")) {
				lastScaffold = System.currentTimeMillis();
			}
			boolean canflag = System.currentTimeMillis() - lastScaffold < 500;
            if (mc.thePlayer.isInWeb) return;
            mc.timer.timerSpeed = 1.0f;
            if (canflag || mc.gameSettings.keyBindBack.isKeyDown() || (mc.thePlayer.moveStrafing > 0 || mc.gameSettings.keyBindRight.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown())) {
            	switch (stage) {
                    case 0:
                        ++stage;
                        lastDist = 0;
                        break;
                    case 2:
                        double motionY = 0.4234205F;
                        if (mc.thePlayer.isMoving() && mc.thePlayer.onGround) {
                            if (mc.thePlayer.isPotionActive(Potion.jump)) {
                                motionY += ((mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1);
                            }
                            e.setY(mc.thePlayer.motionY = motionY);

					        mc.thePlayer.isAirBorne = true;
					        mc.thePlayer.triggerAchievement(StatList.jumpStat);
                            moveSpeed = getBaseMoveSpeed();
                            hops++;
                            if (hops > 0) {
                                moveSpeed *= canflag ? 1.9 : 2.14;
                            }
                        }
                        break;
                    case 3:
                        moveSpeed = lastDist - (.66) * (lastDist - getBaseMoveSpeed());
                        break;
                    default:
                        if (mc.thePlayer.onGround || onground || collidingList.size() > 0 && stage > 0) {
                            stage = mc.thePlayer.moveForward == 0.0F && mc.thePlayer.moveStrafing == 0.0F ? 0 : 1;
                        }
                        if (mc.thePlayer.motionY < 0 && !mc.thePlayer.isPotionActive(Potion.jump)) {
                        	e.y = mc.thePlayer.motionY - .035;
                        	moveSpeed += .005;
                        }
                        moveSpeed = lastDist - lastDist / (159);
                        break;
                }
                moveSpeed = Math.max(moveSpeed, getBaseMoveSpeed());

                e.setMoveSpeed( moveSpeed);
                stage++;
                return;
            }
            if (!canflag && (mc.thePlayer.moveForward > 0 && mc.thePlayer.moveStrafing < 1 && !mc.gameSettings.keyBindLeft.isKeyDown() && !mc.gameSettings.keyBindRight.isKeyDown() && (System.currentTimeMillis() - lastStrafeHop) > 420L)) {
                double max = 1.5;

                if (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed) != null) max = 0.766;

                if (moveSpeed >= max) {
                    moveSpeed -= MathUtils.getRandomInRange(0.15f, 0.21f);
                }
                if (mc.thePlayer.moveForward == 0.0f && mc.thePlayer.moveStrafing == 0.0f) {
                    moveSpeed = getBaseMoveSpeed();
                }
                if (stage == 1 && mc.thePlayer.isCollidedVertically && (mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f)) {
                    moveSpeed =  (mc.thePlayer.isPotionActive(Potion.moveSpeed) ? .075 : .07) + getBaseMoveSpeed();
                }
                if (stage == 2 && mc.thePlayer.isCollidedVertically && (mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f)) {
                    if (mc.thePlayer.isPotionActive(Potion.jump)) {
                        e.setY((mc.thePlayer.motionY = 0.407F + (mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1));
                    } else {

                        if (onServer("hypixel")) {
                        	e.setY((mc.thePlayer.motionY = 0.4225914205111111111111111F));
                        } else {
                        	e.setY((mc.thePlayer.motionY = 0.4026D));
                        }
                        
                    }
			        mc.thePlayer.isAirBorne = true;
			        mc.thePlayer.triggerAchievement(StatList.jumpStat);
                    moveSpeed =  ((mc.thePlayer.isPotionActive(Potion.moveSpeed) ? .071 : .079) + getBaseMoveSpeed()) * (mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 1.8 : 1.6);
                } else if (stage == 3) {
                    double difference = 0.66 * (lastDist - getBaseMoveSpeed());
                    moveSpeed = lastDist - difference;
                } else {
                    if ((mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0, mc.thePlayer.motionY, 0.0)).size() > 0 || mc.thePlayer.onGround) && stage > 0) {
                        stage = ((mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f) ? 1 : 0);
                    }
                    moveSpeed = lastDist - lastDist / 159;
                }
                moveSpeed = Math.max(moveSpeed, getBaseMoveSpeed());
                lastNormalHop = System.currentTimeMillis();


                if (stage > 0) {
                    double forward = mc.thePlayer.movementInput.moveForward;
                    double strafe = mc.thePlayer.movementInput.moveStrafe;
                    float yaw = Sensation.instance.cheatManager.isModuleEnabled("Target Strafe") && !Sensation.instance.cheatManager.isModuleEnabled("Scaffold") ? this.yaw : mc.thePlayer.rotationYaw;
                    if ((forward == 0.0D) && (strafe == 0.0D)) {
                        e.setZ((float) (mc.thePlayer.motionZ = 0.0));
                        e.setX((float) (mc.thePlayer.motionX = 0.0));
                    } else {
                        if (forward != 0.0D) {
                            if (strafe > 0.0D) {
                                yaw += (forward > 0.0D ? -45 : 45);
                            } else if (strafe < 0.0D) {
                                yaw += (forward > 0.0D ? 45 : -45);
                            }
                            strafe = 0.0D;
                            if (forward > 0.0D) {
                                forward = 1;
                            } else if (forward < 0.0D) {
                                forward = -1;
                            }
                        }
                        e.setX((mc.thePlayer.motionX = forward * moveSpeed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * moveSpeed * Math.sin(Math.toRadians(yaw + 90.0D))));
                        e.setZ((mc.thePlayer.motionZ = forward * moveSpeed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * moveSpeed * Math.cos(Math.toRadians(yaw + 90.0D))));
                    }
                }
                if (mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f) {
                    if (mc.thePlayer.motionY < 0) {
                    }
                    ++stage;
                }
            }
		}

		if (prop_mode.getValue().get("FMCHop") && mc.thePlayer.isMoving() && !mc.thePlayer.isInWater()) {
			if (onground && mc.thePlayer.onGround) { 
                stage = 0;
                e.y = mc.thePlayer.motionY = 0.42f;
                if (air < 4) {
                    air++;
                }
            }
        	moveSpeed = 0.321;
            double thirdvalue = 0.0295999999;
            if (stage <= 10) {
    	        List<Double> speds = Arrays.asList(0.497, 0.3031, 0.302, 0.3019,0.3019,0.3219,0.3019,0.3019,0.3019,0.3019,0.3019, .301, .3, .298, .297);
    	        moveSpeed = speds.get(stage);
            }

        	if (air >= 2) {
        		if (stage <= 11) {
    		        List<Double> speeds = Arrays.asList(0.1069, 0.0642, 0.0629, 0.0607, 0.0584, 0.0561, 0.0539, 0.0517, 0.0496, 0.0475, 0.0455, 0.045);
    		        moveSpeed += speeds.get(stage);
    	    	} else if (stage == 14) {
    	    		moveSpeed += 0.1;
    	    	}
            }
        	
        	if (air >= 3) {
        		if (stage == 11) {
                    moveSpeed += 0.058;
        		} else if (stage > 11) {
                    moveSpeed += thirdvalue + 0.0002;
        		} else if (stage >= 1) {
                    moveSpeed += thirdvalue;
        		} else if (moveSpeed == 0) {
                    moveSpeed += 0.086;
        		}
        	}
        	
        	if (stage == 8 && mc.thePlayer.onGround) {
        		moveSpeed -= 0.002;
        	}
        	
            if (mc.thePlayer.moveForward <= 0) {
                moveSpeed -= 0.06;
            }
            
            if (mc.thePlayer.isCollidedHorizontally) {
                moveSpeed = getBaseMoveSpeed();
                air = 0;
            }
            if (stage == 10 || stage == 11) {
            	e.y = mc.thePlayer.motionY * .6;
            	e.x = mc.thePlayer.motionX * 1.6;
            	e.z = mc.thePlayer.motionZ * 1.6;
            }
            e.setMoveSpeed( moveSpeed * (1 + (stage * .0005) + (mc.thePlayer.hurtTime * .0002)));
        	++stage;
		}
        if (prop_mode.getValue().get("AAC") && mc.thePlayer.isMoving() && !mc.thePlayer.isInWater()) {
        	mc.thePlayer.setSprinting(true);
        	if (onground && mc.thePlayer.onGround) { 
                stage = 0;
                e.y = mc.thePlayer.motionY = 0.42f;
                if (air < 4) {
                    air++;
                }
            }
        	moveSpeed = 0.29;
            double thirdvalue = 0.0285999999;
            if (stage <= 14) {
    	        List<Double> speds = Arrays.asList(0.497, 0.3031, 0.302, 0.3019,0.3019,0.3019,0.3019,0.3019,0.3019,0.3019,0.3019, .301, .3, .298, .297);
    	        moveSpeed = speds.get(stage);
            }

        	if (air >= 2) {
        		if (stage <= 11) {
    		        List<Double> speeds = Arrays.asList(0.1069, 0.0642, 0.0629, 0.0607, 0.0584, 0.0561, 0.0539, 0.0517, 0.0496, 0.0475, 0.0455, 0.045);
    		        moveSpeed += speeds.get(stage);
    	    	} else if (stage == 14) {
    	    		moveSpeed += 0.042;
    	    	}
            }
        	
        	if (air >= 3) {
        		if (stage == 11) {
                    moveSpeed += 0.018;
        		} else if (stage > 11) {
                    moveSpeed += thirdvalue + 0.0001;
        		} else if (stage >= 1) {
                    moveSpeed += thirdvalue;
        		} else if (moveSpeed == 0) {
                    moveSpeed += onServer("Sentinel") ? 0.066 : .046;
        		}
        	}
        	
        	if (stage == 8 && mc.thePlayer.onGround) {
        		moveSpeed -= 0.002;
        	}
        	
            if (mc.thePlayer.moveForward <= 0) {
                moveSpeed -= 0.06;
            }

            if (mc.thePlayer.isCollidedHorizontally) {
                moveSpeed -= 0.1;
                air = 0;
            }
            e.setMoveSpeed( onServer("Sentinel") ? moveSpeed * (1.035) : moveSpeed);
 
            ++stage;
        }
		
		if (prop_mode.getValue().get("NCP")) {
			BlockPos underPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ);
			if (!mc.thePlayer.isMoving()) {
				moveSpeed = getBaseMoveSpeed();
				mc.timer.timerSpeed = 1.0f;
			} else {

				double motionY = mc.getCurrentServerData() != null
						&& mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel") ? 0.39875f : .39975f;

				stage += 1;
				if (stage == 1) {
					if (onServer("omegacraft")) mc.timer.timerSpeed = 0.4f;
				} else if (stage == 2 && mc.thePlayer.onGround) {
					if (onServer("omegacraft")) mc.timer.timerSpeed = 1.2f;
					if (mc.thePlayer.isPotionActive(Potion.jump)) {
						motionY += (mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1;
					}
					e.y = mc.thePlayer.motionY = motionY;
					moveSpeed = getBaseMoveSpeed();
					moveSpeed *= prop_speed.getValue();
					slow = true;
				} else {
					if (collidingList.size() > 0 || mc.thePlayer.onGround && stage > 0) {
						stage = mc.thePlayer.isMoving() ? 1 : 0;
					}
					if (slow) {
						moveSpeed -= 0.67 * (moveSpeed - getBaseMoveSpeed());
						slow = false;
					} else {
						if (onServer("omegacraft")) mc.timer.timerSpeed = 1.0f + stage * .005f;
						moveSpeed -= 0.0074;
					}
				}
				e.setMoveSpeed( Math.max(moveSpeed, getBaseMoveSpeed()));
			}
		}

		if (prop_mode.getValue().get("Sloth") && mc.thePlayer.isMoving()) {
			if (Sensation.instance.cheatManager.isModuleEnabled("Fly") || mc.thePlayer.fallDistance > 1.8)
				return;
			stage += 1;
            float f = mc.thePlayer.rotationYaw * 0.017453292F;
			if (mc.thePlayer.onGround) {
				e.x = mc.thePlayer.motionX -= (double) (MathHelper.sin(f) * 0.1F);
				e.z = mc.thePlayer.motionZ += (double) (MathHelper.cos(f) * 0.1F);
				e.y = mc.thePlayer.motionY = .425f;
				stage = 0;
			}
			double value = (mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 1.001 : (mc.thePlayer.moveStrafing != 0 ? 1.35 : 1.45));
			
			moveSpeed = mc.thePlayer.onGround || stage < 1 ? getBaseMoveSpeed() * 1.05 : getBaseMoveSpeed() * (value + ((stage == 5) && mc.thePlayer.moveStrafing == 0 ? .07 : 0));

			if (MathUtils.isInputBetween(stage, 6, 8)) {

				e.y = mc.thePlayer.motionY += .05;					
				e.x = mc.thePlayer.motionX -= (double) (MathHelper.sin(f) * 0.1F);
				e.z = mc.thePlayer.motionZ += (double) (MathHelper.cos(f) * 0.1F);
			}
			if (MathUtils.isInputBetween(stage, 9, 11)) {
				moveSpeed *= 1.21;
				if (mc.thePlayer.fallDistance > 1) {
					e.y = mc.thePlayer.motionY *= 1.25;
				}
			}

			e.setMoveSpeed(moveSpeed * 1.004);
		}
		
		if (prop_mode.getValue().get("Mineplex")) {
			if (mc.thePlayer.onGround && mc.thePlayer.isMoving()) {
				e.setX(0);
				e.setZ(0);
				moveSpeed = 0.55;
				mc.timer.timerSpeed += 1.5f;
				e.setY(mc.thePlayer.motionY = 0.42);
				return;
			}
			mc.timer.timerSpeed = 1.0f;
			moveSpeed -= 0.0015;
			mc.thePlayer.motionY += 0.02;
			e.setMoveSpeed( moveSpeed);
		}
	}

	public float getDir(float yaw) {
		EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
		float theyaw = yaw;
		if (player.movementInput.moveForward < 0) {
			theyaw += 180;
		}
		float forward = 1;
		if (player.movementInput.moveForward < 0) {
			forward = -0.5F;
		} else if (player.movementInput.moveForward > 0) {
			forward = 0.5F;
		}
		if (player.movementInput.moveStrafe > 0) {
			theyaw -= 90 * forward;
		}
		if (player.movementInput.moveStrafe < 0) {
			theyaw += 90 * forward;
		}
		theyaw *= 0.017453292F;// <- this little value can be found in jump() in entity living base
		return theyaw;
	}

	double getMotion(int stage) throws ConcurrentModificationException {
		double[] mot = { 0.35, 0.2799, 0.183, 0.103, 0.024, -0.008, -0.04, -0.072, -0.104, -0.13, -0.02, -.1, };
		stage--;
		if (stage >= 0 && stage < mot.length) {
			return mot[stage];
		} else {
			return mc.thePlayer.motionY;
		}
	}
	
    public int getSpeedEffect() {
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed))
            return mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
        else
            return 0;
    }


	public double getHopSpeed() {
		if (hops <= 2) {
			return .66;
		} else {
			return mc.thePlayer.isPotionActive(Potion.moveSpeed) ? .8 : .65;
		}
	}
	
	public void setSpeedNoEvent(double moveSpeed) {
		double speed = Math.max(moveSpeed, getBaseMoveSpeed());
		
		double forward = mc.thePlayer.movementInput.moveForward, strafe = mc.thePlayer.movementInput.moveStrafe, 
				yaw = Sensation.instance.cheatManager.isModuleEnabled("Target Strafe") && !Sensation.instance.cheatManager.isModuleEnabled("Scaffold") ? this.yaw : mc.thePlayer.rotationYaw;
			
		if (forward == 0.0F && strafe == 0.0F) {
			mc.thePlayer.motionX = (0);
			mc.thePlayer.motionZ = (0);
		}
		if (forward != 0 && strafe != 0) {
			forward = forward * Math.sin(Math.PI / 4);
			strafe = strafe * Math.cos(Math.PI / 4);
		}
		mc.thePlayer.motionX = ((forward * speed * -Math.sin(Math.toRadians(yaw)) + (strafe) * speed * Math.cos(Math.toRadians(yaw))) * 1);
		mc.thePlayer.motionZ = ((forward * speed * Math.cos(Math.toRadians(yaw)) - (strafe) * speed * -Math.sin(Math.toRadians(yaw))) * 1);
	}
}