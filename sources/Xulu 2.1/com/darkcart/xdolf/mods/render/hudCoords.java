package com.darkcart.xdolf.mods.render;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.entity.EntityPlayerSP;

public class hudCoords extends Module {

	public hudCoords() {
		super("hud", "Coordinates", "Displays your coordinates.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.RENDER);
	}

	@Override
	public void onUpdate(EntityPlayerSP player) {
		if (isEnabled()) {
			
		}
	}

}
