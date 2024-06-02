/**
 * 
 */
package cafe.kagu.kagu.eventBus.impl;

import cafe.kagu.kagu.eventBus.Event;

/**
 * @author lavaflowglow
 *
 */
public class EventRender2D extends Event {

	/**
	 * @param eventPosition The position of the event
	 */
	public EventRender2D(EventPosition eventPosition) {
		super(eventPosition);
	}

}
