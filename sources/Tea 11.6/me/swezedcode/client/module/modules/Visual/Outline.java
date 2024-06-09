package me.swezedcode.client.module.modules.Visual;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;

public class Outline extends Module {

	public static boolean enabled;
	
	public Outline() {
		super("Wireframe", Keyboard.KEY_NONE, 0xFF83E026, ModCategory.Visual);
	}
	
	@Override
	public void onEnable() {
		enabled = true;
	}
	
	@Override
	public void onDisable() {
		enabled = false;
	}
	
}
