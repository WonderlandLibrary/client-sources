package ooo.cpacket.ruby.module.move;

import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import ooo.cpacket.ruby.api.event.EventImpl;
import ooo.cpacket.ruby.api.event.events.player.EventMotionUpdate;
import ooo.cpacket.ruby.api.event.events.player.motion.State;
import ooo.cpacket.ruby.module.Module;
import ooo.cpacket.ruby.util.SpeedUtils;

public class HighJump extends Module {

	public HighJump(String name, int key, Category category) {
		super(name, key, category);
	}

	@Override
	public void onEnable() {
//		mc.thePlayer.sendQueue.addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
	}

	@Override
	public void onDisable() {

	}

	@EventImpl
	public void onUpdate(EventMotionUpdate e) {
		if (e.getState() == State.PRE) {
			if (mc.thePlayer.onGround) {
				mc.timer.timerSpeed = 1.f;
				mc.thePlayer.motionY = 2.09;
			}
			if (mc.thePlayer.fallDistance >= 0.95) {
				mc.thePlayer.motionY = 0.4;
				SpeedUtils.setMoveSpeed(3.0);
				mc.thePlayer.fallDistance = 0;
				mc.timer.timerSpeed = 1f;
			}
//			if (mc.thePlayer.ticksExisted % 2 == 0)
//			mc.thePlayer.motionY += 0.12229299;
		}
	}

}
