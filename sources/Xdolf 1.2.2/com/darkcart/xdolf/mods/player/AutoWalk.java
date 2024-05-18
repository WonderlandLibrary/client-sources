package com.darkcart.xdolf.mods.player;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.entity.EntityPlayerSP;

public class AutoWalk extends Module {
	
	public AutoWalk() {
		super("AutoWalk", "Automatically walks for you.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.PLAYER);
	}

	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			Wrapper.getMinecraft().gameSettings.keyBindForward.pressed = true;
		}
	}
	
	@Override
	public void onDisable() {
		Wrapper.getMinecraft().gameSettings.keyBindForward.pressed = 
				Keyboard.isKeyDown(Wrapper.getMinecraft().gameSettings.keyBindForward.getKeyCode());
	}
}
