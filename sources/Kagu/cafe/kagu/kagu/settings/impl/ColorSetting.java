/**
 * 
 */
package cafe.kagu.kagu.settings.impl;

import java.awt.Color;

import cafe.kagu.kagu.eventBus.impl.EventSettingUpdate;
import cafe.kagu.kagu.settings.Setting;
import cafe.kagu.kagu.utils.UiUtils;

/**
 * @author lavaflowglow
 *
 */
public class ColorSetting extends Setting<ColorSetting> {

	/**
	 * @param name The name of the setting
	 * @param defaultColor The default color for the setting
	 */
	public ColorSetting(String name, int defaultColor) {
		super(name);
		this.color = this.defaultColor = defaultColor;
	}
	
	private int color, defaultColor;
	
	/**
	 * @return the color
	 */
	public int getColor() {
		return color;
	}
	
	/**
	 * @param color the color to set
	 */
	public void setColor(int color) {
		this.color = color;
		
		// Kagu hook
		{
			EventSettingUpdate eventSettingUpdate = new EventSettingUpdate(this);
			eventSettingUpdate.post();
		}
	}
	
	/**
	 * @return the defaultColor
	 */
	public int getDefaultColor() {
		return defaultColor;
	}
	
	/**
	 * Calculates and returns the hsb values of the color
	 * @return The hsb value of the color in a float array, the orders of the values in the array are hue, saturation, and brightness
	 */
	public float[] getHsb() {
		float[] rgba = UiUtils.getFloatArrayFromColor(color);
		return Color.RGBtoHSB((int)(rgba[0] * 255), (int)(rgba[1] * 255), (int)(rgba[2] * 255), null);
	}
	
}
