package com.darkcart.xdolf.mods.aura;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.mods.Hacks;
import com.darkcart.xdolf.util.Category;
import com.darkcart.xdolf.util.Value;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;

public class CrystalLog extends Module {

	public static Value distance = new Value("CrystalLog distance");

	public CrystalLog() {
		super("CrystalLog", "Disconnects the player if an endercrystal comes into range.", Keyboard.KEYBOARD_SIZE, 0xffffff, Category.COMBAT);
	}

	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			for (Entity e : Wrapper.getWorld().loadedEntityList) {
				if (e instanceof EntityEnderCrystal) {
					if (player.getDistanceToEntity(e) > distance.getValue() && player.getPosition().getY() >= e.getPosition().getY()) {
						Wrapper.getWorld().sendQuittingDisconnectingPacket();
						Hacks.findMod(CrystalLog.class).toggle();
					}
				}
			}
		}
	}
}
