package win.sightclient.module.settings.button;

import win.sightclient.module.Module;
import win.sightclient.module.settings.Setting;

public class ButtonSetting extends Setting {

	private Execute toInvoke;
	
	public ButtonSetting(String name, Module parent, Execute toInvoke) {
		super(name, parent);
		this.toInvoke = toInvoke;
	}

	public void click() {
		this.toInvoke.onButtonClick();
	}
}
