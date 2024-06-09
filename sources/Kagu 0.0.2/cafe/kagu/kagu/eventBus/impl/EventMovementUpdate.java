/**
 * 
 */
package cafe.kagu.kagu.eventBus.impl;

import cafe.kagu.kagu.eventBus.Event;

/**
 * @author lavaflowglow
 *
 */
public class EventMovementUpdate extends Event {
	
	/**
	 * @param forward The forward movement of the player
	 * @param strafe The strafe movement of the player
	 * @param eventPosition The position of the event
	 */
	public EventMovementUpdate(EventPosition eventPosition, float forward, float strafe) {
		super(eventPosition);
		this.forward = forward;
		this.strafe = strafe;
	}

	private float forward, strafe;

	/**
	 * @return the forward
	 */
	public float getForward() {
		return forward;
	}

	/**
	 * @param forward the forward to set
	 */
	public void setForward(float forward) {
		this.forward = forward;
	}

	/**
	 * @return the strafe
	 */
	public float getStrafe() {
		return strafe;
	}

	/**
	 * @param strafe the strafe to set
	 */
	public void setStrafe(float strafe) {
		this.strafe = strafe;
	}
	
	/**
	 * This event doesn't have a post call
	 * @return false
	 */
	@Override
	@Deprecated
	public boolean isPost() {
		return false;
	}
	
	/**
	 * @return false
	 */
	@Override
	@Deprecated
	public boolean isPre() {
		return false;
	}
	
}
