
package Ascii4UwUWareClient.API.Events.Misc;

import Ascii4UwUWareClient.API.Event;

public class EventKey extends Event {
	private int key;

	public EventKey(int key) {
		this.key = key;
	}

	public int getKey() {
		return this.key;
	}

	public void setKey(int key) {
		this.key = key;
	}
}
