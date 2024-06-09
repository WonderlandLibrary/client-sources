package me.swezedcode.client.module.modules.World;

import org.lwjgl.input.Keyboard;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;

public class MaxRotations extends Module {

	public MaxRotations() {
		super("MaxRotations", Keyboard.KEY_NONE, 0xFF87ED1A, ModCategory.World);
	}

	public static boolean enabled;
	
	public void onEnable() {
		this.enabled = true;
	}
	
	public void onDisable() {
		this.enabled = false;
	}

}
