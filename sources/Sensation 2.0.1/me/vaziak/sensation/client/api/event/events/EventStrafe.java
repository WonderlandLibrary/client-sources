package me.vaziak.sensation.client.api.event.events;

import me.vaziak.sensation.client.api.event.Cancellable;

public class EventStrafe extends Cancellable {
	public float strafe;
	public float forward;
	public float friction; 
	
	public EventStrafe(float strafe, float forward, float friction) {
		this.strafe = strafe;
		this.forward = forward;
		this.friction = friction;
	}
	
	public float getFrication() {
		return friction;
	}

	public float getForward() {
		return forward;
	}
	
	public float getStrafe() {
		return strafe;
	}
}
