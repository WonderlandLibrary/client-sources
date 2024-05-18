package me.swezedcode.client.utils.events;

import com.darkmagician6.eventapi.events.callables.EventCancellable;

public class EventKeyPress extends EventCancellable {

	private int key;

	public EventKeyPress(int key) {
		this.key = key;
	}

	public int getKey() {
		return key;
	}
	
}
