package com.darkcart.xdolf.mods.player;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.mods.Hacks;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.entity.EntityPlayerSP;

public class dropDupe extends Module {
	
	public dropDupe() {
		super("dropDupe", "Old", "95% chance of you getting your item duplicated when you drop it and get DC'd [1.11.0 Dupe].", Keyboard.KEY_BACKSLASH, 0xFFFFFF, Category.PLAYER);
	}

	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			Wrapper.getPlayer().dropItem(true);
			Wrapper.getPlayer().dropItem(true);
			Wrapper.getPlayer().dropItem(true);
			Wrapper.getPlayer().dropItem(true);
			Wrapper.getWorld().sendDupeDropPacket();
			Wrapper.getPlayer().dropItem(true);
			Hacks.findMod(dropDupe.class).toggle();
		}
	}
	
	@Override
	public void onDisable() {
	}
}
