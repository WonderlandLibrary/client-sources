package win.sightclient.module.other;

import win.sightclient.Sight;
import win.sightclient.module.Category;
import win.sightclient.module.Module;

public class RPCMod extends Module {

	public RPCMod() {
		super("DiscordRPC", Category.OTHER);
		this.setHidden(true);
		this.setToggledNoSave(true);
	}

	@Override
	public void onEnable() {
		super.onEnable();
		Sight.instance.getRichPresence().init();
	}
	
	@Override
	public void onDisable() {
		Sight.instance.getRichPresence().shutdown();
	}
}
