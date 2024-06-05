package win.sightclient.event.events.render;

import win.sightclient.event.Event;

public class EventName extends Event {

	private String name;

	public EventName(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
