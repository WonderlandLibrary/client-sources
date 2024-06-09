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
public class IntegerSetting extends Setting<IntegerSetting> {

	/**
	 * @param name      The name of the setting
	 * @param value     The initial value of the setting
	 * @param min       The min value of the setting
	 * @param max       The max value of the setting
	 * @param increment How much this setting should increment by
	 */
	public IntegerSetting(String name, int value, int min, int max, int increment) {
		super(name);
		this.value = value;
		this.min = min;
		this.max = max;
		this.increment = increment;
	}
	
	private int value, min, max, increment;

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		double precision = 1 / (double)increment;
		this.value = (int)(Math.round(Math.max(min, Math.min(max, value)) * precision) / precision);
		
		// Kagu hook
		{
			EventSettingUpdate eventSettingUpdate = new EventSettingUpdate(this);
			eventSettingUpdate.post();
		}
	}

	/**
	 * @return the min
	 */
	public int getMin() {
		return min;
	}

	/**
	 * @param min the min to set
	 */
	public void setMin(int min) {
		this.min = min;
	}

	/**
	 * @return the max
	 */
	public int getMax() {
		return max;
	}

	/**
	 * @param max the max to set
	 */
	public void setMax(int max) {
		this.max = max;
	}

	/**
	 * @return the increment
	 */
	public int getIncrement() {
		return increment;
	}

	/**
	 * @param increment the increment to set
	 */
	public void setIncrement(int increment) {
		this.increment = increment;
	}

}
