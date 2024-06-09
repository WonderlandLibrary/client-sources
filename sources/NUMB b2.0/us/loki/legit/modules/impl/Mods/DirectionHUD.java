package us.loki.legit.modules.impl.Mods;

import org.lwjgl.input.Keyboard;

import de.Hero.settings.Setting;
import us.loki.legit.Client;
import us.loki.legit.modules.*;

public class DirectionHUD extends Module {

	public DirectionHUD() {
		super("DirectionHUD", "DirectionHUD", Keyboard.KEY_NONE, Category.MODS);
		Client.instance.setmgr.rSetting(new Setting("Under", this, false));
		Client.instance.setmgr.rSetting(new Setting("Top", this, true));
	}

}