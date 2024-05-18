package com.kilo.mod.util;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;

public class XrayHandler {

	private static final Minecraft mc = Minecraft.getMinecraft();
	public static Map<String, Boolean> blocks = new HashMap<String, Boolean>();
	
	public static boolean canRender(Block block, BlockPos pos) {
		if (block != null && pos != null && block.getItem(mc.theWorld, pos) != null) {
			boolean can = false;
			if (blocks.get(block.getUnlocalizedName()) != null && blocks.get(block.getUnlocalizedName())) {
				can = true;
			}
			if (blocks.get(block.getItem(mc.theWorld, pos).getUnlocalizedName()) != null && blocks.get(block.getItem(mc.theWorld, pos).getUnlocalizedName())) {
				can = true;
			}
			if (blocks.get(block.getLocalizedName()) != null && blocks.get(block.getLocalizedName())) {
				can = true;
			}
			return can;
		}
		return false;
	}
	
	public static boolean isBlockEnabled(String s) {
		return blocks.get(s) != null?blocks.get(s):false;
	}
}
