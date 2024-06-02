/**
 * 
 */
package cafe.kagu.kagu.eventBus.impl;

import cafe.kagu.kagu.eventBus.Event;

/**
 * @author lavaflowglow
 *
 */
public class EventChatSendMessage extends Event {

	private String message;

	/**
	 * @param eventPosition The position of the event
	 */
	public EventChatSendMessage(EventPosition eventPosition, String message) {
		super(eventPosition);
		this.message = message;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
