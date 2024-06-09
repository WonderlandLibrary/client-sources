package com.kilo.mod.all;

import net.minecraft.network.play.client.C03PacketPlayer;

import com.kilo.mod.Category;
import com.kilo.mod.Module;

public class NoFall extends Module {
	
	public NoFall(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
	}
	
	public void update() {
		if (!active) { return; }
		
		if (mc.thePlayer.fallDistance > 2f) {
			mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
		}
	}
}
