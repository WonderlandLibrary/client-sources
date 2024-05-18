package com.darkcart.xdolf.mods.aura;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Client;
import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

public class AntiVelocity extends Module {
	
	public AntiVelocity() {
		super("AntiVelocity", "Blocks velocity.", Keyboard.KEY_L, 0xFFFFFF, Category.COMBAT);
	}
}
