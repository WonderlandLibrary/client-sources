package com.darkcart.xdolf.mods.render;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

public class AntiTotemAnimation extends Module {

	public AntiTotemAnimation() {
		super("AntiTotemAnimation", "Allows you to still see when Totem of Undying activates.", Keyboard.KEYBOARD_SIZE,
				0xffffff, Category.RENDER);
	}

}
