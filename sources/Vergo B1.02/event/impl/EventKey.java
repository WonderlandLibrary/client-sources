package xyz.vergoclient.event.impl;

import xyz.vergoclient.event.Event;

public class EventKey extends Event {
	
	public int key;
	
	public EventKey(int key) {
		this.key = key;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}
	
}
