package com.darkcart.xdolf.mods.aura;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

public class Reach extends Module {

	public static boolean reach;
	
	public Reach() {
		super("reach", "Old", "Puts your hit reach [Mouse click reach] to 7.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF,
				Category.COMBAT);
	}

	@Override
	public void onEnable() {
		this.reach = true;
		super.onEnable();
	}

	@Override
	public void onDisable() {
		this.reach = false;
		super.onDisable();
	}
}