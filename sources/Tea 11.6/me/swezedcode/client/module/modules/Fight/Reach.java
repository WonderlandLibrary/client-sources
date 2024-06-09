package me.swezedcode.client.module.modules.Fight;

import org.lwjgl.input.Keyboard;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;

public class Reach extends Module {

	public Reach() {
		super("Reach", Keyboard.KEY_NONE, 0xFFFF75DA, ModCategory.Fight);
	}

	public static float reach = 3.4F;
	
}
