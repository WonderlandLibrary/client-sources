package net.augustus.utils.HackerDetector.impl;

import net.augustus.utils.HackerDetector.Check;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;

public class AutoBlockCheck extends Check{
	
	public AutoBlockCheck() {
		super("AutoBlock", true);
	}
	
	
	
	@Override
	public boolean runCheck(EntityPlayer player) {
		if((player.isBlocking() || player.isUsingItem()) && player.swingProgress != 0 && player.inventory.getStackInSlot(player.inventory.currentItem).getItem() instanceof ItemSword)
			return true;
		return false;
	}

}
