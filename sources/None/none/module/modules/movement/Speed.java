package none.module.modules.movement;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.block.Block;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.MathHelper;
import net.minecraft.world.HWID;
import none.Client;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventMove;
import none.event.events.EventPacket;
import none.event.events.EventPreMotionUpdate;
import none.event.events.EventStep;
import none.module.Category;
import none.module.Module;
import none.module.modules.combat.Killaura;
import none.notifications.Notification;
import none.notifications.NotificationType;
import none.utils.MoveUtils;
import none.utils.PlayerUtil;
import none.utils.TimeHelper;
import none.utils.Utils;
import none.utils.Block.BlockUtils;
import none.valuesystem.BooleanValue;
import none.valuesystem.ModeValue;

public class Speed extends Module{
	private static String[] modes = {"Hypixel", "AAC364"};
	public static ModeValue speedmode = new ModeValue("Speed-Mode", "Hypixel", modes);

	private BooleanValue autojump = new BooleanValue("AutoJump", true);
	private BooleanValue hurttime = new BooleanValue("HT", true);
	public static BooleanValue novelocity = new BooleanValue("NoVelocity", false);
	private BooleanValue lagback = new BooleanValue("LagBack", true);
	
	public Speed() {
		super("Speed", "Speed", Category.MOVEMENT, Keyboard.KEY_M);
	}
	
	private float air, ground, aacSlow;
    public boolean shouldslow = false;
    double count = 0;
    int jumps;
    public static TimeHelper timer = new TimeHelper();
    boolean collided = false, lessSlow;
    int spoofSlot = 0;
    double less, stair;
	
	private double speed, speedvalue;
    private double lastDist;
    public static int stage, aacCount;
    private double[] motion = {1.0064, 1.0037, 1.0045, 1.0052};
    TimeHelper aac = new TimeHelper();
    TimeHelper lastFall = new TimeHelper();
    TimeHelper lastCheck = new TimeHelper();
    
    @Override
    protected void onEnable() {
    	super.onEnable();
    	
    	if (mc.thePlayer == null) return;
    	collided = mc.thePlayer.isCollidedHorizontally;
        spoofSlot = mc.thePlayer.inventory.currentItem;
        lessSlow = false;
        
        speed = MoveUtils.defaultSpeed();
        less = 0;
        jumps = 0;
        mc.timer.timerSpeed = 1;
        air = 0;
        
        if (speedmode.getSelected().equalsIgnoreCase("Hypixel") && !HWID.isHWID()) {
			evc("Premium Only");
			Client.instance.notification.show(Client.notification("Premium", "You are not Premium", 3));
			setState(false);
			return;
		}
    }
    
    @Override
    protected void onDisable() {
    	super.onDisable();
    	
    	aacCount = 0;
        mc.timer.timerSpeed = 1;
    }
    
