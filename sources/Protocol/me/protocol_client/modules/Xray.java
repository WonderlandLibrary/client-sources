package me.protocol_client.modules;

import java.util.ArrayList;

import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;

public class Xray extends Module {
	public ArrayList<Block>	blocks	= new ArrayList<Block>();

	public Xray() {
		super("Xray", "xray", 45, Category.RENDER, new String[] { "xray" });
		blocks.add(Block.getBlockById(15));
		blocks.add(Block.getBlockById(16));
		blocks.add(Block.getBlockById(2));
		blocks.add(Block.getBlockById(10));
		blocks.add(Block.getBlockById(11));
		blocks.add(Block.getBlockById(10));
		blocks.add(Block.getBlockById(48));
		blocks.add(Block.getBlockById(52));
		blocks.add(Block.getBlockById(56));
		blocks.add(Block.getBlockById(15));
		blocks.add(Block.getBlockById(14));
		blocks.add(Block.getBlockById(21));
		blocks.add(Block.getBlockById(89));
		blocks.add(Block.getBlockById(15));
		blocks.add(Block.getBlockById(124));
		blocks.add(Block.getBlockById(129));
		blocks.add(Block.getBlockById(41));
		blocks.add(Block.getBlockById(19));
		blocks.add(Block.getBlockById(42));
		blocks.add(Block.getBlockById(73));
		blocks.add(Block.getBlockById(74));
	}

	public void onEnable() {
		// Visgraph.java to make all sides visible
		Wrapper.mc().gameSettings.gammaSetting += 1000;
		Minecraft.getMinecraft().renderGlobal.loadRenderers();
	}

	public void onDisable() {
		Wrapper.mc().gameSettings.gammaSetting -= 1000;
		Minecraft.getMinecraft().renderGlobal.loadRenderers();
	}

	public boolean shouldXray(Block block) {
		return blocks.contains(block);
	}
}
