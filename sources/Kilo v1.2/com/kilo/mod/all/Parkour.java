package com.kilo.mod.all;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.Util;

public class Parkour extends Module {
	
	public Parkour(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
		
		addOption("Distance Boost", "Jump a little further", Interactable.TYPE.CHECKBOX, false, null, false);
	}
	
	public void onPlayerUpdate() {
		if (!mc.thePlayer.isSneaking()) {
			if (mc.thePlayer.onGround && mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(mc.thePlayer.motionX, -0.1f, mc.thePlayer.motionZ)).isEmpty()) {
				if (Util.makeBoolean(getOptionValue("distance boost"))) {
					double ox = mc.thePlayer.motionX*3;
					double oy = 0;
					double oz = mc.thePlayer.motionZ*3;
					mc.thePlayer.setPositionAndUpdate(mc.thePlayer.posX+ox, mc.thePlayer.posY+oy, mc.thePlayer.posZ+oz);
				}
				mc.thePlayer.jump();
			}
		}
	}
}
