package com.boomer.client.event.events.input;

import com.boomer.client.event.Event;

public class KeyPressEvent extends Event {
	private int key;

	public KeyPressEvent(int key) {
		this.key = key;
	}

	public int getKey() {
		return this.key;
	}
}
