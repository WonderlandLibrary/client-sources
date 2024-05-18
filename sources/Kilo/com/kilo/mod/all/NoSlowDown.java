package com.kilo.mod.all;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import org.lwjgl.util.Timer;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.ClientUtils;
import com.kilo.util.Util;

public class NoSlowDown extends Module {
	
	private float[] oldRotation = new float[] {0, 0};
	private Timer timer = new Timer();

	public NoSlowDown(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
		
		addOption("Bow", "Aiming with a bow", Interactable.TYPE.CHECKBOX, true, null, false);
		addOption("Food", "Eating food", Interactable.TYPE.CHECKBOX, true, null, false);
		addOption("Potions", "Drinking potions", Interactable.TYPE.CHECKBOX, true, null, false);
		addOption("Sword", "Blocking with sword", Interactable.TYPE.CHECKBOX, true, null, false);
	}
	
	public boolean slowOnItemUse() {
		if (!active) {
			return true;
		}
		
		ItemStack is = mc.thePlayer.getCurrentEquippedItem();
		
		if (is == null) {
			return true;
		}
		
		Item i = is.getItem();

		if ((i instanceof ItemSword)) {
			if (Util.makeBoolean(getOptionValue("sword"))) {
				return false;
			}
		}
		if ((i instanceof ItemBow)) {
			if (Util.makeBoolean(getOptionValue("bow"))) {
				return false;
			}
		}
		if ((i instanceof ItemFood)) {
			if (Util.makeBoolean(getOptionValue("food"))) {
				return false;
			}
		}
		if ((i instanceof ItemPotion)) {
			if (!ItemPotion.isSplash(mc.thePlayer.getCurrentEquippedItem().getItemDamage())) {
				if (Util.makeBoolean(getOptionValue("potions"))) {
					return false;
				}
			}
		}
		
		return true;
	}
}
