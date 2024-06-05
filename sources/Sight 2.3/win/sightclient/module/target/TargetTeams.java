package win.sightclient.module.target;

import win.sightclient.module.Category;
import win.sightclient.module.Module;

public class TargetTeams extends Module {

	public TargetTeams() {
		super("Teams", Category.TARGETS);
		this.setHidden(true);
	}
}
