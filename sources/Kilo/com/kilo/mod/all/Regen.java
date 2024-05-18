package com.kilo.mod.all;

import net.minecraft.item.ItemFood;
import net.minecraft.network.play.client.C03PacketPlayer;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.Util;

public class Regen extends Module {
	
	public Regen(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
		
		addOption("Speed", "Regen speed", Interactable.TYPE.SLIDER, 0, new float[] {0, 10}, false);
	}
	
	public void update() {
		if (!active) { return; }

		if (mc.thePlayer.getHealth() < 20 && mc.thePlayer.getFoodStats().getFoodLevel() >= 19) {
			for(int i = 0; i < Util.makeFloat(getOptionValue("speed"))*10; i++) {
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
			}
		}
	}
}
