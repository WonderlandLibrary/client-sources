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
public class DoubleSetting extends Setting<DoubleSetting> {

	/**
	 * @param name      The name of the setting
	 * @param value     The initial value of the setting
	 * @param min       The min value of the setting
	 * @param max       The max value of the setting
	 * @param increment How much this setting should increment by
	 */
	public DoubleSetting(String name, double value, double min, double max, double increment) {
		super(name);
		this.value = value;
		this.min = min;
		this.max = max;
		this.increment = increment;
	}
	
	private double value, min, max, increment;

	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		double precision = 1 / increment;
		this.value = Math.round(Math.max(min, Math.min(max, value)) * precision) / precision;
		
		// Kagu hook
		{
			EventSettingUpdate eventSettingUpdate = new EventSettingUpdate(this);
			eventSettingUpdate.post();
		}
	}

	/**
	 * @return the min
	 */
	public double getMin() {
		return min;
	}

	/**
	 * @param min the min to set
	 */
	public void setMin(double min) {
		this.min = min;
	}

	/**
	 * @return the max
	 */
	public double getMax() {
		return max;
	}

	/**
	 * @param max the max to set
	 */
	public void setMax(double max) {
		this.max = max;
	}

	/**
	 * @return the increment
	 */
	public double getIncrement() {
		return increment;
	}

	/**
	 * @param increment the increment to set
	 */
	public void setIncrement(double increment) {
		this.increment = increment;
	}
	
}
