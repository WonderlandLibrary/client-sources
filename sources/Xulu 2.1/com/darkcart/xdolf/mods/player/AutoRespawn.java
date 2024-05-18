package com.darkcart.xdolf.mods.player;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Client;
import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.entity.EntityPlayerSP;

public class AutoRespawn extends Module {
	
	public AutoRespawn() {
		super("autoRespawn", "Vanilla", "Automatically respawns for you.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.PLAYER);
	}
	
	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled() && player != null && player.getHealth() <= 0) {
			player.respawnPlayer();
        }
	}
}
