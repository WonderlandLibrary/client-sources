package com.darkcart.xdolf.mods.player;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class SpeedMine extends Module
{
	public SpeedMine()
	{
		super("fastbreak", "NoCheat+", "Allows you to break blocks 3x faster.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.PLAYER);
	}
	
	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			 if (Minecraft.getMinecraft().playerController.curBlockDamageMP > 0.8F) {
			      Minecraft.getMinecraft().playerController.curBlockDamageMP = 1.0F;
			    }
			    Minecraft.getMinecraft().playerController.blockHitDelay = 0;
		}
	}
}