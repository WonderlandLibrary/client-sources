package com.darkcart.xdolf.mods.player;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiGameOver;

public class DeathCoords extends Module {

	public static int x = 0, y = 0, z = 0;

	public DeathCoords() {
		super("deathCoords", "Old", "Tells you where you died. Works in conjunction with .deathcoords", Keyboard.KEY_NONE,
				0xffffff, Category.PLAYER);
	}

	public void onUpdate(EntityPlayerSP player) {
		if (this.isEnabled()) {
			if (Wrapper.getMinecraft().currentScreen instanceof GuiGameOver) {
				x = player.getPosition().getX();
				y = player.getPosition().getY();
				y = player.getPosition().getZ();
			}
		}
	}

}