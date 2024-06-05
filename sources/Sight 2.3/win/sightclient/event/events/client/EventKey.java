package win.sightclient.event.events.client;

import win.sightclient.event.Event;

public class EventKey extends Event {

	private int key;
	
	public EventKey(int key) {
		this.key = key;
	}
	
	public int getKey() {
		return this.key;
	}
}
