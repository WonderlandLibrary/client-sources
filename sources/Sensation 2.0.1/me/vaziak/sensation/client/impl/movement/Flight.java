package me.vaziak.sensation.client.impl.movement;

import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInputFromOptions;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.EventSafeWalk;
import me.vaziak.sensation.client.api.event.events.PlayerMoveEvent;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.client.api.event.events.ProcessPacketEvent;
import me.vaziak.sensation.client.api.event.events.SendPacketEvent;
import me.vaziak.sensation.client.api.property.impl.BooleanProperty;
import me.vaziak.sensation.client.api.property.impl.DoubleProperty;
import me.vaziak.sensation.client.api.property.impl.StringsProperty;
import me.vaziak.sensation.client.impl.combat.Criticals;
import me.vaziak.sensation.client.impl.combat.KillAura;
import me.vaziak.sensation.client.impl.player.Phase;
import me.vaziak.sensation.utils.client.ChatUtils;
import me.vaziak.sensation.utils.math.BlockUtils;
import me.vaziak.sensation.utils.math.MathUtils;
import me.vaziak.sensation.utils.math.TimerUtil;

/*
 * Recoded started on:
 *
 * 2/8/2020
 * @
 * 3:40 PM US CNTRL 
 * 
 * @author Criiptic
 * 
 * TODO: Iris fly - Kauri Fly - Verus Fly - Hypixel bypass - Cubecraft bypass - 
 * 
 */

public class Flight extends Module {
	
	private StringsProperty prop_mode = new StringsProperty("Fly", "How this cheat will function.", false, true, new String[]{"Vanilla", "Falcon", "Watchdog", "Sentinel", "Iris"});
	private StringsProperty prop_boostMode = new StringsProperty("Boost Mode", "Which boost the Watchdog mode will use", () -> prop_mode.getValue().get("Watchdog") ,false, true, new String[] {"No Damage", "Damage", "Timer", "None"});
	private DoubleProperty boostSpeed = new DoubleProperty("Boost speed", "Speed that it will boost at",() -> prop_mode.getValue().get("Watchdog") && prop_boostMode.getValue().get("Damage"), 1.99, 1.0, 2, .1, null);
	
	private int stage;
	private int counter;
	private boolean boost;
	private boostType type;
	private double lastDist;
	private TimerUtil timer;
	private TimerUtil timer2;
	private TimerUtil offsetTimer;
	private TimerUtil boostTimer;
	private float timerspeed;
	private double moveSpeed;
	private double[] positions = new double[]{0.0, 0.0, 0.0};
	private boolean damageboost;
	private boolean hasDamaged; 
	private float randomOffset;
	private float randomDecension;
	private float randomReAscension;
	private double randomAscend;
	private double randomDescend;
	private double yPos;
	private int ticker;
	private int alternateTicker;


    public Flight() {
        super("Fly", Category.MOVEMENT);
        registerValue(prop_mode, prop_boostMode, boostSpeed);
        timer = new TimerUtil();
        timer2 = new TimerUtil();
        offsetTimer = new TimerUtil();
        boostTimer = new TimerUtil();
    	type = boostType.NONE;
    }
    
	public enum boostType {
		DAMAGE, NODAMAGE, TIMER, NONE;
	}

    public void onDisable() {
    	mc.timer.timerSpeed = 1.0f; 
    	switch (prop_mode.getSelectedStrings().get(0)) {
    	
    		case "Sentinel": 


	            if (getGroundLevel() != 999) {
	      			mc.thePlayer.setPosition(mc.thePlayer.posX, positions[1], mc.thePlayer.posZ);
	            	if (mc.thePlayer.posY - getGroundLevel() <= 4.99) {
		            	sendC04Specific(getGroundLevel(), true);
		            	sendC04Specific(getGroundLevel() -.1, false);
	            	} else {
	            		int distance = (int)(mc.thePlayer.posY - getGroundLevel());
	            		for (int i = 0; i < distance; i++) {
			            	sendC04Specific(Math.round(mc.thePlayer.posY) - i, true);
	            		}
		            	sendC04Specific(getGroundLevel(), true);
		            	sendC04Specific(getGroundLevel() -.1, false);
	            	}
	            	setPositionSpecific(getGroundLevel());
	            } else {
	            	ChatUtils.log("Couldn't find a ground position...");
	            	ChatUtils.log("Lagging you back so you don't fall ;)");
	            	sendC04Specific(mc.thePlayer.posY + .35, false);
	            }
    			break;
    	}
    }
    
