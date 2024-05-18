package com.darkcart.xdolf.mods.player;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class roofClip extends Module {
	/**
	 * This code was from 2hacks2exploits by 2F4U.
	 */
	public roofClip() {
		super("roofClip", "Broken", "Attempts to clip thro the nether roof/other blocks. Requires you to stand in a door.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.PLAYER);
	}

	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			player.noClip = true;
		      if (Minecraft.getMinecraft().gameSettings.keyBindJump.isPressed())
		      {
		        Double x = Double.valueOf(player.posX);
		        Double y = Double.valueOf(player.posY);
		        Double z = Double.valueOf(player.posZ);
		        
		        player.posY = (y.doubleValue() + 0.01D);
		      }
		      if (!Minecraft.getMinecraft().gameSettings.keyBindJump.isPressed())
		      {
		        Double x = Double.valueOf(player.posX);
		        Double y = Double.valueOf(player.posY);
		        Double z = Double.valueOf(player.posZ);
		        
		        player.posY = player.posY+1.2;
	      }
	    }
	}
	
	@Override
	public void onEnable() {
	}
}
