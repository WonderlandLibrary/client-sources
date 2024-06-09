/**
 * 
 */
package cafe.kagu.kagu.eventBus.impl;

import cafe.kagu.kagu.eventBus.Event;
import net.minecraft.entity.Entity;

/**
 * @author lavaflowglow
 *
 */
public class EventEntityRender extends Event {

	/**
	 * @param eventPosition The position of the event
	 * @param entity        The entity being rendered
	 * @param partialTicks  The partial ticks
	 */
	public EventEntityRender(EventPosition eventPosition, Entity entity, float partialTicks) {
		super(eventPosition);
		this.entity = entity;
		this.partialTicks = partialTicks;
	}

	private Entity entity;
	private float partialTicks;

	/**
	 * @return the entity
	 */
	public Entity getEntity() {
		return entity;
	}

	/**
	 * @return the partialTicks
	 */
	public float getPartialTicks() {
		return partialTicks;
	}

}
