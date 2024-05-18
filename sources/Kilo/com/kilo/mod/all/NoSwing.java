package com.kilo.mod.all;

import net.minecraft.network.play.client.C03PacketPlayer;

import org.lwjgl.input.Keyboard;

import com.kilo.manager.Notification;
import com.kilo.manager.NotificationManager;
import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;

public class NoSwing extends Module {
	
	public NoSwing(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
	}
}
