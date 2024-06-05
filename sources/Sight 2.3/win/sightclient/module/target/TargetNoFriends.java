package win.sightclient.module.target;

import win.sightclient.module.Category;
import win.sightclient.module.Module;

public class TargetNoFriends extends Module {

	public TargetNoFriends() {
		super("NoFriends", Category.TARGETS);
		this.setHidden(true);
	}
}
