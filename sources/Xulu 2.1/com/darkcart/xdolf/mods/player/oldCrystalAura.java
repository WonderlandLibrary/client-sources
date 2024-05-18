package com.darkcart.xdolf.mods.player;

import java.util.Iterator;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.EnumHand;

public class oldCrystalAura extends Module {

	public oldCrystalAura() {
		super("crystalAuraOld", "Old", "Old crystalaura.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.PLAYER);
	}

	Minecraft mc = Minecraft.getMinecraft();

	@Override
	public void onUpdate(EntityPlayerSP player) {
		if (isEnabled()) {
			for (Iterator<Entity> entities = mc.world.loadedEntityList.iterator(); entities.hasNext();) {
				Entity theObject = (Entity) entities.next();
				if ((theObject instanceof EntityEnderCrystal)) {
					Entity crystal = theObject;

					float attacktime = mc.player.getCooledAttackStrength(1.0F);
					if ((theObject instanceof EntityEnderCrystal)) {
						if ((mc.player.getDistanceToEntity(theObject) <= 10.0F) && (attacktime <= 1.0F)) {
							mc.player.attackTargetEntityWithCurrentItem(theObject);
							mc.player.swingArm(EnumHand.MAIN_HAND);
							mc.playerController.attackEntity(mc.player, theObject);
						}
					}
				}
			}
		}
	}

	@Override
	public void onDisable() {
	}
}