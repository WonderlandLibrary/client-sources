package me.valk.event.events.other;

import me.valk.event.Event;

public class EventKeyPress extends Event {

	private int key;

	public EventKeyPress(int key) {
		this.key = key;
	}

	public int getKey() {
		return key;
	}
	
}
