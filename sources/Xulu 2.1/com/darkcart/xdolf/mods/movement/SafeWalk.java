package com.darkcart.xdolf.mods.movement;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

public class SafeWalk extends Module {
	public SafeWalk() {
		super("safeWalk", "New", "Prevents you from falling off of blocks.", Keyboard.KEY_NONE, 0xFFFFFF, Category.MOVEMENT);
	}
}