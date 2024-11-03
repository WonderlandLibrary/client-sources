package net.silentclient.client.event;

public class EventCancelable extends Event {
	private boolean cancelled = false;
	
	public boolean isCancelable() {
		return cancelled;
	}
	
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
