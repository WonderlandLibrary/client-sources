package Snipeware.module.impl.movement;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.client.*;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.potion.*;
import net.minecraft.entity.*;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.client.entity.*;
import org.apache.commons.lang3.RandomUtils;

import Snipeware.Client;
import Snipeware.api.annotations.Handler;
import Snipeware.events.Event;
import Snipeware.events.packet.EventPacketReceive;
import Snipeware.events.packet.EventPacketSend;
import Snipeware.events.player.EventMotionUpdate;
import Snipeware.events.player.EventMove;
import Snipeware.events.player.EventStep;
import Snipeware.gui.notification.Notifications;
import Snipeware.module.Module;
import Snipeware.util.other.Logger;
import Snipeware.util.other.MathUtils;
import Snipeware.util.other.PlayerUtil;
import Snipeware.util.other.TimeHelper;
import Snipeware.util.player.MovementUtils;
import Snipeware.value.impl.BooleanValue;
import Snipeware.value.impl.EnumValue;
import Snipeware.value.impl.NumberValue;

public class Speed extends Module {

	private double nextMotionSpeed;
	private double xMotionSpeed;
	private double zDist;
	private double moveSpeed;
	int stage;
	int ncpStage;
	int WatchdogStage;
	int posY;
	int posYGround;
	public boolean reset, doSlow;

	private NumberValue<Integer> vanillaSpeed = new NumberValue<>("Vanilla Speed", 4, 1, 10, 1);

	public NumberValue<Float> redeSpeed = new NumberValue<>("Rede Speed", 1.2f, 1.1f, 2f, 0.1f);
	 public final EnumValue<Redemode> redemode = new EnumValue<>("Rede mode", Redemode.Bhop);

	private BooleanValue flagbackcheck = new BooleanValue("Flagback Check", true);


	private EnumValue<Mode> mode = new EnumValue<>("Speed Mode", Mode.Watchdog);
	
	public 	int stageRede = 1;
	
	private final TimeHelper Stagetimer = new TimeHelper();
	private final TimeHelper WatchdogTimer = new TimeHelper();

	private TargetStrafe ts;
	private int ticks;
	public Speed() {
		super("Speed", 0, ModuleCategory.MOVEMENT);
		addValues(mode, vanillaSpeed , redeSpeed ,redemode, flagbackcheck);
		stage = 0;
	}

	private enum Mode {
	 Legit, Watchdog, NCP, Strafe, Vanilla, Redesky, Mineplex;
	}
	
	 public enum Redemode {
	        Bhop,
	        Ground,
	        Test
	    }
	 
	 @Handler
	 public void aa(EventPacketSend event){
		 /*
			if(mode.getValueAsString() == "Strafe") {
				if (event.getPacket() instanceof C03PacketPlayer) {
		            C03PacketPlayer packetPlayer = (C03PacketPlayer) event.getPacket();
		            double x = mc.thePlayer.posX;
		            double y = mc.thePlayer.posY;
		            double z = mc.thePlayer.posZ;
		            float yaw = mc.thePlayer.rotationYaw;
		            float pitch = mc.thePlayer.rotationPitch;
		            boolean ground = packetPlayer.onGround;
		            if (packetPlayer.isMoving()) {
		                x = packetPlayer.getPositionX();
		                y = packetPlayer.getPositionY();
		                z = packetPlayer.getPositionZ();
		            }

		            if (packetPlayer.getRotating()) {
		                yaw = packetPlayer.getYaw();
		                pitch = packetPlayer.getPitch();
		            }

		            event.setPacket(new C03PacketPlayer.C06PacketPlayerPosLook(x, y, z, yaw, pitch, ground));
		        }
			}
			*/
	 }

