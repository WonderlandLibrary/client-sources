/**
 * 
 */
package cafe.kagu.kagu.eventBus.impl;

import cafe.kagu.kagu.eventBus.Event;

/**
 * @author lavaflowglow
 *
 */
public class EventTick extends Event {

	/**
	 * @param eventPosition The position of the event
	 */
	public EventTick(EventPosition eventPosition) {
		super(eventPosition);
	}

}
