package com.kilo.ui.inter.slotlist.part;

import net.minecraft.item.ItemStack;

public class XrayBlock {

	public ItemStack stack;
	public String display, name;
	
	public XrayBlock(ItemStack stack, String display, String name) {
		this.stack = stack;
		this.display = display;
		this.name = name;
	}
}
