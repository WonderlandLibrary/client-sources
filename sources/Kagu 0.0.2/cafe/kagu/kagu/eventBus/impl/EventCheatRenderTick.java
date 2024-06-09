/**
 * 
 */
package cafe.kagu.kagu.eventBus.impl;

import cafe.kagu.kagu.eventBus.Event;

/**
 * @author lavaflowglow
 *
 */
public class EventCheatRenderTick extends Event {

	/**
	 * @param eventPosition
	 */
	public EventCheatRenderTick(EventPosition eventPosition) {
		super(eventPosition);
	}

}
