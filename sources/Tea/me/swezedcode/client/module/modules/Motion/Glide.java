package me.swezedcode.client.module.modules.Motion;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;
import me.swezedcode.client.utils.timer.TimerUtils;
import me.swezedcode.client.utils.values.BooleanValue;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Glide extends Module {

	public Glide() {
		super("Glide", Keyboard.KEY_NONE, 0xFFFF1CE5, ModCategory.Motion);
	}

	private BooleanValue damage = new BooleanValue(this, "Take damage", "", Boolean.valueOf(false));

	@EventListener
	public void onFlight(EventPreMotionUpdates e) {
		if (mc.thePlayer.onGround == false) {
			if (mc.thePlayer.isMoving()) {
				mc.thePlayer.setSpeed(mc.thePlayer.ticksExisted % 2 == 0 ? 0.15f * 2.32f : 0.15f);
			}
			mc.timer.timerSpeed = 2F;
			if (TimerUtils.hD(0.1F)) {
				mc.thePlayer.motionY = -0.05D;
				TimerUtils.rt();
			} else {
				mc.thePlayer.motionY = 0.0D;
			}
			if (mc.thePlayer.onGround == false) {
				mc.thePlayer.motionY = -9.E-006D;
				mc.thePlayer.setMotionY(-9.E-006D);
			}
		}
	}

	@Override
	public void onEnable() {
		mc.thePlayer.motionY = 0.01D;
		if (damage.getValue()) {
			for (int i = 0; i < 64; i++) {
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
						mc.thePlayer.posY + 0.05D, mc.thePlayer.posZ, false));
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
						mc.thePlayer.posY, mc.thePlayer.posZ, false));
			}
			mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
					mc.thePlayer.posX + 2.147483647E9D, mc.thePlayer.posY + 2.147483647E9D,
					mc.thePlayer.posZ + 2.147483647E9D, mc.thePlayer.onGround));
		}
	}

	@Override
	public void onDisable() {
		mc.timer.timerSpeed = 1.0F;
	}

}
