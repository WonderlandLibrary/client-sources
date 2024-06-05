package win.sightclient.module.settings;

import win.sightclient.Sight;
import win.sightclient.module.Module;

public class Setting {

	private String name;
	private Module parent;
	private boolean visible = true;
	
	public Setting(String name, Module parent) {
		this.name = name;
		this.parent = parent;
		
		Sight.instance.sm.rSetting(this);
	}
	
	public Object getValue() {
		return null;
	}
	public String getName() {
		return this.name;
	}
	
	public Module getModule() {
		return this.parent;
	}
	
	public boolean isVisible() {
		return this.visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
