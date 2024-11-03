package net.silentclient.client.event.impl;

import net.silentclient.client.event.EventCancelable;

public class KeyEvent extends EventCancelable {

	private final int key;
	
	public KeyEvent(int key) {
		this.key = key;
	}
	
	public int getKey() {
		return key;
	}
}
