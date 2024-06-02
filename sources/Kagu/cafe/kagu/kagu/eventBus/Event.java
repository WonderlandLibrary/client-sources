/**
 * 
 */
package cafe.kagu.kagu.eventBus;

import cafe.kagu.kagu.Kagu;

/**
 * @author lavaflowglow
 *
 */
public abstract class Event {

	/**
	 * @param eventPosition The position of the event in the code
	 */
	public Event(EventPosition eventPosition) {
		this.eventPosition = eventPosition;
	}

	private final EventPosition eventPosition;
	private boolean canceled = false;

	/**
	 * Pushes the event object onto the event bus and distributes it to any
	 * subscribers
	 */
	public void post() {
		Kagu.getEventBus().postEvent(this);
	}

	/**
	 * @return True if the event position is pre, false if post
	 */
	public boolean isPre() {
		return eventPosition == EventPosition.PRE;
	}

	/**
	 * @return True if the event position is post, false if pre
	 */
	public boolean isPost() {
		return eventPosition == EventPosition.POST;
	}

	/**
	 * @return the canceled
	 */
	public boolean isCanceled() {
		return canceled;
	}

	/**
	 * @param canceled the canceled to set
	 */
	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}
	
	/**
	 * Cancels the event, does nothing if it's already canceled
	 */
	public void cancel() {
		canceled = true;
	}

	/**
	 * @return the eventPosition
	 */
	public EventPosition getEventPosition() {
		return eventPosition;
	}

	/**
	 * @author lavaflowglow
	 *
	 */
	public static enum EventPosition {
		PRE, POST
	}

}