	@Override
	@RegisterEvent(events = {EventPreMotionUpdate.class, EventPacket.class, EventMove.class, EventStep.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		if (speedmode.getSelected().equalsIgnoreCase("Hypixel") && !HWID.isHWID()) {
			return;
		}
		
		setDisplayName(this.getName() + ChatFormatting.WHITE + " " + speedmode.getSelected());
		
		if (event instanceof EventStep) {
            EventStep es = (EventStep) event;
            if (!es.isPre()) {
                double height = mc.thePlayer.getEntityBoundingBox().minY - mc.thePlayer.posY;
                if (height > 0.7) {
                    less = 0;
                }
                if (height == 0.5)
                    stair = 0.75;
            }
        }
		
		if (event instanceof EventMove)
		{
			EventMove em = (EventMove) event;
			if (speedmode.getSelected().equalsIgnoreCase("Hypixel")) {
				if (mc.thePlayer.isCollidedHorizontally) {
                    collided = true;
                }
                if (collided) {
                    mc.timer.timerSpeed = 1;
                    stage = -1;
                }
                if (stair > 0)
                    stair -= 0.25;
                less -= less > 1 ? 0.12 : 0.11;
                if (less < 0)
                    less = 0;
                if (!BlockUtils.isInLiquid() && MoveUtils.isOnGround(0.01) && (PlayerUtil.isMoving2())) {
                    collided = mc.thePlayer.isCollidedHorizontally;
                    if (stage >= 0 || collided) {
                        stage = 0;

                        double motY = 0.407 + MoveUtils.getJumpEffect() * 0.1;
                        if (stair == 0 && MoveUtils.isMoveKeyPressed()) {
                            mc.thePlayer.jump();
                            em.setY(mc.thePlayer.motionY = motY);
                        } else {

                        }

                        less++;
                        if (less > 1 && !lessSlow)
                            lessSlow = true;
                        else
                            lessSlow = false;
                        if (less > 0.97)
                            less = 0.97;
                    }
                }
                speed = getHypixelSpeed(stage) + 0.0331;
                speed *= 0.91;
                if (stair > 0) {
                    speed *= 0.7 - MoveUtils.getSpeedEffect() * 0.1;
                }

                if (stage < 0)
                    speed = MoveUtils.defaultSpeed();
                if (lessSlow) {
                    speed *= 0.95;
                }


                if (BlockUtils.isInLiquid()) {
                    speed = 0.12;
                }
                if (MoveUtils.isMoveKeyPressed()) {
                    setMotion(em, speed);
                    ++stage;
                }
			}else if (speedmode.getSelected().equalsIgnoreCase("AAC364")) {

				if (mc.thePlayer.hurtTime != 0 && hurttime.getObject()) 
					return;
				
				if (!MoveUtils.isMoveKeyPressed()) {
					aacCount = 0;
					return;
				}
				
				if (mc.thePlayer.fallDistance > 1.2) {
                    lastFall.setLastMS();
                }
                if (mc.thePlayer.isCollidedVertically && MoveUtils.isOnGround(0.01)) {
                    stage = 0;
                    double motY = 0.407 + MoveUtils.getJumpEffect() * 0.1;
//                    mc.thePlayer.setSprinting(false);
                    mc.thePlayer.jump();
                    mc.thePlayer.setSprinting(true);
//                    em.setY(mc.thePlayer.motionY = motY);
                    em.setY(mc.thePlayer.motionY = 0.42);
                    if (aacCount < 4)
                        aacCount++;
                }
//                evc("" + aacCount + ":" + stage);
                speed = getAACSpeed(stage, aacCount);
//                if (MoveUtils.isMoveKeyPressed()) {
//                    if (BlockUtils.isInLiquid()) {
//                        speed = 0.075;
//                    }
                    setMotion(em, speed);
//                }

//                if (MoveUtils.isMoveKeyPressed()) {
                    stage++;
//                }
			}
		}
		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate e = (EventPreMotionUpdate) event;
			if (e.isPre()) {
				if (speedmode.getSelected().equalsIgnoreCase("Seksin")) {
					mc.gameSettings.keyBindJump.pressed = false; 
	                if (mc.thePlayer.onGround) {
	                	if (MoveUtils.isMoveKeyPressed()) { 
	                		mc.thePlayer.jump(); 
	                	} 
	 
	                	if (mc.gameSettings.keyBindForward.pressed) { 
	                		mc.thePlayer.setSprinting(true); 
	                	}
	                	mc.thePlayer.landMovementFactor = 0.0265F; 
	                	mc.thePlayer.jumpMovementFactor = 0.0275F; 
	                } else { 
	                	if (mc.gameSettings.keyBindForward.pressed) { 
	                        mc.thePlayer.setSprinting(true); 
	                	} 
	 
	                    mc.thePlayer.landMovementFactor = 0.0265F; 
	                    mc.thePlayer.jumpMovementFactor = 0.0275F; 
	                }
				}else if (speedmode.getSelected().equalsIgnoreCase("Cubecraft")) {
//					boolean boost = Math.abs(mc.thePlayer.rotationYawHead - mc.thePlayer.rotationYaw) < 90;
					
					if ((hurttime.getObject() && mc.thePlayer.hurtTime < 5) || !hurttime.getObject()) {
						if (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown()) {
							if (mc.thePlayer.onGround) {
				                if (autojump.getObject()) {
//				                	if (Client.instance.moduleManager.getModulesbycls(Scaffold.class).isEnabled()) {
				                		mc.thePlayer.setSprinting(false);
//				                	}
				                	mc.thePlayer.jump();
				                }
//				                mc.thePlayer.setSprinting(true);
				                mc.thePlayer.setSprinting(true);
				                float f = (float)Utils.getDirection();
//				                mc.thePlayer.motionX -= (double)(MathHelper.sin(f) * 0.25F);
//				                mc.thePlayer.motionZ += (double)(MathHelper.cos(f) * 0.25F);
				                mc.thePlayer.motionX -= (double)(MathHelper.sin(f) * 0.2F);
				                mc.thePlayer.motionZ += (double)(MathHelper.cos(f) * 0.2F);
				            } else {
				                double currentSpeed = Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
				                //Default 1.0064 : 1.001
				                double speed = 1.0064;

				                double direction = Utils.getDirection();
				                mc.thePlayer.motionX = -Math.sin(direction) * speed * currentSpeed;
				                mc.thePlayer.motionZ = Math.cos(direction) * speed * currentSpeed;
				            }
				        }
					}
				}
			}
		}
		
		if (event instanceof EventPacket) {
			EventPacket ep = (EventPacket) event;
            Packet p = ep.getPacket();
            if (ep.isIncoming()) {
                if (p instanceof S08PacketPlayerPosLook) {

                    aacCount = 0;
                    count = 0;
                    jumps = -3;
                    S08PacketPlayerPosLook pac = (S08PacketPlayerPosLook) ep.getPacket();
                    if (lagback.getObject()) {
                        Client.instance.notification.show(new Notification(NotificationType.WARNING, getName(), " Disabled Speed by LagBack", 5));
                        mc.thePlayer.onGround = false;
                        mc.thePlayer.motionX *= 0;
                        mc.thePlayer.motionZ *= 0;
                        mc.thePlayer.jumpMovementFactor = 0;
                        this.toggle();
                    } else if (lastCheck.hasTimeReached(300)) {
                        pac.yaw = mc.thePlayer.rotationYaw;
                        pac.pitch = mc.thePlayer.rotationPitch;
                    }
                    stage = -4;
                    lastCheck.setLastMS();
                }
            }
		}
	}
	
