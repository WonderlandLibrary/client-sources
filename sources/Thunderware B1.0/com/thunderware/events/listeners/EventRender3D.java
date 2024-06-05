package com.thunderware.events.listeners;

import com.thunderware.events.Event;

public class EventRender3D extends Event<EventRender3D> {
	private float partialTicks;
	
	public EventRender3D(float partialTicks) {
		this.partialTicks = partialTicks;
	}
	
	public float getPartialTicks() {
		return partialTicks;
	}
}
