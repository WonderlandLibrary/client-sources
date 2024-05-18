package com.darkcart.xdolf.mods.aura;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Category;
import com.darkcart.xdolf.util.EntityUtilsV2;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;

public class CrystalLog extends Module {

	public static EntityEnderCrystal entityEnderCrystal;

	public CrystalLog() {
		super("crystalAimbot", "TooLazy2Fix", "Locks the camera at a crystal close to you.", Keyboard.KEYBOARD_SIZE, 0xffffff, Category.COMBAT);
	}

	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			for (Entity e : Wrapper.getWorld().loadedEntityList) {
				if (e instanceof EntityEnderCrystal) {
					if (player.getDistanceToEntity(e) > 5 && player.getPosition().getY() >= e.getPosition().getY()) {
						EntityUtilsV2.faceEntity(this.entityEnderCrystal);
					}
				}
			}
		}
	}
}