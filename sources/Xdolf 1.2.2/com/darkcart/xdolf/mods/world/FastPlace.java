package com.darkcart.xdolf.mods.world;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Client;
import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.entity.EntityPlayerSP;

public class FastPlace extends Module {
	
	public FastPlace() {
		super("FastPlace", "Allows you to quickly place blocks.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.WORLD);
	}

	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled())
        {
			Wrapper.getMinecraft().rightClickDelayTimer = 0;
        }
	}
}