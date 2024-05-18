package com.darkcart.xdolf.mods.world;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Client;
import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;

public class Fullbright extends Module {
	
	public Fullbright()
	{
		super("Fullbright", "Turns on NightVision effect to brighten the world.", Keyboard.KEY_C, Category.WORLD);
	}

	@Override
	public void onDisable() {
		try {
			if(Wrapper.getPlayer().isPotionActive(Potion.getPotionById(16)))
				Wrapper.getPlayer().removeActivePotionEffect(Potion.getPotionById(16));
		}catch(Exception ex) {}
	}

	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			if(player != null && !player.isPotionActive(Potion.getPotionById(16))) {
				PotionEffect nightVision = new PotionEffect(Potion.getPotionById(16), Integer.MAX_VALUE, 0);
				nightVision.setPotionDurationMax(true);
				player.addPotionEffect(nightVision);
			}
		}
	}
}
