package me.swezedcode.client.module.modules.Motion;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.events.EntityStep;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;
import me.swezedcode.client.utils.events.TickEvent;
import me.swezedcode.client.utils.timer.TimerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Step extends Module {

	private boolean procced;
	private double yPos;

	public Step() {
		super("Step", Keyboard.KEY_NONE, 0xFF22B5E6, ModCategory.Motion);
	}

	@EventListener
	public void onPreMotions(EventPreMotionUpdates event) {
		if (!(mc.thePlayer.isCollidedHorizontally)) {
			mc.timer.timerSpeed = 1;
		}
		if(mc.thePlayer.motionY >= 0.381) {
			return;
		}
		if ((mc.thePlayer.isCollidedHorizontally) && (!mc.thePlayer.isSneaking()) && mc.thePlayer.fallDistance == 0.0) {
			if (TimerUtils.hD(4.9)) {
				mc.thePlayer.motionY = 0.381;
				mc.timer.timerSpeed = 2;
				TimerUtils.rt();
			}
		}
	}

	private boolean isGoodForStepMyDudes() {
		if ((mc.thePlayer.isCollidedHorizontally) && (!mc.thePlayer.isSneaking())) {
			return true;
		}
		return false;
	}

	@Override
	public void onDisable() {
		mc.thePlayer.stepHeight = 0.5F;
		mc.timer.timerSpeed = 1.0F;
	}

}
