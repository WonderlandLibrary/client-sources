package com.darkcart.xdolf.mods.render;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

public class NoPumpkinBlur extends Module {
	
	public NoPumpkinBlur() {
		super("NoPumpkinBlur", "Removes the pumpkin overlay.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.RENDER);
	}
	
	// NOTE: Code is in GuiIngame, line 160.
}
