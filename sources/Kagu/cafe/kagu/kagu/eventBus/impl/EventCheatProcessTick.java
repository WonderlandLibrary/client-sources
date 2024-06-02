/**
 * 
 */
package cafe.kagu.kagu.eventBus.impl;

import cafe.kagu.kagu.eventBus.Event;

/**
 * @author lavaflowglow
 *
 */
public class EventCheatProcessTick extends Event {

	/**
	 * @param eventPosition
	 */
	public EventCheatProcessTick(EventPosition eventPosition) {
		super(eventPosition);
	}

}
