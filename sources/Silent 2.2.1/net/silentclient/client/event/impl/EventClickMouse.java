package net.silentclient.client.event.impl;

import net.silentclient.client.event.Event;

public class EventClickMouse extends Event {
	private final int button;
	
	public EventClickMouse(int button) {
		this.button = button;
	}
	
	public int getButton() {
		return button;
	}
}
