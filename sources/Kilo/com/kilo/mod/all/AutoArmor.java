package com.kilo.mod.all;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.mod.util.InventoryClick;
import com.kilo.mod.util.InventoryUtil;
import com.kilo.mod.util.ItemValue;
import com.kilo.util.Util;

public class AutoArmor extends Module {

	public AutoArmor(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);

		addOption("NC+", "Use on servers using NC+", Interactable.TYPE.CHECKBOX, true, null, false);
		addOption("Drop", "Drop current armor if inventory is full", Interactable.TYPE.CHECKBOX, false, null, false);
	}
	
	public void update() {
		if (!active) { return; }
		
		if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiInventory) && !(mc.currentScreen instanceof GuiChat)) { return; }

		int[] best = new int[] {-1, -1, -1, -1};
		int[] bestSlot = new int[] {-1, -1, -1, -1};
		
		boolean full = true;
		
		for(int i = 9; i <= 44; i++){
			Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
			if (!slot.getHasStack()) {
				full = false;
				continue;
			}
			
			ItemStack stack = slot.getStack();
			Item item = stack.getItem();
			
			if (!(item instanceof ItemArmor)) { continue; }
			
			int piece = (Item.getIdFromItem(item)-2)%4;
			int value = getValue(stack);
			
			Slot armorSlot = mc.thePlayer.inventoryContainer.getSlot(piece+5);
			boolean isArmor = armorSlot.getHasStack();
			int armorValue = best[piece];
			
			if (isArmor) {
				 armorValue = getValue(armorSlot.getStack());
			}
			
			if (value > armorValue) {
				best[piece] = value;
				bestSlot[piece] = i;
			}
		}
		
		for(int i = 0; i < 4; i++) {
			if (bestSlot[i] == -1) { continue; }
			if (mc.thePlayer.inventoryContainer.getSlot(i+5).getHasStack()) {
				if (full && Util.makeBoolean(getOptionValue("drop"))) {
					InventoryUtil.click(new InventoryClick(mc.thePlayer.inventoryContainer.windowId, i+5, 4));
				} else if (!full) {
					InventoryUtil.click(new InventoryClick(mc.thePlayer.inventoryContainer.windowId, i+5, 1));
				} else {
					continue;
				}
			}
			InventoryUtil.click(new InventoryClick(mc.thePlayer.inventoryContainer.windowId, bestSlot[i], 1));
		}
	}
	
	public int getValue(ItemStack stack) {
		if (!(stack.getItem() instanceof ItemArmor)) {
			return 0;
		}
		ItemArmor item = (ItemArmor)stack.getItem();
		int itemValue = 0;
		int id = Item.getIdFromItem(item);
		
		ItemArmor.ArmorMaterial material = item.getArmorMaterial();
		
		itemValue = ItemValue.armor.valueOf(material.name()).val();
		
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
