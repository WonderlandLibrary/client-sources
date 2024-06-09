package ooo.cpacket.ruby.module.move;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import ooo.cpacket.ruby.api.event.EventImpl;
import ooo.cpacket.ruby.api.event.events.network.EventPacket;
import ooo.cpacket.ruby.api.event.events.player.EventMotionUpdate;
import ooo.cpacket.ruby.api.event.events.player.EventRawMove;
import ooo.cpacket.ruby.module.Module;
import ooo.cpacket.ruby.util.SpeedUtils;
import ooo.cpacket.ruby.util.Timer;
import ooo.cpacket.ruby.util.mc.MCTimerHook;

public class Fly extends Module {

	private Timer timer = new Timer();

	public double startY;

	public Fly(String name, int key, Category category) {
		super(name, key, category);
		this.addModes("Hypixel", "HypixelVert", "Guardian", "Vanilla", "Mineplex", "SentinelMeme");
	}

	@Override
	public void onEnable() {
		this.startY = mc.thePlayer.posY;
//		mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 1.4E-7, mc.thePlayer.posZ);
		if (this.isMode("Mineplex")) {
//			MCTimerHook.timerSpeed 5.0D;
		} else if (this.isMode("SentinelMeme")) {
			MCTimerHook.timerSpeed = 1.9D;
		} else if (this.isMode("Guardian")) {
			this.mc.thePlayer.motionY = 0.4;
			this.startY = SpeedUtils.getBaseMoveSpeed() + 1.1;
		}
	}

	@Override
	public void onDisable() {
		mc.timer.timerSpeed = 1.0f;
		if (this.isMode("Guardian")) {
			mc.thePlayer.capabilities.setFlySpeed(0.05F);
			mc.thePlayer.capabilities.isFlying = false;
//			mc.thePlayer.sendQueue.addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX,
//					mc.thePlayer.posY + 15.4922, mc.thePlayer.posZ, true));
		}
	}

	@EventImpl
	public void onPreMotionUpdate(EventRawMove e) {
		if (this.isMode("Mineplex")) {
//			SpeedUtils.setMoveSpeed(e, 0.45);
		} else if (this.isMode("SentinelMeme")) {
			if (mc.thePlayer.ticksExisted % 2 == 0) {
				SpeedUtils.setMoveSpeed(1.8199999999);
			} else {
				SpeedUtils.setMoveSpeed(0.3087299999999);
			}
		}
	}

	@EventImpl
	public void onPreMotionUpdate(EventPacket event) {

	}
	@EventImpl
	public void onPreMotionUpdate(EventMotionUpdate event) {
		if (this.isMode("Hypixel")) {
			this.mc.thePlayer.motionY = 0;
			if (mc.thePlayer.ticksExisted % 2 == 0)
				mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 1.287E-14, mc.thePlayer.posZ);
			else
				mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.287E-14, mc.thePlayer.posZ);
		}
		if (this.isMode("HypixelVert")) {
			//note, damage needed
			boolean lock = (mc.thePlayer.posY + 1) > this.startY;
			if (mc.thePlayer.movementInput.jump) {
				mc.thePlayer.motionY = 0.4;
			} else if (mc.thePlayer.movementInput.sneak) {
				mc.thePlayer.motionY = -0.4;
			} else {
				mc.thePlayer.motionY = 0.0;
			}
			if (mc.thePlayer.rotationPitch <= 0) {
				mc.thePlayer.motionY = 0.0;
				SpeedUtils.setMoveSpeed(mc.thePlayer.ticksExisted % 2 == 0 ? 0.3 : 0.3);
			}
		}
		if (this.isMode("Mineplex")) {
			if (mc.thePlayer.ticksExisted % 2 == 0) {
				event.setPosY(event.getPosY() + 0.0000001);
			}
			if (mc.thePlayer.movementInput.jump) {
				mc.thePlayer.motionY = 2.4;
			} else if (mc.thePlayer.movementInput.sneak) {
				mc.thePlayer.motionY = -2.4;
			} else {
				mc.thePlayer.motionY = 0;
			}
			if (this.timer.hasReached(4000)) {
				mc.thePlayer.sendQueue
				.addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX,
						mc.thePlayer.posY, mc.thePlayer.posZ, true));
		mc.thePlayer.sendQueue.addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 24, mc.thePlayer.posZ, true));
				this.timer.reset();
			}
			SpeedUtils.setMoveSpeed(3.7);
		}
		if (this.isMode("Guardian")) {
			SpeedUtils.setMoveSpeed(4.4);
			if (this.timer.hasReached(3000)) {
				mc.thePlayer.sendQueue
						.addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX,
								mc.thePlayer.posY, mc.thePlayer.posZ, true));
				mc.thePlayer.sendQueue.addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, 1337, mc.thePlayer.posZ, true));
				this.timer.reset();
			}
//			if
			mc.thePlayer.motionY = mc.thePlayer.getFlyMotion();
		} else if (this.isMode("Vanilla")) {
			mc.thePlayer.motionX = 0;
			mc.thePlayer.motionY = mc.thePlayer.getFlyMotion();
			mc.thePlayer.motionZ = 0;
			mc.thePlayer.jumpMovementFactor = 5.0F;
		} else if (this.isMode("Mineplex")) {
//			mc.thePlayer.capabilities.isFlying = true;
//			mc.thePlayer.capabilities.setFlySpeed(0.01f);
		} else if (this.isMode("SentinelMeme")) {
			boolean lock = (mc.thePlayer.posY + 0.1) > this.startY;
			if (mc.thePlayer.movementInput.jump && !lock) {
				EntityPlayerSP h = mc.thePlayer;
				mc.thePlayer.motionY = 0.22;
			} else if (mc.thePlayer.movementInput.sneak) {
				mc.thePlayer.motionY = -0.22;
			} else {
				if (mc.timer.timerSpeed > 1.0) {
					mc.timer.timerSpeed -= 0.02;
				}
				mc.thePlayer.motionY = -0.0392;
			}
		}
	}

}
