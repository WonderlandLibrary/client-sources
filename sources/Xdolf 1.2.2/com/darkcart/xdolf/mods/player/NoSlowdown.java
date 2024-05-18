package com.darkcart.xdolf.mods.player;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;

public class NoSlowdown extends Module
{
	
	public NoSlowdown()
	{
		super("NoSlowdown", "Removes speed modifiers causing player to slowdown.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.PLAYER);
	}
	
	@Override
	public void onEnable()
	{
		fastIce();
	}
	
	@Override
	public void onDisable()
	{
		normalIce();
	}
	
	private void fastIce() {
		Blocks.ICE.slipperiness = 0.39F;
		Blocks.PACKED_ICE.slipperiness = 0.39F;
	}

	private void normalIce() {
		Blocks.ICE.slipperiness = 0.98F;
		Blocks.PACKED_ICE.slipperiness = 0.98F;
	}
	
	
}
