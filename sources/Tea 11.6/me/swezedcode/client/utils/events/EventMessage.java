package me.swezedcode.client.utils.events;

import com.darkmagician6.eventapi.events.Event;
import com.darkmagician6.eventapi.events.callables.EventCancellable;

public class EventMessage extends EventCancellable implements Event {
	
	private String message;
	private boolean canelled;
	
	public EventMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public boolean isCancelled() {
		return canelled;
	}
	
	public void setCanelled(boolean canelled) {
		this.canelled = canelled;
	}

}
