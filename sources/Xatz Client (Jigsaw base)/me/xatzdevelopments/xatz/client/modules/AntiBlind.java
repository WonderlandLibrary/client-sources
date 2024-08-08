package me.xatzdevelopments.xatz.client.modules;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.module.Module;
import net.minecraft.potion.Potion;

public class AntiBlind extends Module {

	public AntiBlind() {
		super("AntiBlind", Keyboard.KEY_NONE, Category.COMBAT, "Removes Blind effect");
	}

	@Override
	public void onDisable() {

		super.onDisable();
	}

	@Override
	public void onEnable() {

		super.onEnable();
	}

	@Override
	public void onUpdate() {
		/* if (mc.thePlayer.isPotionActive(Potion.getPotionById(9))) {
			mc.thePlayer.removeActivePotionEffect(Potion.getPotionById(9));
		}

		if (mc.thePlayer.isPotionActive(Potion.getPotionById(15))) {
			mc.thePlayer.removeActivePotionEffect(Potion.getPotionById(15));
		} */  // This module is acting very Jewish

		super.onUpdate();
	}

}
