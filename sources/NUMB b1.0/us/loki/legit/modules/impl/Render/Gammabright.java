package us.loki.legit.modules.impl.Render;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import de.Hero.settings.Setting;
import us.loki.legit.Client;
import us.loki.legit.events.EventUpdate;
import us.loki.legit.modules.*;

public class Gammabright extends Module {

	public Gammabright() {
		super("Gammabright", "Gammabright", Keyboard.KEY_B, Category.RENDER);
		Client.instance.setmgr.rSetting(new Setting("Gamma (Test)", this, 50.0, 0.0, 200.0, true));

	}

	@EventTarget
	public void onUpdate() {
		mc.gameSettings.gammaSetting = 1000f;
		mc.gameSettings.gammaSetting = 0f;
	}

	@Override
	public void onEnable() {
		EventManager.register(this);
		mc.gameSettings.gammaSetting = 1000f;
	}

	public void onDisable() {
		EventManager.unregister(this);
		mc.gameSettings.gammaSetting = 1f;
	}
}