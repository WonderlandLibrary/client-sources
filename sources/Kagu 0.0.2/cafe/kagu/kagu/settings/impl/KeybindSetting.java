/**
 * 
 */
package cafe.kagu.kagu.settings.impl;

import cafe.kagu.kagu.settings.Setting;

/**
 * @author lavaflowglow
 *
 */
public class KeybindSetting extends Setting<KeybindSetting> {

	/**
	 * @param name The name of the module
	 */
	public KeybindSetting(String name, int defaultKeybind) {
		super(name);
		this.keybind = defaultKeybind;
	}

	private int keybind;
	private double clickguiAnimation = 0;

	/**
	 * @return the keybind
	 */
	public int getKeybind() {
		return keybind;
	}

	/**
	 * @param keybind the keybind to set
	 */
	public void setKeybind(int keybind) {
		this.keybind = keybind;
	}

	/**
	 * @return the clickguiAnimation
	 */
	public double getClickguiAnimation() {
		return clickguiAnimation;
	}

	/**
	 * @param clickguiAnimation the clickguiAnimation to set
	 */
	public void setClickguiAnimation(double clickguiAnimation) {
		this.clickguiAnimation = clickguiAnimation;
	}

}
