package com.darkcart.xdolf.mods.movement;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class LongJump extends Module {
	
	public LongJump() {
		super("longJump", "Broken", "World's most simple Longjump.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.MOVEMENT);
	}

	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			if(Minecraft.getMinecraft().gameSettings.keyBindForward.pressed || Minecraft.getMinecraft().gameSettings.keyBindBack.pressed || Minecraft.getMinecraft().gameSettings.keyBindLeft.pressed || Minecraft.getMinecraft().gameSettings.keyBindRight.pressed){
				if(player.isAirBorne){
					player.motionX *= 1.11f;
					player.motionZ *= 1.11f;
				}
			}else{
				player.motionX *= 1f;
				player.motionZ *= 1f;
			}
		}
	}
	
	@Override
	public void onDisable() {
	}
}
