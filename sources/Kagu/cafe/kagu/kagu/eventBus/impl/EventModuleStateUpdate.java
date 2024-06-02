/**
 * 
 */
package cafe.kagu.kagu.eventBus.impl;

import cafe.kagu.kagu.eventBus.Event;

/**
 * @author lavaflowglow
 *
 */
public class EventModuleStateUpdate extends Event {

	/**
	 * @param eventPosition The position of the event
	 */
	public EventModuleStateUpdate(EventPosition eventPosition, boolean originalState, boolean newState) {
		super(eventPosition);
		this.originalState = originalState;
		this.newState = newState;
	}

	private boolean originalState, newState;

	/**
	 * @return the originalState
	 */
	public boolean isOriginalState() {
		return originalState;
	}

	/**
	 * @param originalState the originalState to set
	 */
	public void setOriginalState(boolean originalState) {
		this.originalState = originalState;
	}

	/**
	 * @return the newState
	 */
	public boolean isNewState() {
		return newState;
	}

	/**
	 * @param newState the newState to set
	 */
	public void setNewState(boolean newState) {
		this.newState = newState;
	}

}
