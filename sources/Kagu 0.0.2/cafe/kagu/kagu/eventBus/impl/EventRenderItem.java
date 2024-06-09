/**
 * 
 */
package cafe.kagu.kagu.eventBus.impl;

import cafe.kagu.kagu.eventBus.Event;
import net.minecraft.item.EnumAction;

/**
 * @author lavaflowglow
 *
 */
public class EventRenderItem extends Event {
	
	/**
	 * @param eventPosition The position of the event
	 * @param action The item action
	 * @param equipProgress The equip progress
	 * @param swingProgress The swing progress
	 */
	public EventRenderItem(EventPosition eventPosition, EnumAction action, float equipProgress, float swingProgress) {
		super(eventPosition);
		this.action = action;
		this.equipProgress = equipProgress;
		this.swingProgress = swingProgress;
	}

	private EnumAction action;
	private float equipProgress, swingProgress;

	/**
	 * @return the action
	 */
	public EnumAction getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(EnumAction action) {
		this.action = action;
	}

	/**
	 * @return the equipProgress
	 */
	public float getEquipProgress() {
		return equipProgress;
	}

	/**
	 * @param equipProgress the equipProgress to set
	 */
	public void setEquipProgress(float equipProgress) {
		this.equipProgress = equipProgress;
	}

	/**
	 * @return the swingProgress
	 */
	public float getSwingProgress() {
		return swingProgress;
	}

	/**
	 * @param swingProgress the swingProgress to set
	 */
	public void setSwingProgress(float swingProgress) {
		this.swingProgress = swingProgress;
	}

}
