package com.kilo.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.ui.inter.slotlist.part.XrayBlock;

public class XrayManager {

	private static List<XrayBlock> xrayBlocks = new CopyOnWriteArrayList<XrayBlock>();
	
	public static void loadBlocks() {
		xrayBlocks.clear();
		Map<String, Integer> nameIndex = new LinkedHashMap<String, Integer>();
		Map<Integer, Block> blocks = new LinkedHashMap<Integer, Block>();
		Map<Integer, ItemStack> itemStacks = new LinkedHashMap<Integer, ItemStack>();

		for(int i = 0; i < Block.blockRegistry.getKeys().size(); i++) {
			Block b = (Block)Block.blockRegistry.getObjectById(i);
			ItemStack is = new ItemStack(Item.getItemFromBlock(b));
			
			String display = b.getLocalizedName();
			if (is != null && is.getItem() != null) {
				display = is.getDisplayName();
			}
			nameIndex.put(display, i);
			blocks.put(i, b);
			itemStacks.put(i, is);
		}
		List<String> list = new ArrayList<String>();
		list.addAll(nameIndex.keySet());
		Collections.sort(list);
		
		for(String s : list) {
			int index = nameIndex.get(s);
			
			ItemStack is = itemStacks.get(index);
			String display = blocks.get(index).getLocalizedName();
			if (is != null && is.getItem() != null) {
				display = is.getDisplayName();
			}
			
			if (!display.startsWith("tile")) {
				if (getXrayBlock(blocks.get(index).getUnlocalizedName()) == null) {
					addXrayBlock(new XrayBlock(is, display, blocks.get(index).getUnlocalizedName()));
				}
			}
		}
	}
	
	public static List<XrayBlock> getList() {
		return xrayBlocks;
	}
	
	public static void addXrayBlock(XrayBlock xb) {
		xrayBlocks.add(xb);
	}
	
	public static void addXrayBlock(int index, XrayBlock xb) {
		xrayBlocks.add(index, xb);
	}
	
	public static void removeXrayBlock(XrayBlock xb) {
		xrayBlocks.remove(xb);
	}
	
	public static void removeXrayBlock(int index) {
		xrayBlocks.remove(xrayBlocks.get(index));
	}
	
	public static XrayBlock getXrayBlock(int index) {
		if (xrayBlocks.size() == 0) {
			return null;
		}
		return xrayBlocks.get(index);
	}
	
	public static XrayBlock getXrayBlock(String n) {
		for(XrayBlock xb : xrayBlocks) {
			if (xb.name.equalsIgnoreCase(n)) {
				return xb;
			}
		}
		return null;
	}
	
	public static int getIndex(XrayBlock w) {
		return xrayBlocks.indexOf(w);
	}
	
	public static int getSize() {
		return getList().size();
	}
}
