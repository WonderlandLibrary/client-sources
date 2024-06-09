/**
 * 
 */
package cafe.kagu.kagu.eventBus.impl;

import cafe.kagu.kagu.eventBus.Event;
import cafe.kagu.kagu.eventBus.Event.EventPosition;
import cafe.kagu.kagu.settings.Setting;

/**
 * @author lavaflowglow
 *
 */
public class EventSettingUpdate extends Event {

	/**
	 * @param eventPosition
	 */
	public EventSettingUpdate(Setting setting) {
		super(EventPosition.POST);
		this.setting = setting;
	}

	private Setting setting;

	/**
	 * @return the setting
	 */
	public Setting getSetting() {
		return setting;
	}
	
	/**
	 * Does nothing, not used in this event
	 */
	@Override
	@Deprecated
	public void cancel() {
		
	}
	
	/**
	 * Does nothing, not used in this event
	 */
	@Override
	@Deprecated
	public void setCanceled(boolean canceled) {
		
	}
	
	/**
	 * @return false, not used in this event
	 */
	@Deprecated
	public boolean isPre() {
		return false;
	}

	/**
	 * @return false, not used in this event
	 */
	@Deprecated
	public boolean isPost() {
		return false;
	}
	
}
