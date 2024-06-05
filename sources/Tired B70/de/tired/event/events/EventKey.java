package de.tired.event.events;

import de.tired.event.Event;

public class EventKey extends Event
{
	public int key;

	public EventKey(final int key) {
		this.key = key;
	}

	public int getKey() {
		return this.key;
	}
}
