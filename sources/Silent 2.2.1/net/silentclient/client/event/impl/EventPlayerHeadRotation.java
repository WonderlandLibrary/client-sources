package net.silentclient.client.event.impl;

import net.silentclient.client.event.EventCancelable;

public class EventPlayerHeadRotation extends EventCancelable {
	private float yaw;
	private float pitch;
	
	public EventPlayerHeadRotation(float yaw, float pitch) {
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getPitch() {
		return pitch;
	}
}
