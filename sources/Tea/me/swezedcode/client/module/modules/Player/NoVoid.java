package me.swezedcode.client.module.modules.Player;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;

public class NoVoid extends Module {

	public NoVoid() {
		super("NoVoid", Keyboard.KEY_NONE, 0xF1AC73A, ModCategory.Player);
		setDisplayName("No Void");
	}

	@EventListener
	public void onFall(EventPreMotionUpdates e) {
		if (mc.thePlayer.onGround) {
			return;
		}
		if (!mc.thePlayer.onGround && mc.thePlayer.fallDistance >= 4.0F) {
			mc.thePlayer.motionY = 0.1D;
		}
	}

}
