package com.darkcart.xdolf.mods.player;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.entity.EntityPlayerSP;

public class enderDupe extends Module {
	
	public enderDupe() {
		super("enderChestDupe", "New", "Drops the item your holding in while your in a EnderChest, which will cause it to dupe after 5-10 minutes [If your lucky].", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.PLAYER);
	}

	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
		player.dropItem(true);
		  }
		}
	
	@Override
	public void onEnable() {
		Wrapper.addChatMessage("§7Drops the item your holding in while your in a EnderChest, which will cause it to dupe after 5-10 minutes [If your lucky].");
	}
}
