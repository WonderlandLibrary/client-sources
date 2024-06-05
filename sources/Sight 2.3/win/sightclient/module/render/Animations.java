package win.sightclient.module.render;

import win.sightclient.Sight;
import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.module.settings.ModeSetting;

public class Animations extends Module {

	public static boolean toggled2;
	public static ModeSetting mode;
	
	public Animations() {
		super("Animations", Category.RENDER);
		
		Sight.instance.sm.rSetting(mode = new ModeSetting("Mode", this, new String[] {
				"Exhibition",
				"Stella",
				"Slick",
				"Push"
		}));
	}
	
	@Override
	public void updateSettings() {
		this.toggled2 = this.isToggled();
		this.setSuffix(this.mode.getValue());
	}
}
