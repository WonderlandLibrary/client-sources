package us.loki.legit.modules.impl.Other;

import de.Hero.settings.Setting;
import us.loki.legit.Client;
import us.loki.legit.gui.cirexminimap.MiniMapScreen;
import us.loki.legit.modules.*;

public class MiniMap extends Module {

	public MiniMap() {
		super("MiniMap","MiniMap", 0, Category.OTHER);
		Client.instance.setmgr.rSetting(new Setting("MiniMapX", this, 0, 0, 960, true));
		Client.instance.setmgr.rSetting(new Setting("MiniMapY", this, 0, 0, 510, true));
		Client.instance.setmgr.rSetting(new Setting("MiniMapWidth", this, 100, 0, 960, true));
		Client.instance.setmgr.rSetting(new Setting("MiniMapHeight", this, 100, 0, 510, true));

		Client.instance.setmgr.rSetting(new Setting("MiniMapIngame", this, true));

	}


	@Override
	public void onEnable() {
		setEnabled(false);
		mc.displayGuiScreen(new MiniMapScreen());
	}

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub

	}

}
