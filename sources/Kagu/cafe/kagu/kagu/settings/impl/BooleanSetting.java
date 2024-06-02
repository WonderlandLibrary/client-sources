/**
 * 
 */
package cafe.kagu.kagu.settings.impl;

import cafe.kagu.kagu.eventBus.impl.EventSettingUpdate;
import cafe.kagu.kagu.settings.Setting;

/**
 * @author lavaflowglow
 *
 */
public class BooleanSetting extends Setting<BooleanSetting> {

	/**
	 * @param name      The name of the module
	 * @param enabled   Whether the setting is enabled by default or not
	 */
	public BooleanSetting(String name, boolean enabled) {
		super(name);
		this.enabled = enabled;
	}
	
	private boolean enabled;
	private double clickguiToggleStatus = 0;

	/**
	 * @return True if the client is enabled, false otherwise
	 */
	public boolean isEnabled() {
		return enabled;
	}
	
	/**
	 * @return True if the client is disabled, false otherwise
	 */
	public boolean isDisabled() {
		return !enabled;
	}
	
	/**
	 * Disables the setting, does nothing if the setting is already disabled
	 */
	public void disable() {
		enabled = false;
		
		// Kagu hook
		{
			EventSettingUpdate eventSettingUpdate = new EventSettingUpdate(this);
			eventSettingUpdate.post();
		}
		
	}
	
	/**
	 * Enables the setting, does nothing if the setting is already enabled
	 */
	public void enable() {
		enabled = true;
		
		// Kagu hook
		{
			EventSettingUpdate eventSettingUpdate = new EventSettingUpdate(this);
			eventSettingUpdate.post();
		}
		
	}
	
	/**
	 * Flips the state of the setting
	 */
	public void toggle() {
		enabled = !enabled;
		
		// Kagu hook
		{
			EventSettingUpdate eventSettingUpdate = new EventSettingUpdate(this);
			eventSettingUpdate.post();
		}
		
	}

	/**
	 * @return the clickguiToggleStatus
	 */
	public double getClickguiToggleStatus() {
		return clickguiToggleStatus;
	}

	/**
	 * @param clickguiToggleStatus the clickguiToggleStatus to set
	 */
	public void setClickguiToggleStatus(double clickguiToggleStatus) {
		this.clickguiToggleStatus = clickguiToggleStatus;
	}

}
