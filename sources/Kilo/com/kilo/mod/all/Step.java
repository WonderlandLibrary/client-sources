package com.kilo.mod.all;

import net.minecraft.network.play.client.C03PacketPlayer;

import org.lwjgl.input.Keyboard;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.Util;

public class Step extends Module {
	
	public Step(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
		
		addOption("Step Height", "Maximum step height", Interactable.TYPE.SLIDER, 1, new float[] {1, 10}, true);
	}
	
	public void onDisable() {
		super.onDisable();
		mc.thePlayer.stepHeight = 0.6f;
	}
	
	public void update() {
		if (!active) { return; }
		
		if (mc.gameSettings.keyBindSneak.isKeyDown() || mc.thePlayer.isInWater() || mc.thePlayer.isInLava()) {
			mc.thePlayer.stepHeight = 0.6f;
			return;
		}
		
		mc.thePlayer.stepHeight = Util.makeFloat(getOptionValue("step height"));
	}
	
	public void onStep(float height) {
		if (height == 1) {
			if (mc.thePlayer != null) {
				mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY+0.0696D, mc.thePlayer.posZ, mc.thePlayer.onGround));
			}
		}
	}
}
