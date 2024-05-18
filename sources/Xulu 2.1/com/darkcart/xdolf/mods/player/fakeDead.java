package com.darkcart.xdolf.mods.player;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class fakeDead extends Module {
	
	public fakeDead() {
		super("fakeDead", "Old", "Freezes you.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.PLAYER);
	}
	
	@Override
	public void onDisable(){
		Minecraft.getMinecraft().player.isDead=false;
	}
	
	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()){
			player.isDead=true;
        }
	}
}
