package com.thunderware.events.listeners;

import com.thunderware.events.Event;

public class EventStep extends Event<EventStep> {
	public double stepHeight;
	private boolean pre;

	public EventStep(double stepHeight,boolean pre){
		this.pre = pre;
		this.stepHeight = stepHeight;
	}
	
}
