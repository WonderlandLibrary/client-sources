package com.kilo.ui.inter.slotlist.part;

import net.minecraft.client.Minecraft;

import com.kilo.render.Colors;

public class Waypoint {

	public String name;
	public double x, y, z;
	public int color;
	
	public Waypoint(String name) {
		this(name, Colors.WHITE.c);
	}
	
	public Waypoint(String name, int color) {
		this(name, Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, color);
	}
	
	public Waypoint(String name, double x, double y, double z, int color) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.z = z;
		this.color = color;
	}
}
