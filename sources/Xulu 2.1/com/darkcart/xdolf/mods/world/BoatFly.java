package com.darkcart.xdolf.mods.world;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class BoatFly extends Module {
	
	public BoatFly()
	{
		super("boatFly", "Old", "Allows you to fly with Boats/horses.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.WORLD);
	}
	

	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			if(!Wrapper.getPlayer().isRiding())
				return;
			Wrapper.getPlayer().getRidingEntity().motionY =
				Minecraft.getMinecraft().gameSettings.keyBindJump.pressed ? 1.04 : 0;
		}
	}

	@Override
	public void onDisable() {
	}
}
