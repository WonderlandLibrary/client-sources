package com.darkcart.xdolf.mods.player;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

public class SafeWalk extends Module {
	public SafeWalk() {
		super("SafeWalk", "Prevents you from falling off of blocks.", Keyboard.KEY_F4, 0xFFFFFF, Category.PLAYER);
	}
}