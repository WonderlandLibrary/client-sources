package com.kilo.mod.all;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;

import org.lwjgl.input.Keyboard;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;

public class Cleanse extends Module {
	
	private final Potion[] vision = new Potion[] {Potion.blindness, Potion.confusion};
	private final Potion[] blacklist = new Potion[] {Potion.digSlowdown, Potion.hunger, Potion.moveSlowdown, Potion.poison, Potion.weakness, Potion.wither};
	
	public Cleanse(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
	}
	
	public void update() {
		if (!active) { return; }
		
		for(Potion p : vision) {
			if (mc.thePlayer.getActivePotionEffect(p) != null) {
				mc.thePlayer.removePotionEffectClient(p.id);
			}
		}
		boolean a = false;
		for(Potion p : blacklist) {
			if (mc.thePlayer.getActivePotionEffect(p) != null) {
				a = true;
				break;
			}
		}
		if (a) {
			for(int i = 0; i < 20; i++) {
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(mc.thePlayer.onGround));
			}
		}
	}
}
