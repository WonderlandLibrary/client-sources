package com.kilo.mod.all;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.Util;

public class Fly extends Module {
	
	private float flySpeed;
	
	public Fly(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);

		addOption("Speed", "Speed to fly at", Interactable.TYPE.SLIDER, 1, new float[] {1, 25}, true);
	}
	
	public void onEnable() {
		super.onEnable();
		flySpeed = Math.max(0.05f, mc.thePlayer.capabilities.getFlySpeed());
	}
	
	public void onDisable() {
		super.onDisable();
		mc.thePlayer.capabilities.setFlySpeed(flySpeed);
		if (mc.thePlayer.capabilities.isCreativeMode) {
			return;
		}
		mc.thePlayer.capabilities.isFlying = false;
	}
	
	public void update() {
		if (!mc.thePlayer.isSpectator()) {
			mc.thePlayer.capabilities.setFlySpeed((float)Math.max(0.05, mc.thePlayer.capabilities.getFlySpeed()));
		}
		if (active) {
			mc.thePlayer.capabilities.setFlySpeed((float)Math.max(0.05, 0.05f*Util.makeFloat(getOptionValue("speed"))));
			mc.thePlayer.capabilities.isFlying = true;
		}
	}
	
	public void onPlayerPreMotion() {
		mc.thePlayer.capabilities.setFlySpeed((float)Math.max(0.05, 0.05f*Util.makeFloat(getOptionValue("speed"))));
		mc.thePlayer.capabilities.isFlying = true;
	}
}
