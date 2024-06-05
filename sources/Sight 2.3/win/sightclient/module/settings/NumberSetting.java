package win.sightclient.module.settings;

import win.sightclient.Sight;
import win.sightclient.module.Module;

public class NumberSetting extends Setting {

	private double value;
	private double min;
	private double max;
	private boolean onlyInt;
	
	public NumberSetting(String name, Module parent, double defaultValue, double min, double max, boolean onlyInt) {
		super(name, parent);
		this.value = defaultValue;
		this.min = min;
		this.max = max;
		this.onlyInt = onlyInt;
	}
	
	@Override
	public Double getValue() {
		if (this.onlyInt) {
			this.value = (int)this.value;
		}
		return this.value;
	}
	
	public float getValueFloat() {
		if (this.onlyInt) {
			this.value = (int)this.value;
		}
		return (float)this.value;
	}
	
	public long getValueLong() {
		if (this.onlyInt) {
			this.value = (int)this.value;
		}
		return (long)this.value;
	}
	
	public int getValueInt() {
		return (int)this.value;
	}
	
 	public double getMin() {
 		return this.min;
 	}
 	
 	public double getMax() {
 		return this.max;
 	}
 	
 	public boolean getOnlyInt() {
 		return this.onlyInt;
 	}
 	
 	public void setValue(double value, boolean save) {
 		this.value = value;
		if (save) {
			Sight.instance.fileManager.saveDefaultConfig();
		}
 	}
}
