package com.darkcart.xdolf.mods.movement;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.entity.EntityPlayerSP;

public class SlowMotion extends Module {
	
	public SlowMotion() {
		super("slowMotion", "NoCheat+", "Slows down the game speed to 0.2f.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.MOVEMENT);
	}

	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			net.minecraft.util.Timer.timerSpeed = 0.2f;
		}
	}
	
	@Override
	public void onDisable() {
		net.minecraft.util.Timer.timerSpeed = 1;
	}
}
