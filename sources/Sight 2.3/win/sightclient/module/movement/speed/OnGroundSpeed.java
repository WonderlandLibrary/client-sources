package win.sightclient.module.movement.speed;

import net.minecraft.network.play.client.C03PacketPlayer;
import win.sightclient.event.Event;
import win.sightclient.event.events.client.EventUpdate;
import win.sightclient.event.events.player.EventFlag;
import win.sightclient.event.events.player.EventMove;
import win.sightclient.module.Module;
import win.sightclient.module.ModuleMode;
import win.sightclient.utils.minecraft.MoveUtils;

public class OnGroundSpeed extends ModuleMode {

	private int stage;
	private double lastDist;
	private double moveSpeed;
	
	public OnGroundSpeed(Module parent) {
		super(parent);
	}

	@Override
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			EventUpdate eu = (EventUpdate)e;
			
			if (eu.isPre()) {
				if (!mc.thePlayer.onGround) {
					this.stage = 0;
				} else {
					//eu.setOnGround(false);
					mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(false));
				}
		        final double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
		        final double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
		        this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
			}
		} else if (e instanceof EventMove) {
			EventMove em = (EventMove)e;
			
			this.stage++;
			if (this.stage > 10) {
				this.stage = 0;
			} else {
				if (stage == 2) {
					mc.timer.timerSpeed = 1.5F;
					this.moveSpeed = MoveUtils.getBaseSpeed() * 1.3;
				} else {
					mc.timer.timerSpeed = 1F;
					this.moveSpeed -= this.moveSpeed / 59;
				}
			}
			
			MoveUtils.setMotion(em, Math.max(this.moveSpeed, MoveUtils.getBaseSpeed()));
			this.stage++;
		} else if (e instanceof EventFlag) {
			this.parent.setToggled(false);
		}
	}
	
	@Override
	public void onEnable() {
		this.lastDist = 0;
		this.moveSpeed = 0;
		this.stage = 0;
	}
}
