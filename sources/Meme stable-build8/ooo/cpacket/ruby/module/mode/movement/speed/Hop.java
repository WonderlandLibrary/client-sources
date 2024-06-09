package ooo.cpacket.ruby.module.mode.movement.speed;

import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import ooo.cpacket.ruby.api.event.EventImpl;
import ooo.cpacket.ruby.api.event.events.network.EventPacket;
import ooo.cpacket.ruby.api.event.events.player.EventMotionUpdate;
import ooo.cpacket.ruby.api.event.events.player.EventRawMove;
import ooo.cpacket.ruby.api.event.events.player.motion.State;
import ooo.cpacket.ruby.module.move.Speed;
import ooo.cpacket.ruby.util.SpeedUtils;

public class Hop extends SpeedMode {

	public Hop(Speed module, String name) {
		super(module, "Hypixel");
	}

	@Override
	@EventImpl
	public void move(EventRawMove e) {
		SpeedUtils.setMoveSpeed(e, ROLF.moveSpeed);
	}

	@Override
	@EventImpl
	public void tick(EventMotionUpdate e) {
		if (e.getState() == State.PRE) {
			mc.timer.timerSpeed = 0.9f;
			if (mc.thePlayer.isMoving() && mc.thePlayer.onGround) {
				mc.thePlayer.motionY = 0.42;
				mc.thePlayer.sendQueue.addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX + 0.001, mc.thePlayer.posY + 0.42, mc.thePlayer.posZ + 0.001, true));
				mc.thePlayer.sendQueue.addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX + 0.001, mc.thePlayer.posY + 1.82E-14, mc.thePlayer.posZ + 0.001, true));
				ROLF.moveSpeed = SpeedUtils.getBaseMoveSpeed() + 0.42;
			}
			if (ROLF.moveSpeed > 0.305) {
				ROLF.moveSpeed -= 0.04058;
			}
		}
	}

	@Override
	@EventImpl
	public void packet(EventPacket e) {
	}

	@Override
	public void onEnable() {

	}

	static class $1 {
		public static double delay$0;
	}

	static class ROLF {
		public static double stage;
		public static double moveSpeed;
		public static double lastDistance;
	}

	@Override
	public void onDisable() {
		mc.timer.timerSpeed = 1.0f;
	}

}
