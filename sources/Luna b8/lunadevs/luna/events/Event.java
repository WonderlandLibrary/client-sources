package lunadevs.luna.events;

public abstract class Event {

	private boolean cancelled;

	public Event call() {
		this.cancelled = false;
		return this;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public static enum State
	  {
	    PRE,  POST;
	  }
	
	public void setCancelled(boolean state) {
		cancelled = state;
	}
}