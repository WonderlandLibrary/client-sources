package net.silentclient.client.event.impl;

import net.silentclient.client.event.Event;

public class EventCameraRotation extends Event {
	private float yaw;
	private float pitch;
	private float roll;
	
	public EventCameraRotation(float yaw, float pitch, float roll) {
		this.yaw = yaw;
		this.pitch = pitch;
		this.roll = roll;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public float getRoll() {
		return roll;
	}

	public void setRoll(float roll) {
		this.roll = roll;
	}
}
