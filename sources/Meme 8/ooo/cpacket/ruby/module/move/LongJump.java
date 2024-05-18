package ooo.cpacket.ruby.module.move;

import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import ooo.cpacket.ruby.api.event.EventImpl;
import ooo.cpacket.ruby.api.event.events.player.EventMotionUpdate;
import ooo.cpacket.ruby.api.event.events.player.EventRawMove;
import ooo.cpacket.ruby.api.event.events.player.motion.State;
import ooo.cpacket.ruby.module.Module;
import ooo.cpacket.ruby.util.SpeedUtils;
import ooo.cpacket.ruby.util.Timer;
import ooo.cpacket.ruby.util.motionstuff.SpeedValue;

public class LongJump extends Module {

	private Timer timer = new Timer();
	private SpeedValue speed = new SpeedValue(0.02873f, 0.0326f);

	public LongJump(String name, int key, Category category) {
		super(name, key, category);
		this.addModes("Cubeshit", "NCP");
	}

	double lastDist, stage, moveSpeed;

	@Override
	public void onEnable() {
		this.speed = new SpeedValue((float) (SpeedUtils.getBaseMoveSpeed() + 0.312), 0.035626f);
		stage = 1;
		this.moveSpeed = SpeedUtils.getBaseMoveSpeed();
	}

	@Override
	public void onDisable() {
	}

	@EventImpl
	public void onMove(EventMotionUpdate e) {
		double xd = mc.thePlayer.posX - mc.thePlayer.prevPosX;
		double lol = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
		lastDist = Math.sqrt(xd * xd + lol * lol);
		if (e.getState() != State.PRE) {
			return;
		}
		if (mc.thePlayer.onGround && mc.thePlayer.ticksExisted % 24 == 0) {
			mc.thePlayer.motionY += 0.2;
		}
		mc.thePlayer.onGround = true;
		SpeedUtils.setMoveSpeed(0.17);
		if (this.isMode("Mineplex")) {
			if (e.getState() == State.PRE) {
				if (mc.thePlayer.onGround && mc.thePlayer.isMoving()) {
					mc.thePlayer.motionY = 0.405;
				}
			}
			if (e.getState() == State.POST) {
				mc.thePlayer.onGround = true;
			}
		} else {
			if (e.getState() == State.PRE) {
				if (mc.thePlayer.onGround && mc.thePlayer.isMoving() && mc.thePlayer.ticksExisted % 2 != 0) {
					// e.setPosY(e.getPosY() + 0.4);
				}
			}
		}
	}

	static class bool {
		public static boolean bool = false;
	}

	@EventImpl
	public void onMove(EventRawMove e) {
		if (this.isMode("Guardian")) {
			if (this.timer.hasReached(1000L)) {
				mc.thePlayer.sendQueue.addToSendQueue(
						new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
				mc.thePlayer.sendQueue.addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX,
						mc.thePlayer.posY + 15.4922, mc.thePlayer.posZ, true));
				this.timer.reset();
			}
			SpeedUtils.setMoveSpeed(4.4);
		}
		if (this.isMode("CubeShit")) {
			mc.thePlayer.onGround = true;
		} else if (this.isMode("Mineplex")) {
			SpeedUtils.setMoveSpeed(e, 0.492);
			mc.thePlayer.onGround = true;
		}
	}

}
