package us.loki.legit.modules.impl.Mods;

import org.lwjgl.input.Keyboard;

import de.Hero.settings.Setting;
import us.loki.legit.Client;
import us.loki.legit.modules.*;

public class Keystrokes extends Module {

	public Keystrokes() {
		super("Keystrokes", "Keystrokes", Keyboard.KEY_NONE, Category.MODS);
		Client.instance.setmgr.rSetting(new Setting("1", this, false));
		Client.instance.setmgr.rSetting(new Setting("2", this, false));
		Client.instance.setmgr.rSetting(new Setting("3", this, false));
		Client.instance.setmgr.rSetting(new Setting("Rainbow v1", this, false));
		Client.instance.setmgr.rSetting(new Setting("Rainbow v2", this, true));
	}

}
