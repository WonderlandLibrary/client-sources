package win.sightclient.module.target;

import win.sightclient.module.Category;
import win.sightclient.module.Module;

public class TargetOther extends Module {

	public TargetOther() {
		super("Others", Category.TARGETS);
		this.setHidden(true);
	}
}
