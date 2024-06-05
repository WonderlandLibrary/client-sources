package win.sightclient.event.events.player;

import win.sightclient.event.Event;

public class EventFlag extends Event {

	private static long lastFlag;
	
	public EventFlag() {
		lastFlag = System.currentTimeMillis();
	}
	
	public static long getLastFlag() {
		return lastFlag; 
	}
}
