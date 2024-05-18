package com.darkcart.xdolf.mods.player;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Client;
import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.GameType;

public class creativeMode extends Module {
	
	public creativeMode() {
		super("creativeMode", "New", "Thanks Sleepinqq", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.PLAYER);
	}
	@Override
	public void onDisable(){
		//Minecraft.getMinecraft().playerController.setGameType(GameType.SURVIVAL);
	}
	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled());
		//Minecraft.getMinecraft().playerController.setGameType(GameType.CREATIVE);
	}
}
