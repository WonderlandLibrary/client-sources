package com.darkcart.xdolf.mods.render;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.entity.EntityPlayerSP;

public class antiBorder extends Module {

	public antiBorder() {
		super("antiBorder", "Vanilla", "Removes the Minecraft border [Singleplayer] at 30million blocks. Won't let you go thro the border with the colors itself without glitching thro tho.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.RENDER);
	}

	@Override
	public void onUpdate(EntityPlayerSP player) {
		if (isEnabled()) {
		}
	}

}
