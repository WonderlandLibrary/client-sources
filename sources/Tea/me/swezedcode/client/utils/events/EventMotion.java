package me.swezedcode.client.utils.events;

import com.darkmagician6.eventapi.events.callables.EventCancellable;
import com.darkmagician6.eventapi.types.EventType;

import me.swezedcode.client.utils.location.Location;

public class EventMotion extends EventCancellable {

	public double x, y, z;

	private static Location location;
	private float yaw;

	public float pitch;
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
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getZ() {
		return z;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	private void setY(double y) {
		this.y = y;
	}
	
	public void setZ(double z) {
		this.z = z;
	}

}