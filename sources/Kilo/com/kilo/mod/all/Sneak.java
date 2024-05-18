package com.kilo.mod.all;

import net.minecraft.network.play.client.C0BPacketEntityAction;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.Timer;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.Util;

public class Sneak extends Module {
	
	public Sneak(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
	}
	
	public void onDisable() {
		super.onDisable();
        mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
	}
	
	public void onPlayerPreUpdate() {
        mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
	}
	
	public void onPlayerPostUpdate() {
		mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
	}
}
