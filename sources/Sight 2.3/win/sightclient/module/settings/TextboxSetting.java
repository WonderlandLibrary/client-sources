package win.sightclient.module.settings;

import win.sightclient.Sight;
import win.sightclient.module.Module;

public class TextboxSetting extends Setting {

	private String value;
	
	public TextboxSetting(String name, Module parent, String defaultValue) {
		super(name, parent);
		this.value = defaultValue;
	}

	@Override
	public String getValue() {
		return this.value;
	}
	
	public void setValue(String value, boolean save) {
		this.value = value;
		if (save) {
			Sight.instance.fileManager.saveDefaultConfig();
		}
	}
}