	@Handler
	public void onMove(final EventMove event) {
		if (PlayerUtil.isOnLiquid() && Client.INSTANCE.getModuleManager().getModule("liquidwalk").isEnabled())
			return;
		switch (mode.getValue()) {

			case Mineplex: {
                mc.timer.timerSpeed = 1.0f;
                if (mc.thePlayer.isMovingOnGround()) {
                    //mc.timer.timerSpeed = 2.48F;
                    if (reset)
                        moveSpeed = 0.95F;
                    else
                        moveSpeed += MathUtils.getRandomInRange(0.38, 0.6);
                    doSlow = true;
                    reset = false;
                    event.setY(mc.thePlayer.motionY = 0.40F);
                    MovementUtils.setSpeed(event, 0.00001);
                 
                } else {

                    if (doSlow)
						nextMotionSpeed = moveSpeed;
                    doSlow = false;
                    if (moveSpeed <= 0.8F)
                        moveSpeed = nextMotionSpeed - 0.01F;
                    else if (moveSpeed < 2.2F)
                        moveSpeed = nextMotionSpeed * 0.9823F;
                    else
                        moveSpeed = nextMotionSpeed * 0.97F;
                    moveSpeed = Math.min(moveSpeed + 0.053123F, moveSpeed);
                    moveSpeed = Math.max(moveSpeed, MovementUtils.getSpeed() + 0.1628F);
                    MovementUtils.setSpeed(event, moveSpeed);
                }
				break;
			}
		case Watchdog: {
			if (mc.thePlayer.isMoving()) {
                if (mc.thePlayer.onGround) {
                    mc.thePlayer.jump();
                    if(mc.thePlayer.isPotionActive(Potion.moveSpeed.id)) {
                        setSpeed((0.45));
                    }else {
                        setSpeed((0.38));
                    }
                    ticks = 0;
                } else {
                    if(mc.thePlayer.isPotionActive(Potion.moveSpeed.id)) {
                        if (ticks == 0) setSpeed((0.43));
                        ticks++;
                    }else {
                        if (ticks == 0) setSpeed((0.31));
                        ticks++;
                    }
                }
            }
			break;
		}
		case Vanilla: {
			MovementUtils.setSpeed(event, vanillaSpeed.getValue());
			break;
		}
		case Strafe:{
			if(mc.thePlayer.onGround) {
				mc.thePlayer.motionY = 0.42f;
				MovementUtils.setSpeed(event, MovementUtils.getSpeed() * 1.05 + Math.random() / 100);
			}else {
				MovementUtils.setSpeed(event, MovementUtils.getSpeed() * 0.93 + Math.random() / 100);
			}
		break;
		} 
		case NCP: { 
			if(mc.thePlayer.isMoving2()) {
				mc.gameSettings.keyBindJump.pressed = true;
				
			}else{
				mc.gameSettings.keyBindJump.pressed = false;
			}
			if (!PlayerUtil.isInLiquid() && mc.thePlayer.isCollidedVertically && MovementUtils.isOnGround(0.01)
					&& (mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f)) {
				ncpStage= 0;
				
				if (ncpStage < 4)
					ncpStage++;
			}
			moveSpeed = getAACSpeed(ncpStage, stage) + 0.009;
			if ((mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f)) {
				if (PlayerUtil.isInLiquid()) {
					moveSpeed = 0.2;
				}
				MovementUtils.setSpeed(event, moveSpeed);
			}

			if (mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f) {
				++ncpStage;
			}
			break;
		}
		
		case Redesky: {
			
			if (this.redemode.getValue() == Redemode.Bhop) {
		
			mc.gameSettings.keyBindSprint.pressed = true;
			
			if(mc.thePlayer.isMoving()) {
			mc.gameSettings.keyBindJump.pressed = true;
			}else {
				mc.gameSettings.keyBindJump.pressed = false;
			}
			
			mc.timer.timerSpeed = redeSpeed.getValue();
		
			}else if(this.redemode.getValue() == Redemode.Ground) {
				
			
				
			if(mc.thePlayer.onGround && mc.thePlayer.isMoving() && !mc.thePlayer.isCollidedHorizontally) {
				
				if(stageRede == 1) {
					mc.timer.timerSpeed = 1.1f;
						if(Stagetimer.reach(700)) {
							stageRede = 2;
							Stagetimer.reset();
							 
						}
					}else if(stageRede == 2) {
					mc.timer.timerSpeed = 1.0f;
					if(Stagetimer.reach(400)) {
						stageRede = 3;
						Stagetimer.reset();
					
					}
				}else if(stageRede == 3) {
					mc.timer.timerSpeed = 1.4f;
					if(Stagetimer.reach(400)) {
						stageRede = 4;
						Stagetimer.reset();
						
					
					}
				}else if(stageRede == 4) {
					stageRede = 1;
					Stagetimer.reset();
				}
			}else {
				mc.timer.timerSpeed = 1;
			}
			}
			break;
		}
	
	case Legit:{
			
			mc.gameSettings.keyBindSprint.pressed = true;
		
			if(mc.thePlayer.isMoving2()) {
			
			mc.gameSettings.keyBindJump.pressed = true;
			
			}else {
			
				mc.gameSettings.keyBindJump.pressed = false;
			}
			
		
			
			break;
		}
		}
	
	}
	
    public void setSpeed(double speed) {
        speed = (float) speed;
        mc.thePlayer.motionX = (-(Math.sin(getDirection()) * speed));
        mc.thePlayer.motionZ = (Math.cos(getDirection()) * speed);
    }
    
