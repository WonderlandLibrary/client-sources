package com.darkcart.xdolf.mods.player;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Category;
import com.darkcart.xdolf.util.Value;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class AntiHunger extends Module
{

	public AntiHunger()
	{
		super("antiHunger","NoCheat+", "Drains less hunger", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.PLAYER);
	}
	
	@Override
	public void onDisable() {
		try {
			if(Wrapper.getPlayer().isPotionActive(Potion.getPotionById(23)))
				Wrapper.getPlayer().removeActivePotionEffect(Potion.getPotionById(23));
		}catch(Exception ex) {}
	}
	
	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			player.onGround=false;
			if(player != null && !player.isPotionActive(Potion.getPotionById(23))) {
				PotionEffect saturation = new PotionEffect(Potion.getPotionById(23), Integer.MAX_VALUE, 0);
				saturation.setPotionDurationMax(true);
				player.addPotionEffect(saturation);
			}
		}
	}
}
