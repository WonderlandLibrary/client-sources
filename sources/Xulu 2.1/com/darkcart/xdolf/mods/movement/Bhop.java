package com.darkcart.xdolf.mods.movement;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class Bhop extends Module {
	Minecraft mc;
	public Bhop() {
		super("bhop", "", "I'm a bunny.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.MOVEMENT);
	}

	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()){
			
		if(player.onGround){
			net.minecraft.util.Timer.timerSpeed = 1.1f;
			player.motionX /= 3f;
			player.motionZ /= 3f;
			player.motionY /= 0.01f;
		}else{
			net.minecraft.util.Timer.timerSpeed = 1.1f;
			player.moveStrafing *= 0.1f;
			player.landMovementFactor = 0.045f;
			player.jumpMovementFactor = 0.045f;
		}}

	}
	
	@Override
	public void onDisable() {
		net.minecraft.util.Timer.timerSpeed = 1;
		Wrapper.getPlayer().landMovementFactor = 0.03f;
		Wrapper.getPlayer().jumpMovementFactor = 0.03f;
	}
}
