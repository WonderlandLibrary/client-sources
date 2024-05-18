package com.darkcart.xdolf.mods.world;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.mods.Hacks;
import com.darkcart.xdolf.mods.player.Flight;
import com.darkcart.xdolf.util.Category;
import com.darkcart.xdolf.util.FreecamEntity;

import net.minecraft.client.entity.EntityPlayerSP;

public class Freecam extends Module {
	
	public Freecam()
	{
		super("Freecam", "Frees the players camera to move freely.", Keyboard.KEY_B, 0xFFFFFF, Category.WORLD);
	}
	
	private FreecamEntity freecamEnt;
	
	@Override
	public void onEnable() {
		freecamEnt = new FreecamEntity();
	}

	@Override
	public void onDisable() {
		freecamEnt.resetPlayerPosition();
		freecamEnt.despawn();
		Wrapper.getMinecraft().renderGlobal.loadRenderers();
	}
}