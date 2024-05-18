package me.swezedcode.client.module.modules.Visual;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;

public class NoFov extends Module {

	public static boolean enabled;
	
	public NoFov() {
		super("NoFov", Keyboard.KEY_NONE, 0xFFFF8340, ModCategory.Visual);
	}
	
	@Override
	public void onEnable() {
		setEnabled(true);
	}
	
	@Override
	public void onDisable() {
		setEnabled(false);
	}
	
	public static boolean isEnabled() {
		return enabled;
	}
	
	public static void setEnabled(boolean enabled) {
		NoFov.enabled = enabled;
	}
	
}
