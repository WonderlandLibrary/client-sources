package net.silentclient.client.event.impl;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.silentclient.client.event.Event;

public class EventFovUpdate extends Event {
	private AbstractClientPlayer entity;
	private float fov;
	
	public EventFovUpdate(AbstractClientPlayer entity, float fov) {
		this.entity = entity;
		this.fov = fov;
	}

	public float getFov() {
		return fov;
	}

	public void setFov(float fov) {
		this.fov = fov;
	}

	public AbstractClientPlayer getEntity() {
		return entity;
	}
}
