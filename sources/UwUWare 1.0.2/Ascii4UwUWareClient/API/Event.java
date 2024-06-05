
package Ascii4UwUWareClient.API;

public abstract class Event {
	private boolean cancelled;
	public byte type;

	public boolean isCancelled() {
		return this.cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public byte getType() {
		return this.type;
	}

	public void setType(byte type) {
		this.type = type;
	}
	public static void call(final Event event) {
		EventBus.getInstance ().register ( event );
	}
}