    @Override
    public void onEnable() {
    	if (mc.thePlayer == null) return;
    	positions[0] = mc.thePlayer.posX;
    	positions[1] = mc.thePlayer.boundingBox.minY;
    	positions[2] = mc.thePlayer.posZ;
    	switch (prop_mode.getSelectedStrings().get(0)) {
    	case "Watchdog":
        	randomOffset = MathUtils.getRandomInRange(.0568f, .0574f);
        	randomDecension = MathUtils.getRandomInRange(.0000000000000125f, .0000000000000127f);
        	randomReAscension = MathUtils.getRandomInRange(.051222000000013f, .052111000000013f);

        	ticker = (MathUtils.getRandomInRange(20, 25));
        	alternateTicker = 0;
        	stage = 1;
        	counter = 0;
        	lastDist = 0;
        	moveSpeed = 0;
			ticker = 0;
    		timer.reset();
    		timer2.reset();
    		if (!mc.thePlayer.onGround) {
    			forceDisable("Sorry, you must be on the ground to do this!");
    		}
        	if (prop_boostMode.getValue().get("None")) {
        		type = boostType.NONE;  
        	} else {
        		boost = mc.thePlayer.onGround;

        	}
        	if (prop_boostMode.getValue().get("Damage")) {
                hasDamaged = false;
    			damageboost = mc.thePlayer.isMoving();
        		type = boostType.DAMAGE;
        		timerspeed = 2f;
        	}
        	if (prop_boostMode.getValue().get("Timer")) {
        		type = boostType.TIMER;
        		timerspeed = 3f;
        	}
        	if (prop_boostMode.getValue().get("No Damage")) {
        		type = boostType.NODAMAGE;        		
        		if (boost) {
            		timerspeed = 1.9f;
        		}
        	}
        		
    	break;
    	
    	case "Sentinel":  
    		sendC04(0.5900000333786011, false);
    	break;
    	
    	case "Iris":
    	
    	break;
    }
    	
    	
    }

    @Collect
    public void onProcessPacket(ProcessPacketEvent event) {	
    }
	@Collect
	public void onSendPacket(SendPacketEvent sendPacketEvent) {
	}
	
