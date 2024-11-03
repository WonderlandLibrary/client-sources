package net.silentclient.client.event.impl;

import net.silentclient.client.event.EventCancelable;

public class EventRender3D extends EventCancelable {
	private float partialTicks;
	
	public EventRender3D(float partialTicks) {
		this.partialTicks = partialTicks;
	}

	public float getPartialTicks() {
		return partialTicks;
	}
}
