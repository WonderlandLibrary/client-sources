package com.kilo.mod.all;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.util.ItemValue;

public class AutoTool extends Module {

	public AutoTool(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
	}
	
	public void onPlayerBlockBreaking(BlockPos pos, EnumFacing face) {
		if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiInventory) && !(mc.currentScreen instanceof GuiChat)) { return; }

		int bestSlot = -1;
		int bestVal = -1;
		
		for(int i = 0; i < 9; i++){
			ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);
			if (stack == null) {
				continue;
			}
			
			Item item = stack.getItem();
			
			if (!(item instanceof ItemTool)) { continue; }
			
			int value = getValue(stack, mc.theWorld.getBlockState(pos).getBlock());
			
			if (value > bestVal) {
				bestVal = value;
				bestSlot = i;
			}
		}
		
		if (bestSlot != -1) {
			mc.thePlayer.inventory.currentItem = bestSlot;
		}
	}
	
	public int getValue(ItemStack stack, Block block) {
		if (!(stack.getItem() instanceof ItemTool)) {
			return 0;
		}
		
		ItemTool item = (ItemTool)stack.getItem();
		int itemValue = 0;
		int id = Item.getIdFromItem(item);
		
		Item.ToolMaterial material = Item.ToolMaterial.valueOf(item.getToolMaterialName());
		
		itemValue = ItemValue.tool.valueOf(material.name()).val();
		
		int harv = 0;
		harv = (int)stack.getStrVsBlock(block)*1000;
		
		if (stack.getEnchantmentTagList() == null) {
			return itemValue+harv;
		}
		
		int enchantValue = 0;
		for(int i = 0; i < stack.getEnchantmentTagList().tagCount(); i++) {
			int enchantLevel = stack.getEnchantmentTagList().getCompoundTagAt(i).getInteger("lvl");
			int enchantID = stack.getEnchantmentTagList().getCompoundTagAt(i).getInteger("id");
			enchantValue+= ItemValue.enchant.val(enchantID)*enchantLevel;
		}
		
		return itemValue+enchantValue+harv;
	}
}
