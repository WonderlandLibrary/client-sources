package com.darkcart.xdolf.mods.world;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;

public class Nuker extends Module {

	public Nuker() {
		super("nuker", "Cancer", "Destroys all the blocks around you.", Keyboard.KEY_NONE, 0xffffff, Category.WORLD);
	}

	public void onUpdate(EntityPlayerSP player) {
		player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK,
				player.getPosition(), EnumFacing.DOWN));
	}
}