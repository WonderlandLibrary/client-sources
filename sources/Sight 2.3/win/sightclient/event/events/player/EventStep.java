package win.sightclient.event.events.player;

import win.sightclient.event.Event;

public class EventStep extends Event {

	public double stepHeight;
	
	private boolean pre;
	
	public EventStep(double height, boolean pre) {
		this.stepHeight = height;
		this.pre = pre;
	}
	
	public boolean isPre() {
		return this.pre;
	}
}
