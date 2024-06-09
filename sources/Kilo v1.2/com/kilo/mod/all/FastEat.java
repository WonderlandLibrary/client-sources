package com.kilo.mod.all;

import net.minecraft.item.ItemFood;
import net.minecraft.network.play.client.C03PacketPlayer;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.util.Util;

public class FastEat extends Module {
	
	public FastEat(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
	}
	
	public void update() {
		if (!active) { return; }
		
		if (mc.thePlayer.getItemInUse() != null && mc.thePlayer.getItemInUse().getItem() instanceof ItemFood && mc.thePlayer.getItemInUseDuration() == 15) {
			for(int i = 0; i < 60; i++) {
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
			}
			mc.playerController.onStoppedUsingItem(mc.thePlayer);
		}
	}
}
