package com.darkcart.xdolf.mods.render;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

public class Chams extends Module {
	
	public Chams() {
		super("Chams", "Renders players through walls.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.RENDER);
	}
}