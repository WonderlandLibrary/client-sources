package win.sightclient.module.settings;

import win.sightclient.Sight;
import win.sightclient.module.Module;

public class BooleanSetting extends Setting {

	private boolean value;
	
	public BooleanSetting(String name, Module parent, boolean defaultValue) {
		super(name, parent);
		this.value = defaultValue;
	}
	
	@Override
	public Boolean getValue() {
		return this.value;
	}
	
	public void setValue(Boolean value, boolean save) {
		this.value = value;
		if (save) {
			Sight.instance.fileManager.saveDefaultConfig();
		}
	}

	public void toggle() {
		this.value = !this.value;
	}
}
