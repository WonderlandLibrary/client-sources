package com.kilo.mod.all;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.mod.util.InventoryClick;
import com.kilo.mod.util.InventoryUtil;
import com.kilo.mod.util.ItemValue;
import com.kilo.util.Util;

public class AutoWeapon extends Module {

	public AutoWeapon(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
	}
	
	public void update() {
		if (!active) { return; }
		
		if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiInventory) && !(mc.currentScreen instanceof GuiChat)) { return; }

		int bestSlot = -1;
		int bestVal = -1;
		
		for(int i = 0; i < 9; i++){
			ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);
			if (stack == null) {
				continue;
			}
			
			Item item = stack.getItem();
			
			if (!(item instanceof ItemSword)) { continue; }
			
			int value = getValue(stack);
			
			if (value > bestVal) {
				bestVal = value;
				bestSlot = i;
			}
		}
		
		if (bestSlot != -1) {
			mc.thePlayer.inventory.currentItem = bestSlot;
		}
	}
	
	public int getValue(ItemStack stack) {
		if (!(stack.getItem() instanceof ItemSword)) {
			return 0;
		}
		ItemSword item = (ItemSword)stack.getItem();
		int itemValue = 0;
		int id = Item.getIdFromItem(item);
		
		Item.ToolMaterial material = Item.ToolMaterial.valueOf(item.getToolMaterialName());
		
		itemValue = ItemValue.tool.valueOf(material.name()).val();
		
		if (stack.getEnchantmentTagList() == null) {
			return itemValue;
		}
		
		int enchantValue = 0;
		for(int i = 0; i < stack.getEnchantmentTagList().tagCount(); i++) {
			int enchantLevel = stack.getEnchantmentTagList().getCompoundTagAt(i).getInteger("lvl");
			int enchantID = stack.getEnchantmentTagList().getCompoundTagAt(i).getInteger("id");
			enchantValue+= ItemValue.enchant.val(enchantID)*enchantLevel;
		}
		
		return itemValue+enchantValue;
	}
}
