package com.kilo.mod.all;

import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.network.play.client.C03PacketPlayer;

import org.lwjgl.input.Keyboard;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.Util;

public class FastBow extends Module {
	
	public FastBow(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);

		addOption("Power", "How far the bow is pulled", Interactable.TYPE.SLIDER, 10, new float[] {0, 20}, false);
		addOption("Bow Damage", "Use with Bow Damage too", Interactable.TYPE.CHECKBOX, false, null, false);
	}
	
	public void update() {
		if (!active) { return; }
		
		if (Util.makeBoolean(getOptionValue("bow damage"))) {
			if (mc.thePlayer.getItemInUse() != null && mc.thePlayer.getItemInUse().getItem() instanceof ItemBow && mc.thePlayer.getItemInUseDuration() == 5) {
				mc.playerController.onStoppedUsingItem(mc.thePlayer);
			}
		} else {
			if (mc.thePlayer.getItemInUse() != null && mc.thePlayer.getItemInUse().getItem() instanceof ItemBow) {
				for(int i = 0; i < Util.makeInteger(getOptionValue("power")); i++) {
					mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
				}
				mc.playerController.onStoppedUsingItem(mc.thePlayer);
			}
		}
	}
}
