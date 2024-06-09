package com.kilo.mod.all;

import net.minecraft.item.ItemBow;

import org.lwjgl.input.Keyboard;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.Util;

public class BowDamage extends Module {
	
	private float[] oldRotation = new float[] {0, 0};
	
	public BowDamage(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
	}
	
	public void onPlayerPreUpdate() {
		if (mc.thePlayer != null && mc.thePlayer.getItemInUse() != null && mc.thePlayer.getItemInUse().getItem() instanceof ItemBow) {
			oldRotation[0] = mc.thePlayer.rotationYaw;
			oldRotation[1] = mc.thePlayer.rotationPitch;
			
			mc.thePlayer.rotationPitch-= Util.angleDifference(mc.thePlayer.rotationPitch, -90);
		}
	}
	
	public void onPlayerPostUpdate() {
		if (mc.thePlayer != null && mc.thePlayer.getItemInUse() != null && mc.thePlayer.getItemInUse().getItem() instanceof ItemBow) {
			mc.thePlayer.rotationYaw-= Util.angleDifference(mc.thePlayer.rotationYaw, oldRotation[0]);
			mc.thePlayer.rotationPitch-= Util.angleDifference(mc.thePlayer.rotationPitch, oldRotation[1]);
		}
	}
}
