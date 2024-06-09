package us.loki.legit.modules.impl.GUI;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import de.Hero.settings.Setting;
import us.loki.legit.Client;
import us.loki.legit.modules.*;

public class Cosmetics extends Module {

	public Cosmetics() {
		super("Cosmetics", "Cosmetics", Keyboard.KEY_NONE, Category.GUI);
		Client.instance.setmgr.rSetting(new Setting("Ears", this, false));
		Client.instance.setmgr.rSetting(new Setting("Wings", this, false));
		Client.instance.setmgr.rSetting(new Setting("BlazeSticks", this, false));
		Client.instance.setmgr.rSetting(new Setting("Angel", this, false));
		Client.instance.setmgr.rSetting(new Setting("Demon", this, false));
		Client.instance.setmgr.rSetting(new Setting("Freakshow", this, false));
	}
}