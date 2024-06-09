package ooo.cpacket.ruby.module.mode.movement.speed;

import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import ooo.cpacket.ruby.api.event.EventImpl;
import ooo.cpacket.ruby.api.event.events.network.EventPacket;
import ooo.cpacket.ruby.api.event.events.player.EventMotionUpdate;
import ooo.cpacket.ruby.api.event.events.player.EventRawMove;
import ooo.cpacket.ruby.api.event.events.player.motion.State;
import ooo.cpacket.ruby.module.move.Speed;
import ooo.cpacket.ruby.util.SpeedUtils;

public class AAC extends SpeedMode {

	public AAC(Speed module, String name) {
		super(module, name);
	}

	@Override
	@EventImpl
	public void move(EventRawMove e) {
	}

	@Override
	@EventImpl
	public void tick(EventMotionUpdate e) {
		if (e.getState() == State.POST)
			return;
		if (mc.thePlayer.onGround && mc.thePlayer.isMoving()) {
			mc.thePlayer.motionY = 0.4;
		}
		mc.thePlayer.onGround = true;
		SpeedUtils.setMoveSpeed(0.8199999);

		// TODO make faster speed
		if (((Speed) this.getModule()).timer.hasReached(3400L)) {
			mc.thePlayer.sendQueue.addToSendQueue(
					new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
			mc.thePlayer.sendQueue
					.addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 24, mc.thePlayer.posZ, true));
			((Speed) this.getModule()).timer.reset();
		}

	}

	@Override
	@EventImpl
	public void packet(EventPacket e) {
	}

	@Override
	public void onEnable() {
	}

	@Override
	public void onDisable() {
		mc.timer.timerSpeed = 1;

	}

}
