package com.kilo.mod.all;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;

import org.lwjgl.input.Keyboard;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;

public class AntiBurn extends Module {
	
	public AntiBurn(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
	}
	
	public void update() {
		if (!active) { return; }
		
		if (mc.thePlayer.isBurning()) {
			for(int i = 0; i < 20; i++) {
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(mc.thePlayer.onGround));
			}
		}
	}
}
