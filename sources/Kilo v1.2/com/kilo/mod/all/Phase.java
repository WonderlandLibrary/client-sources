package com.kilo.mod.all;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import org.lwjgl.input.Keyboard;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.Util;

public class Phase extends Module {

	public Phase(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
		
		addOption("Horizontal", "Horizontal movement distance", Interactable.TYPE.SLIDER, 0.2f, new float[] {0, 1}, true);
		addOption("Vertical", "Vertical movement distance", Interactable.TYPE.SLIDER, 0.1f, new float[] {0, 1}, true);
		
		addOption("Sneak", "Only phase when sneaking", Interactable.TYPE.CHECKBOX, false, null, false);
	}
	
	public void update() {
		if (!active) { return; }
		
		if (mc.thePlayer.isCollidedHorizontally && (Util.makeBoolean(getOptionValue("sneak"))?mc.thePlayer.isSneaking():true)) {
			moveForward();
			moveDown();
		}
	}
	
	private void moveForward() {
		float dir = mc.thePlayer.rotationYaw;
		if (mc.thePlayer.moveForward < 0) {
			dir+= 180;
		}
		if (mc.thePlayer.moveStrafing > 0) { 
			dir-= 90*(mc.thePlayer.moveForward>0?0.5f:mc.thePlayer.moveForward<0?-0.5f:1);
		}
		if (mc.thePlayer.moveStrafing < 0) {
			dir+= 90*(mc.thePlayer.moveForward>0?0.5f:mc.thePlayer.moveForward<0?-0.5f:1);
		}
		 
		float hOff = Util.makeFloat(getOptionValue("horizontal"));
		float xD = (float)Math.cos((dir+90)*Math.PI/180)*hOff;
		float zD = (float)Math.sin((dir+90)*Math.PI/180)*hOff;
		mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX+xD, mc.thePlayer.posY, mc.thePlayer.posZ+zD, true));
	}

	private void moveDown() {
		float vOff = Util.makeFloat(getOptionValue("vertical"));
		mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY-vOff, mc.thePlayer.posZ, true));
	}
}
