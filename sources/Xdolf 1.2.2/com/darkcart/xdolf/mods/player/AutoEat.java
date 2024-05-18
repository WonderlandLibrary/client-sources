package com.darkcart.xdolf.mods.player;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Category;
import com.darkcart.xdolf.util.Value;

import net.minecraft.block.BlockContainer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.FoodStats;

public class AutoEat extends Module {

	public static Value hungerFactor = new Value("AutoEat Threshold");

	public AutoEat() {
		super("AutoEat", "Automatically eats food, will not eat Golden Apples.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.PLAYER);
	}
	
	boolean doneEating;
	
	@Override
	public void onUpdate(EntityPlayerSP player) {
        ItemStack curStack = player.inventory.getCurrentItem();
        
		if(isEnabled()) {
			
			if(!shouldEat()) {
				Wrapper.getMinecraft().gameSettings.keyBindUseItem.pressed = false;
				return;
			}
				
			FoodStats foodStats = player.getFoodStats();
            if (foodStats.getFoodLevel() <= hungerFactor.getValue() && shouldEat()) 
            {
            	eatFood();
            }
		}
	}
	
	public void onDisable() {
    	Wrapper.getMinecraft().gameSettings.keyBindUseItem.pressed = false;
	}
	
	private void eatFood() {
		
		for(int slot = 44; slot >= 9; slot--) {
			ItemStack stack = Wrapper.getPlayer().inventoryContainer.getSlot(slot).getStack();
			
			
			if(stack != null) {
				if(slot >= 36 && slot <= 44) {
					if(stack.getItem() instanceof ItemFood 
							&& !(stack.getItem() instanceof ItemAppleGold)) {
						Wrapper.getPlayer().inventory.currentItem = slot - 36;
						Wrapper.getMinecraft().gameSettings.keyBindUseItem.pressed = true;
						return;
					}
				} else if(stack.getItem() instanceof ItemFood 
						&& !(stack.getItem() instanceof ItemAppleGold)) {
					int itemSlot = slot;
					int currentSlot = Wrapper.getPlayer().inventory.currentItem + 36;
					Wrapper.getMinecraft().playerController.windowClick(0, slot, 0, ClickType.PICKUP, Wrapper.getPlayer());
					Wrapper.getMinecraft().playerController.windowClick(0, currentSlot, 0, ClickType.PICKUP, Wrapper.getPlayer());
					Wrapper.getMinecraft().playerController.windowClick(0, slot, 0, ClickType.PICKUP, Wrapper.getPlayer());
					return;
				}
			}
		}
	}
	
	private boolean shouldEat()
	{	
		if(!Wrapper.getPlayer().canEat(false))
			return false;
		
		if(Wrapper.getMinecraft().currentScreen != null)
			return false;
		
		if(Wrapper.getMinecraft().currentScreen == null && Wrapper.getMinecraft().objectMouseOver != null)
		{
			Entity entity = Wrapper.getMinecraft().objectMouseOver.entityHit;
			if(entity instanceof EntityVillager || entity instanceof EntityTameable)
				return false;
			
			if(Wrapper.getMinecraft().objectMouseOver.getBlockPos() != null && Wrapper.getWorld().
					getBlockState(Wrapper.getMinecraft().objectMouseOver.getBlockPos()).getBlock() instanceof BlockContainer)
				return false;
		}
		
		return true;
	}
}