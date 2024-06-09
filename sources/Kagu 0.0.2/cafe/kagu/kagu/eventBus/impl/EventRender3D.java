/**
 * 
 */
package cafe.kagu.kagu.eventBus.impl;

import cafe.kagu.kagu.eventBus.Event;

/**
 * @author lavaflowglow
 *
 */
public class EventRender3D extends Event {
	
	private float partialTicks;
	
	/**
	 * @param eventPosition The position of the event
	 * @param partialTicks The partial ticks
	 */
	public EventRender3D(EventPosition eventPosition, float partialTicks) {
		super(eventPosition);
		this.partialTicks = partialTicks;
	}
	
	/**
	 * @return the partialTicks
	 */
	public float getPartialTicks() {
		return partialTicks;
	}
	
	/**
	 * Not used in this event
	 */
	@Override
	@Deprecated
	public void cancel() {
		
	}
	
	/**
	 * Not used in this event
	 */
	@Override
	@Deprecated
	public void setCanceled(boolean canceled) {
		
	}
	
	/**
	 * @return false
	 */
	@Override
	@Deprecated
	public boolean isPre() {
		return false;
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
	
}
