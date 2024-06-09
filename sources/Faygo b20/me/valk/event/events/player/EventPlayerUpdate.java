package me.valk.event.events.player;

import me.valk.event.Event;
import me.valk.event.EventType;

public class EventPlayerUpdate extends Event {

	private EventType type;
	public double x;
	public double y;
	public double z;

	public EventPlayerUpdate(EventType type) {
		this.type = type;
	}

	public EventType getType() {
		return type;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getZ() {
		return this.z;
	}

	public void setX(final double x) {
		this.x = x;
	}

	public void setY(final double y) {
		this.y = y;
	}

	public void setZ(final double z) {
		this.z = z;
	}

}
