package win.sightclient.module.target;

import win.sightclient.module.Category;
import win.sightclient.module.Module;

public class TargetPlayers extends Module {

	public TargetPlayers() {
		super("Players", Category.TARGETS);
		this.setToggled(true);
		this.setHidden(true);
	}
}
