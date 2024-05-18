package com.darkcart.xdolf.mods.render;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

public class OutlineESP extends Module {
	
	public static boolean active;

	public OutlineESP() {
		super("outlineESP", "Invisible", "Draws lines around players/mobs.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.RENDER);
	}
	
	public void onUpdate() {
		if(isEnabled()) {
		}
	}
	
	@Override
	public void onDisable() {
		active = false;
	}
	
	@Override
	public void onEnable() {
		active = true;
	}
}