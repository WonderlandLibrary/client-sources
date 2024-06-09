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

import com.kilo.ui.inter.slotlist.part.NukerBlock;

public class NukerManager {

	private static List<NukerBlock> nukerBlocks = new CopyOnWriteArrayList<NukerBlock>();
	
	public static void loadBlocks() {
		nukerBlocks.clear();
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
				if (getNukerBlock(blocks.get(index).getUnlocalizedName()) == null) {
					addNukerBlock(new NukerBlock(is, display, blocks.get(index).getUnlocalizedName()));
				}
			}
		}
	}
	
	public static List<NukerBlock> getList() {
		return nukerBlocks;
	}
	
	public static void addNukerBlock(NukerBlock xb) {
		nukerBlocks.add(xb);
	}
	
	public static void addNukerBlock(int index, NukerBlock xb) {
		nukerBlocks.add(index, xb);
	}
	
	public static void removeNukerBlock(NukerBlock xb) {
		nukerBlocks.remove(xb);
	}
	
	public static void removeNukerBlock(int index) {
		nukerBlocks.remove(nukerBlocks.get(index));
	}
	
	public static NukerBlock getNukerBlock(int index) {
		if (nukerBlocks.size() == 0) {
			return null;
		}
		return nukerBlocks.get(index);
	}
	
	public static NukerBlock getNukerBlock(String n) {
		for(NukerBlock xb : nukerBlocks) {
			if (xb.name.equalsIgnoreCase(n)) {
				return xb;
			}
		}
		return null;
	}
	
	public static int getIndex(NukerBlock w) {
		return nukerBlocks.indexOf(w);
	}
	
	public static int getSize() {
		return getList().size();
	}
}