    @Collect
    public void onPlayerMove(PlayerMoveEvent event) {

        float yaw = mc.thePlayer.rotationYaw;
        double strafe = mc.thePlayer.movementInput.moveStrafe;
        double forward = mc.thePlayer.movementInput.moveForward;
        double mx = -Math.sin(Math.toRadians(yaw)), mz = Math.cos(Math.toRadians(yaw));
        boolean hasSpeed = mc.thePlayer.isPotionActive(Potion.moveSpeed);
    	switch (prop_mode.getSelectedStrings().get(0)) {
	    	case "Watchdog":
 
	    		switch (type) {
	    			case DAMAGE:
	    	            if (damageboost) {
	    	                if (mc.thePlayer.onGround && timer2.hasPassed(250) && !hasDamaged) {
	    	                	if (mc.thePlayer.onGround) {
	    		                    timer2.reset();
            	                    for (int i = 0; i <= (48); i++) {
            	                    	sendC04(.06230141967535, false);
            	                    	sendC04(.001074127387255, false);
            	                    	sendC04(.050999999046326, false);
            	                    }
	    		                    sendC04(0, true);
	    		        			mc.thePlayer.setPosition(mc.thePlayer.posX, positions[1] + .5, mc.thePlayer.posZ);
	    	                        mc.thePlayer.capabilities.isFlying = true;
	    	                        mc.thePlayer.sendPlayerAbilities();
	    	                        mc.thePlayer.capabilities.isFlying = false;
	    		                    mc.thePlayer.triggerAchievement(StatList.jumpStat);
	    		                    hasDamaged = true;
	    		                    boostTimer.reset();
	    	                	} else if (mc.thePlayer.fallDistance >= 2.5) {
	    	                        hasDamaged = true;
	    	                	}


	    	                } else if (!timer2.hasPassed(250)) {
	    	                    event.setX(mc.thePlayer.motionX = 0.0);
	    	                    event.setZ(mc.thePlayer.motionZ = 0.0);
	    	                }
	    	                if (hasDamaged) {
	    	                	double boost = 1;
		    	                	
		    	                    if (stage == 1) {
		    	                   	 	moveSpeed = (1.8)* (getBaseMoveSpeed());
		    	                    } else if (stage == 2) {
		    	                    	moveSpeed *= 2.1499;
		    	                    } else if (stage == 3) {                	
		    	                    	double difference = (hasSpeed ? .1 : .000015) * (lastDist - getBaseMoveSpeed());
		    	                    	moveSpeed = lastDist - difference;
		    	                    } else { 
		    	                    	moveSpeed = lastDist - lastDist / 159;
		    	                    }
		    	                    stage++;
		                            if (stage > 10) {
		                            	if (mc.thePlayer.ticksExisted % 20 == 0) {
		                            		timerspeed = 2f;
		                            	} else {
		                            		if (timerspeed > 1.0) timerspeed -= .15;
		                            	}
		                            	mc.timer.timerSpeed = timerspeed;
		                            }
		    	                 

		                            event.setMoveSpeed(Math.max(moveSpeed, getBaseMoveSpeed()));	
	    	                }
	    	            }
	    				break;
	    			case NODAMAGE:
                        if (boost) {
                            if (stage == 1) {
                                moveSpeed = (1.34) * getBaseMoveSpeed();
                            } else if (stage == 2) {
                                moveSpeed *= hasSpeed ? 2.12 : 2.13;
                            } else if (stage == 3) {
                                moveSpeed = lastDist - (0.66 * (lastDist - getBaseMoveSpeed()));
                            } else {
                                moveSpeed = lastDist - lastDist / 159;
                            }
                            stage++;
                            if (stage > 5) {
	                            
		    					if (timerspeed <= 1.0f || stage > 35) {
		    						mc.timer.timerSpeed = 1.0f;
		    					} else {
		    						if (stage < 25) {
		    							timerspeed = 1.0f + (stage * (.01f + stage * .00125f));
		    						} else {
		    							timerspeed -= .15;
		    						}
		    						mc.timer.timerSpeed = timerspeed;
		    					}
                            }
                            event.setMoveSpeed(Math.max(moveSpeed, getBaseMoveSpeed()));
                            if (timer.hasPassed(2100) && boost) {
                                boost = false;
                            } 
                        }
	    				break;
	    			case TIMER:
	    				if (!timer.hasPassed(5000)) {
	    					timerspeed -= .025f;
	    					if (timerspeed <= 1.0f) {
	    						mc.timer.timerSpeed = 1.0f;
	    						timer.reset();
	    						boost = false;
	    					} else {
		    					mc.timer.timerSpeed = timerspeed;
	    					}
	    				} else {
	    					timer.reset();
	    					boost = false;
	    				}
	    				break;
				case NONE:
					break;
				default:
					break;
	    		}
	    	break;
	    	
	    	case "Sentinel":
	    		event.setMoveSpeed(mc.thePlayer.ticksExisted % 3 != 0 ? 2.3 : 0);
	 
	    	break;
	    	
	    	case "Falcon":
	    		event.setMoveSpeed(mc.thePlayer.ticksExisted % 4 == 0 ? 1.2 : mc.thePlayer.ticksExisted %  3 == 0 ? .22f : .15);
	    	break;
	    }
    }
    
