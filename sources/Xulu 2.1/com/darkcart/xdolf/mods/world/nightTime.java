package com.darkcart.xdolf.mods.world;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.entity.EntityPlayerSP;

public class nightTime extends Module {
	
	public nightTime()
	{
		super("nightTime", "New", "Forces the Client-Time to get set to Night.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.WORLD);
	}
	

	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			Wrapper.getWorld().setWorldTime(13000);
		}
	}

	@Override
	public void onDisable() {
	}
}
