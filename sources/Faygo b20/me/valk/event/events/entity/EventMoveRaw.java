package me.valk.event.events.entity;

import me.valk.event.Event;

public class EventMoveRaw extends Event {

	public double x;
	public double y;
	public double z;
	
	public EventMoveRaw(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
		
}
