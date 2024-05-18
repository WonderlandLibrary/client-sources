package com.darkcart.xdolf.mods.render;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class AACJesus extends Module {
//I was too lazy to put in the "Player" category, gotta do it later 
	public AACJesus() {
		super("aacJesus", "Idk", "A [possibly] working Jesus for AAC aka Shit AntiCheat.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF,
				Category.RENDER);
	}

	@Override
	public void onUpdate(EntityPlayerSP player) {
		if (isEnabled()) {
			if (Minecraft.player.isInWater()) {
				Minecraft.player.motionY = 5.9D;
			}
			EntityPlayerSP thePlayer = Minecraft.player;
			thePlayer.jumpMovementFactor *= 0.9F;
			if (Minecraft.player.isInWater()) {
				Minecraft.player.motionY = 0.2D;
			}
		}
	}
}