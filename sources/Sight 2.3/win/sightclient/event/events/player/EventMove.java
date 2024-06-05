package win.sightclient.event.events.player;

import win.sightclient.event.Event;

public class EventMove extends Event {

	private double x;
	private double y;
	private double z;
	
	public EventMove(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setZ(double z) {
		this.z = z;
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
	
	public void freeze() {
		this.setX(0);
		this.setY(0);
		this.setZ(0);
	}
}
