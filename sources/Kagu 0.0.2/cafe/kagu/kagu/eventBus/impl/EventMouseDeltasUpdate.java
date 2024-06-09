/**
 * 
 */
package cafe.kagu.kagu.eventBus.impl;

import cafe.kagu.kagu.eventBus.Event;

/**
 * @author DistastefulBannock
 *
 */
public class EventMouseDeltasUpdate extends Event {

	/**
	 * @param eventPosition The position of the event
	 * @param deltaX The amount the mouse moved on the x coord
	 * @param deltaY The amount the mouse moved on the y coord
	 */
	public EventMouseDeltasUpdate(EventPosition eventPosition, int deltaX, int deltaY) {
		super(eventPosition);
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}
	
	private int deltaX, deltaY;

	/**
	 * @return the deltaX
	 */
	public int getDeltaX() {
		return deltaX;
	}

	/**
	 * @param deltaX the deltaX to set
	 */
	public void setDeltaX(int deltaX) {
		this.deltaX = deltaX;
	}

	/**
	 * @return the deltaY
	 */
	public int getDeltaY() {
		return deltaY;
	}

	/**
	 * @param deltaY the deltaY to set
	 */
	public void setDeltaY(int deltaY) {
		this.deltaY = deltaY;
	}

}
