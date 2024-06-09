package me.valk.event.events.player;

import me.valk.event.Event;
import me.valk.event.EventType;
import me.valk.help.world.Location;

public class EventMotion extends Event {

	public double x,y,z;

	private static Location location;
	private float yaw, pitch;
	private boolean onGround;
	
	private EventType type;

	public EventMotion(Location location, boolean onGround, float yaw, float pitch, EventType type) {
		this.location = location;
		this.onGround = onGround;
		this.type = type;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public static Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public boolean isOnGround() {
		return onGround;
	}

	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}

	public EventType getType() {
		return type;
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
	
}
