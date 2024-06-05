package win.sightclient.module.scripts;

import win.sightclient.Sight;
import win.sightclient.module.Category;
import win.sightclient.module.Module;

public class Reload extends Module {

	public Reload() {
		super("Reload", Category.SCRIPTS);
	}

	@Override
	public void onEnable() {
		this.setToggled(false);
		mc.thePlayer.sendChatMessage(".reload");
	}
}
