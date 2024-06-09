package com.kilo.mod.all;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import org.lwjgl.input.Keyboard;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.mod.util.InventoryClick;
import com.kilo.mod.util.InventoryUtil;
import com.kilo.mod.util.ItemValue;

public class Dolphin extends Module {

	public Dolphin(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
	}
	
	public void onPlayerPreUpdate() {
		if (mc.thePlayer.isInWater() && !mc.gameSettings.keyBindJump.isKeyDown() && !mc.gameSettings.keyBindSneak.isKeyDown() && !mc.thePlayer.capabilities.isCreativeMode) {
			mc.thePlayer.motionY+= 0.03999999910593033D;
		}
	}
}