	@Collect
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        setMode(prop_mode.getSelectedStrings().get(0));

		
        switch (prop_mode.getSelectedStrings().get(0)) {
        	case "Watchdog": 
        		mc.thePlayer.onGround = true;
        		mc.thePlayer.motionY = 0;

                if (event.isPre()) { 
            	    double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
                    double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
                    lastDist = Math.sqrt((xDist * xDist) + (zDist * zDist));
                    if (!hasDamaged && type == boostType.DAMAGE) return;
                    if (counter == 1) {
                        event.setOnGround(true);
                        mc.thePlayer.setPosition(mc.thePlayer.posX,positions[1] + MathUtils.getRandomInRange(0.5000000315555, 0.5000000345), mc.thePlayer.posZ);
                        mc.thePlayer.capabilities.isFlying = true;
                        mc.thePlayer.sendPlayerAbilities();
                        mc.thePlayer.capabilities.isFlying = false;
                    } else if (counter == 2) {
                        event.setOnGround(false);
            			mc.thePlayer.setPosition(mc.thePlayer.posX,positions[1] + 0.5, mc.thePlayer.posZ);
                        mc.thePlayer.capabilities.isFlying = true;
                        mc.thePlayer.sendPlayerAbilities();
                        mc.thePlayer.capabilities.isFlying = false;
                    } else if (counter >= 3) {
                        event.setOnGround(true);
            			mc.thePlayer.setPosition(mc.thePlayer.posX,positions[1] + 0.5, mc.thePlayer.posZ);
                        mc.thePlayer.capabilities.isFlying = true;
                        mc.thePlayer.sendPlayerAbilities();
                        mc.thePlayer.capabilities.isFlying = false;
            			counter = 0;
                    }
                    counter++;
                }
        	break;
        	
        	case "Sentinel":
        		mc.thePlayer.motionX = mc.thePlayer.motionZ = 0;
        		if (!handleInput()) { 
            		mc.thePlayer.motionY = 0;
        		}
        		mc.timer.timerSpeed = .3f;
        		event.setOnGround(true);
        		if (event.isPre()) {
        			counter++;
        			List<Double> delay = Arrays.asList(0.0, 0.27000001072883606, 0.27000001072883606, 0.27000001072883606, 0.5400000214576721, 0.5400000214576721, 0.5400000214576721);
        			if (counter >= delay.size()) {
        				counter = 0; 
        			}
          			event.setPosY(mc.thePlayer.posY + delay.get(counter));
        		}
        		

        	break;
        	
        	case "AAC":
        	
        	break;
        	
        	case "Vanilla":
        		mc.thePlayer.onGround = true;
        		mc.thePlayer.motionY = 0;
        		if (!handleInput()) { 
            		mc.thePlayer.motionY = 0;
        		}
        	break;
        	case "Falcon":
        		if (mc.thePlayer.motionY == 0) {
            		event.setOnGround(mc.thePlayer.ticksExisted % 10 == 0);
        		} else {
        			event.setOnGround(true);
        		}
        		if (event.isPre()) {
        			event.setPosY(mc.thePlayer.posY + yPos);
        		}
        		if (counter < 4) {
        			yPos *= .98f;
        		} else if (counter >= 4) {
        			boost = !boost;
        			yPos = boost ? .375 : .625;
        			counter = 0;
        		}
    			counter++;
        		if (!handleInput()) { 
            		mc.thePlayer.motionY = 0;
        		} else {
        			yPos = 0;
        			if (mc.thePlayer.ticksExisted % 3 == 0) {
        				mc.thePlayer.motionY = -.5;
        			}
        		}
        	break;
        }
    }
	
	private boolean handleInput() {
		if (mc.gameSettings.keyBindJump.isKeyDown()) { 
			mc.thePlayer.motionY += .42;
			return true;
		} else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
			mc.thePlayer.setSneaking(false);
			mc.thePlayer.motionY -=.42;
            float f = mc.thePlayer.rotationYaw * 0.017453292F;
            mc.thePlayer.motionX -= (double) (MathHelper.sin(f) * 0.20F);
            mc.thePlayer.motionZ += (double) (MathHelper.cos(f) * 0.20F);
            MovementInputFromOptions mov = new MovementInputFromOptions(mc.gameSettings);
            mov.value = 1;
			return true;
		} else {
			return false;
		}
	}
	
	private void reset() {
		counter = 0;
	}
}