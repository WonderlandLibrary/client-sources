package win.sightclient.module.settings;

import win.sightclient.Sight;
import win.sightclient.module.Module;

public class ModeSetting extends Setting {

	private String value;
	private String[] options;
	
	public ModeSetting(String name, Module parent, String[] options) {
		super(name, parent);
		this.options = options;
		this.value = this.options[0];
	}

	public String[] getOptions() {
		return this.options;
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