	private void setMotion(EventMove em, double speed) {
        double forward = mc.thePlayer.movementInput.moveForward;
        double strafe = mc.thePlayer.movementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;
        if ((forward == 0.0D) && (strafe == 0.0D)) {
            em.setX(0.0D);
            em.setZ(0.0D);
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
            
            if (Client.instance.moduleManager.killaura.isEnabled() && Killaura.targeter != null && Killaura.rotationStrafeValue.getObject()) {
            	yaw = Killaura.rotations[0];
            }
            em.setX(forward * speed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F)));
            em.setZ(forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F)));
        }
    }
    private double getHypixelSpeed(int stage) {
        double value = MoveUtils.defaultSpeed() + (0.028 * MoveUtils.getSpeedEffect()) + (double) MoveUtils.getSpeedEffect() / 15;
        double firstvalue = 0.4145 + (double) MoveUtils.getSpeedEffect() / 12.5;
        double decr = (((double) stage / 500) * 2);


        if (stage == 0) {
            //JUMP
            if (timer.hasTimeReached(300)) {
                timer.setLastMS();
                //mc.timer.timerSpeed = 1.354f;
            }
            if (!lastCheck.hasTimeReached(500)) {
                if (!shouldslow)
                    shouldslow = true;
            } else {
                if (shouldslow)
                    shouldslow = false;
            }
            value = 0.64 + (MoveUtils.getSpeedEffect() + (0.028 * MoveUtils.getSpeedEffect())) * 0.134;

        } else if (stage == 1) {
            if (mc.timer.timerSpeed == 1.354f) {
                //mc.timer.timerSpeed = 1.254f;
            }
            value = firstvalue;
        } else if (stage >= 2) {
            if (mc.timer.timerSpeed == 1.254f) {
                //mc.timer.timerSpeed = 1f;
            }
            value = firstvalue - decr;
        }
        if (shouldslow || !lastCheck.hasTimeReached(500) || collided) {
            value = 0.2;
            if (stage == 0)
                value = 0;
        }


        return Math.max(value, shouldslow ? value : MoveUtils.defaultSpeed() + (0.028 * MoveUtils.getSpeedEffect()));
    }
    
    private double getAACSpeed(int stage, int jumps) {
        double value = 0.29;
        double firstvalue = 0.3019;
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
            Block block = MoveUtils.getBlockUnderPlayer(mc.thePlayer, 0.01);
            if (block instanceof BlockIce || block instanceof BlockPackedIce) {
                value = 0.59;
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
            if (MoveUtils.isOnGround(0.05))
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
                aacCount = 0;
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
            aacCount = 0;
        }
        return value;
    }
}