    public double getDirection() {
        float rotationYaw = mc.thePlayer.rotationYaw;

        if (mc.thePlayer.moveForward < 0F)
            rotationYaw += 180F;

        float forward = 1F;
        if (mc.thePlayer.moveForward < 0F)
            forward = -0.5F;
        else if (mc.thePlayer.moveForward > 0F)
            forward = 0.5F;

        if (mc.thePlayer.moveStrafing > 0F)
            rotationYaw -= 90F * forward;

        if (mc.thePlayer.moveStrafing < 0F)
            rotationYaw += 90F * forward;

        return Math.toRadians(rotationYaw);
    }
	
	
	private double getAACSpeed(int stage, int jumps) {
        double value = 0.19;
        double firstvalue = 0.2019;
        double thirdvalue = 0.0286 - (double) stage / 1000;
        if (stage == 0) {
            //JUMP
            value = 0.497;
            if (jumps >= 2) {
                value += 0.1069;
            }
            if (jumps >= 3) {
                value += 0.046;
            }
        } else if (stage == 1) {
            value = 0.3031;
            if (jumps >= 2) {
                value += 0.0642;
            }
            if (jumps >= 3) {
                value += thirdvalue;
            }
        } else if (stage == 2) {
            value = 0.302;
            if (jumps >= 2) {
                value += 0.0629;
            }
            if (jumps >= 3) {
                value += thirdvalue;
            }
        } else if (stage == 3) {
            value = firstvalue;
            if (jumps >= 2) {
                value += 0.0607;
            }
            if (jumps >= 3) {
                value += thirdvalue;
            }
        } else if (stage == 4) {
            value = firstvalue;
            if (jumps >= 2) {
                value += 0.0584;
            }
            if (jumps >= 3) {
                value += thirdvalue;
            }
        } else if (stage == 5) {
            value = firstvalue;
            if (jumps >= 2) {
                value += 0.0561;
            }
            if (jumps >= 3) {
                value += thirdvalue;
            }
        } else if (stage == 6) {
            value = firstvalue;
            if (jumps >= 2) {
                value += 0.0539;
            }
            if (jumps >= 3) {
                value += thirdvalue;
            }
        } else if (stage == 7) {
            value = firstvalue;
            if (jumps >= 2) {
                value += 0.0517;
            }
            if (jumps >= 3) {
                value += thirdvalue;
            }
        } else if (stage == 8) {
            value = firstvalue;
            if (MovementUtils.isOnGround(0.05))
                value -= 0.002;

            if (jumps >= 2) {
                value += 0.0496;
            }
            if (jumps >= 3) {
                value += thirdvalue;
            }
        } else if (stage == 9) {
            value = firstvalue;
            if (jumps >= 2) {
                value += 0.0475;
            }
            if (jumps >= 3) {
                value += thirdvalue;
            }
        } else if (stage == 10) {

            value = firstvalue;
            if (jumps >= 2) {
                value += 0.0455;
            }
            if (jumps >= 3) {
                value += thirdvalue;
            }
        } else if (stage == 11) {

            value = 0.3;
            if (jumps >= 2) {
                value += 0.045;
            }
            if (jumps >= 3) {
                value += 0.018;
            }

        } else if (stage == 12) {
            value = 0.301;
            if (jumps <= 2)
                stage = 0;
            if (jumps >= 2) {
                value += 0.042;
            }
            if (jumps >= 3) {
                value += thirdvalue + 0.001;
            }
        } else if (stage == 13) {
            value = 0.298;
            if (jumps >= 2) {
                value += 0.042;
            }
            if (jumps >= 3) {
                value += thirdvalue + 0.001;
            }
        } else if (stage == 14) {

            value = 0.297;
            if (jumps >= 2) {
                value += 0.042;
            }
            if (jumps >= 3) {
                value += thirdvalue + 0.001;
            }
        }
        if (mc.thePlayer.moveForward <= 0) {
            value -= 0.06;
        }

        if (mc.thePlayer.isCollidedHorizontally) {
            value -= 0.1;
            stage = 0;
        }
        return value;
    }
	
	@Handler
	public void onReceivePacket(final EventPacketReceive event) {
		if (event.getPacket() instanceof S08PacketPlayerPosLook && flagbackcheck.isEnabled()) {
			toggle();
			Notifications.getManager().post("Disabled Modules", "Speed was disabled to prevent flags/errors.");
		}

	}
	

	@Override
	public void onEnable() {
		WatchdogTimer.reset();
		super.onEnable();
		if (mc.thePlayer == null)
			return;
		if (mc.thePlayer != null) {
			moveSpeed = MovementUtils.getSpeed();
		}
		WatchdogStage = 0;
		nextMotionSpeed = 0.0;
		doSlow = false;
		reset = false;
		stage = 2;
		mc.timer.timerSpeed = 1.0f;
		if (ts == null) {
			ts = (TargetStrafe) Client.INSTANCE.getModuleManager().getModule("targetstrafe");
		}
		ncpStage = 0;
	}

	@Override
	public void onDisable() {
		WatchdogTimer.reset();
		super.onDisable();
		mc.gameSettings.keyBindJump.pressed = false;
		mc.gameSettings.keyBindSprint.pressed = false;
		doSlow = false;
		reset = false;
		mc.timer.timerSpeed = 1;
		switch (mode.getValue()) {
		case Watchdog:
		
			
			break;
		}
		
	
	}

	@Handler
	public void onMotionUpdate(final EventMotionUpdate event) {
		setSuffix(mode.getValueAsString());
		switch (mode.getValue()) {
		case Watchdog:
		if(mc.thePlayer.isMoving() && mc.thePlayer.onGround) {
			mc.gameSettings.keyBindJump.pressed = true;
		}
				break;
		case Vanilla:
			break;
		case Strafe:
			if(mc.thePlayer.isMoving()) {
				if(mc.thePlayer.onGround) {
					mc.gameSettings.keyBindJump.pressed = true;
				}else {
					mc.gameSettings.keyBindJump.pressed = false;
				}
			}
			
			
			break;
		case NCP:
			break;
		
		default:
			break;
		}
	}
}
