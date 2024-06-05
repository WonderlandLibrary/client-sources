package me.rich.event.events;

import me.rich.event.Event;

public class EventMouse extends Event {

	public int key;

	public EventMouse(int key) {
		this.key = key;
	}

}
