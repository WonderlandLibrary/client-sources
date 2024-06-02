/**
 * 
 */
package cafe.kagu.kagu.settings.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cafe.kagu.kagu.eventBus.impl.EventSettingUpdate;
import cafe.kagu.kagu.settings.Setting;

/**
 * @author lavaflowglow
 *
 */
public class ModeSetting extends Setting<ModeSetting> {
	
	/**
	 * @param name
	 * @param defaultMode The default mode
	 * @param modes The modes, must include the default mode
	 */
	public ModeSetting(String name, String defaultMode, String... modes) {
		super(name);
		this.modes.addAll(Arrays.asList(modes));
		modeIndex = this.modes.indexOf(defaultMode) == -1 ? 0 : this.modes.indexOf(defaultMode);
	}
	
	private List<String> modes = new ArrayList<>();
	private int modeIndex;
	private double clickguiToggleStatus = 0;
	private boolean clickguiExtended = false;
	
	/**
	 * @return the modes
	 */
	public List<String> getModes() {
		return modes;
	}

	/**
	 * @param mode Sets the mode
	 */
	public void setMode(String mode) {
		modeIndex = modes.indexOf(mode) == -1 ? 0 : modes.indexOf(mode);
		
		// Kagu hook
		{
			EventSettingUpdate eventSettingUpdate = new EventSettingUpdate(this);
			eventSettingUpdate.post();
		}
		
	}
	
	/**
	 * @return The current mode
	 */
	public String getMode() {
		return modeIndex >= modes.size() ? (modes.size() == 0 ? " " : modes.get(modeIndex = 0)) : modes.get(modeIndex);
	}
	
	/**
	 * @param mode The mode that you want to check
	 * @return Whether or not the passed in mode is the current mode
	 */
	public boolean is(String mode) {
		return modes.get(modeIndex).equals(mode);
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

	/**
	 * @return the clickguiExtended
	 */
	public boolean isClickguiExtended() {
		return clickguiExtended;
	}

	/**
	 * @param clickguiExtended the clickguiExtended to set
	 */
	public void setClickguiExtended(boolean clickguiExtended) {
		this.clickguiExtended = clickguiExtended;
	}

}
