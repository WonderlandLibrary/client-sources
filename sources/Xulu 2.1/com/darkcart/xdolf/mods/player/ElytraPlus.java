package com.darkcart.xdolf.mods.player;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class ElytraPlus extends Module {
	
	public ElytraPlus() {
		super("elytraPlus", "New", "Forces you go down really fast.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.PLAYER);
	}

	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			if (Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown()) {
		        Minecraft.getMinecraft().player.setPosition(Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY + 0.40000001192092896D, Minecraft.getMinecraft().player.posZ);
		      }
		      Minecraft.getMinecraft().player.motionY = -1.3D;
		      if (Minecraft.getMinecraft().gameSettings.keyBindSneak.isKeyDown()) {
		        Minecraft.getMinecraft().player.setPosition(Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY - 0.5D, Minecraft.getMinecraft().player.posZ);
		      }
		    }
		  }
	
	@Override
	public void onDisable() {
	}
}
