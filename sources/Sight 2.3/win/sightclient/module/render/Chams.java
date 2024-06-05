package win.sightclient.module.render;

import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.module.settings.NumberSetting;

public class Chams extends Module {

	public static boolean toggled2;
	public static NumberSetting hue;
	
	public Chams() {
		super("Chams", Category.RENDER);
		hue = new NumberSetting("Hue", this, 0.1F, 0, 1F, false);
	}
	
	@Override
	public void updateSettings() {
		toggled2 = this.isToggled();
	}

}
