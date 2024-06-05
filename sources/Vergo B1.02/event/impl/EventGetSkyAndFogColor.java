package xyz.vergoclient.event.impl;

import xyz.vergoclient.event.Event;
import net.minecraft.util.Vec3;

public class EventGetSkyAndFogColor extends Event {
	
	public EventGetSkyAndFogColor(Vec3 color) {
		this.color = color;
	}
	
	public Vec3 color;
	
}
