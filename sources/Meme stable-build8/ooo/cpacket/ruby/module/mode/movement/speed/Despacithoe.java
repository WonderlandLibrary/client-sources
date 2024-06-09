package ooo.cpacket.ruby.module.mode.movement.speed;

import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import ooo.cpacket.ruby.api.event.EventImpl;
import ooo.cpacket.ruby.api.event.events.network.EventPacket;
import ooo.cpacket.ruby.api.event.events.player.EventMotionUpdate;
import ooo.cpacket.ruby.api.event.events.player.EventRawMove;
import ooo.cpacket.ruby.api.event.events.player.motion.State;
import ooo.cpacket.ruby.module.move.Speed;
import ooo.cpacket.ruby.util.SpeedUtils;
import ooo.cpacket.ruby.util.Timer;

public class Despacithoe extends SpeedMode {

	public Despacithoe(Speed module, String name) {
		super(module, name);
	}

	@EventImpl
	@Override
	public void move(EventRawMove e) {
		
	}
	@EventImpl
	@Override
	public void tick(EventMotionUpdate e) {
		if (e.getState() != State.PRE)
			return;
		SpeedUtils.setMoveSpeed(0.9);
		if (Timer2.timer.hasReached(1000)) {
			mc.thePlayer.sendQueue
					.addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX + mc.thePlayer.motionX,
							mc.thePlayer.posY, mc.thePlayer.posZ + mc.thePlayer.motionZ, true));
			mc.thePlayer.sendQueue.addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX,
					mc.thePlayer.posY + 15.4922, mc.thePlayer.posZ, true));
			Timer2.timer.reset();
		}
	}
	static class Timer2 {
		public static Timer timer = new Timer();
	}
	@EventImpl
	@Override
	public void packet(EventPacket e) {
		
	}

	@Override
	public void onEnable() {
		
	}

	@Override
	public void onDisable() {
		
	}

}
