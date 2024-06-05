package win.sightclient.module.target;

import win.sightclient.module.Category;
import win.sightclient.module.Module;

public class TargetInvisibles extends Module {

	public TargetInvisibles() {
		super("Invisibles", Category.TARGETS);
		this.setHidden(true);
	}
}
