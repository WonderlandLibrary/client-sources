package win.sightclient.module.target;

import win.sightclient.module.Category;
import win.sightclient.module.Module;

public class TargetIgnoreDead extends Module {

	public TargetIgnoreDead() {
		super("IgnoreDead", Category.TARGETS);
		this.setHidden(true);
	}
}
