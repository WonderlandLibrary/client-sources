package com.kilo.ui.inter.slotlist.part;

import net.minecraft.client.Minecraft;

import com.kilo.render.Colors;

public class Macro {

	public String name, command;
	public int keybind;
	
	public Macro(String name, String command, int keybind) {
		this.name = name;
		this.command = command;
		this.keybind = keybind;
	}
}
