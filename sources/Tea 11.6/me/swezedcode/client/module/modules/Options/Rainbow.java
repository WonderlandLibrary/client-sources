package me.swezedcode.client.module.modules.Options;

import org.lwjgl.input.Keyboard;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;

public class Rainbow extends Module {

	public static boolean enabled;
	
	public Rainbow() {
		super("Rainbow Theme", Keyboard.KEY_NONE, 0xFF42DB9E, ModCategory.Options);
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
