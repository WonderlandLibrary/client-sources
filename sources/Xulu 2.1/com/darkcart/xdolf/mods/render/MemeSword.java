package com.darkcart.xdolf.mods.render;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.datafix.fixes.MinecartEntityTypes;


public class MemeSword extends Module{

	public MemeSword() {
		super("highFOW", "Gives you braindamage.", "Lowers camera view.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.RENDER);
	}
	
	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			Minecraft.getMinecraft().player.cameraPitch = 15.0f;
		}
	}
	
}
