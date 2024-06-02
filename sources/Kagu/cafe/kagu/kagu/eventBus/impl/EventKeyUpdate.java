/**
 * 
 */
package cafe.kagu.kagu.eventBus.impl;

import cafe.kagu.kagu.eventBus.Event;

/**
 * @author lavaflowglow
 *
 */
public class EventKeyUpdate extends Event {

	/**
	 * @param eventPosition The position of the event
	 * @param keyCode The keycode for the key that was pressed
	 * @param pressed Whether the key was pressed down or not
	 */
	public EventKeyUpdate(EventPosition eventPosition, int keyCode, boolean pressed) {
		super(eventPosition);
		this.keyCode = keyCode;
		this.pressed = pressed;
	}

	private int keyCode;
	private boolean pressed;

	/**
	 * @return the keyCode
	 */
	public int getKeyCode() {
		return keyCode;
	}
	
	/**
	 * @return the pressed
	 */
	public boolean isPressed() {
		return pressed;
	}
	
}
