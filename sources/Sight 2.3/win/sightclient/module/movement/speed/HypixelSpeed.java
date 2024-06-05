package win.sightclient.module.movement.speed;

import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.network.play.client.C03PacketPlayer;
import win.sightclient.Sight;
import win.sightclient.event.Event;
import win.sightclient.event.events.client.EventUpdate;
import win.sightclient.event.events.player.EventFlag;
import win.sightclient.event.events.player.EventMove;
import win.sightclient.module.Module;
import win.sightclient.module.ModuleMode;
import win.sightclient.module.combat.TargetStrafe;
import win.sightclient.notification.Notification;
import win.sightclient.utils.TimerUtils;
import win.sightclient.utils.minecraft.MoveUtils;

public class HypixelSpeed extends ModuleMode{

	public HypixelSpeed(Module parent) {
		super(parent);
	}

	private TargetStrafe targetStrafe;
	private double lastDist;
	private double moveSpeed;
	private int stage;
	private boolean lowHop;
	private int jumpStage;
	private TimerUtils jump = new TimerUtils();
	
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			EventUpdate eu = (EventUpdate)e;
			
			if (eu.isPre()) {
		        final double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
		        final double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
		        this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
			}
		} else if (e instanceof EventMove) {
			if (this.targetStrafe == null) {
				this.targetStrafe = (TargetStrafe) Sight.instance.mm.getModuleByName("TargetStrafe");
			}
			EventMove em = (EventMove)e;
			
			if (mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically && MoveUtils.isMoving() && jump.hasReached(200)) {
				jumpStage++;
				if (jumpStage > 4) {
					jumpStage = 0;
				}
				lowHop = ThreadLocalRandom.current().nextBoolean();
				em.setY(mc.thePlayer.motionY = MoveUtils.getJumpBoost(lowHop ? 0.39999998688697816 : 0.41999998688697816));
	            if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0, mc.thePlayer.motionY, 0.0)).size() != 0 || lowHop) {
	            	mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 1e-15, mc.thePlayer.posZ, true));
	            	
	            }
	            jump.reset();
	        }
        	switch (stage) {
        	case 0:
        		stage++;
        		lastDist = 0;
                if (MoveUtils.isMoving()) {
                    mc.timer.timerSpeed = 1.1F;
                }
        		break;
        	case 1:
        		this.moveSpeed = MoveUtils.getBaseSpeed();
                if (MoveUtils.isMoving()) {
                    mc.timer.timerSpeed = 1.15F;
                }
        		break;
        	case 2:
        		lastDist = 0;
                moveSpeed *= lowHop ? 1.1 : 1.45;
                if (MoveUtils.isMoving()) {
                    mc.timer.timerSpeed = 1.054F;
                }
        		break;
        	case 3:
        		moveSpeed = lastDist - ((lowHop ? 0.75 : 0.675) * (lastDist - MoveUtils.getBaseSpeed()));
        		mc.timer.timerSpeed = 1F;
        		break;
        	default:
        		if ((mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0, mc.thePlayer.motionY, 0.0)).size() > 0 && !mc.thePlayer.isCollidedVertically) && stage > 0) {
                    stage = (MoveUtils.isMoving() ? 1 : 0);
                }
        		if (MoveUtils.isMoving() && this.stage < 8) {
        			mc.timer.timerSpeed = 1.085F;
        		} else {
        			mc.timer.timerSpeed = 1F;
        		}
        		this.moveSpeed = this.lastDist - this.lastDist / ((MoveUtils.getSpeedEffect() > 0 ? 130 : 160 - 1E-7) - (lowHop ? 20 : 50));
                break;
        	}
        	this.moveSpeed = Math.min(Math.max(this.moveSpeed, MoveUtils.getBaseSpeed()), MoveUtils.getBaseSpeed() * 1.8);
        	if (mc.thePlayer.fallDistance > 3) {
        		this.moveSpeed = MoveUtils.getBaseSpeed();
        	} else if (!mc.thePlayer.onGround && mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.boundingBox.offset(mc.thePlayer.motionX, Math.max(0, mc.thePlayer.motionY), mc.thePlayer.motionZ)).size() != 0 && !mc.thePlayer.isCollidedHorizontally) {
        		this.moveSpeed = MoveUtils.getBaseSpeed();
        		jump.reset();
        	}
	    	if (this.targetStrafe.canStrafe()) {
	    		this.targetStrafe.strafe(em, moveSpeed);
	    	} else {
	    		if (mc.thePlayer.isCollidedHorizontally) {
					MoveUtils.setMotion(em, MoveUtils.getBaseSpeed());
	    		} else {
	    			MoveUtils.setMotion(em, moveSpeed, 0.95);
	    		}
	    	}
			this.stage++;
		} else if (e instanceof EventFlag) {
			Sight.instance.nm.send(new Notification("Speed", "Speed was disabled to prevent future flags."));
			this.parent.setToggled(false);
		}
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		this.lastDist = 0;
		this.moveSpeed = 0;
		this.stage = 0;
	}
}
