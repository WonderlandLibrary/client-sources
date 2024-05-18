package com.darkcart.xdolf.mods.render;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

public class NoHurtCam extends Module {

	public NoHurtCam() {
		super("noHurtcam", "New", "Disables the HurtCam animation when damage is taken.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.RENDER);
	}
}
