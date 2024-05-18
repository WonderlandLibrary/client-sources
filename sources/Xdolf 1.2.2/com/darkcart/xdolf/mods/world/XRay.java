package com.darkcart.xdolf.mods.world;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Client;
import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.mods.Hacks;
import com.darkcart.xdolf.util.Category;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class XRay extends Module
{
	public static ArrayList<Block> xrayBlocks = new ArrayList<Block>();
	
	public XRay()
	{
		super("Xray", "Allows you to see through the ground and find ores.", Keyboard.KEY_X, 0x666666, Category.WORLD);
	}
	
	@Override
	public void onEnable() {
		Wrapper.getMinecraft().renderGlobal.loadRenderers();
	}
	
	@Override
	public void onDisable() {
		Wrapper.getMinecraft().renderGlobal.loadRenderers();
	}
	
	public static boolean isXrayBlock(String name)
	{
		return xrayBlocks.contains(name);
	}
	
	public static void shouldAddOrRemove(String name)
	{
		if(isXrayBlock(name))
		{
			xrayBlocks.remove(((XRay)Hacks.findMod(XRay.class)).xrayBlocks.indexOf(Block.getBlockFromName(name)));
			Wrapper.addChatMessage("Removed \247e" + Block.getBlockFromName(name) + "\247f from xray list.");
			Wrapper.getFileManager().saveXrayList();
			Wrapper.getMinecraft().renderGlobal.loadRenderers();
		}else{
			xrayBlocks.add(Block.getBlockFromName(name));
			Wrapper.addChatMessage("Added \247e" + Block.getBlockFromName(name) + "\247f to xray list.");
			Wrapper.getFileManager().saveXrayList();
			Wrapper.getMinecraft().renderGlobal.loadRenderers();
		}
	}
}
