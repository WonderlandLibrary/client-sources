package com.darkcart.xdolf.mods.player;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class haste extends Module {
	
	/**
	 * Code from 2hacks2exploits by 2f4u
	 */
	
	public haste() {
		super("haste", "NoCheat+", "Makes you have Haste IV for a unlimited amount of time.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.PLAYER);
	}

	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			PotionEffect nv = new PotionEffect(Potion.getPotionById(3), 9999999, 3);
			player.addPotionEffect(nv);
		}
	}
	
	@Override
	public void onDisable() {
		Minecraft.player.removeActivePotionEffect(Potion.getPotionById(3));
	}
}
