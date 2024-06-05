package win.sightclient.module.render;

import win.sightclient.module.Category;
import win.sightclient.module.Module;

public class NotifModule extends Module {

	public NotifModule() {
		super("Notifications", Category.RENDER);
		this.setHidden(true);
		this.setToggled(true);
	}
}
