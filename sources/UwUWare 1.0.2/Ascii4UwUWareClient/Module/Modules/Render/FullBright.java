package Ascii4UwUWareClient.Module.Modules.Render;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.World.EventTick;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import java.awt.Color;

public class FullBright extends Module {
	private float old;

	public FullBright() {
		super("FullBright", new String[] { "fbright", "brightness", "bright" }, ModuleType.Render);
		this.setColor(new Color(244, 255, 149).getRGB());
	}

	@Override
	public void onEnable() {
		this.old = this.mc.gameSettings.gammaSetting;
	}

	@EventHandler
	private void onTick(EventTick e) {
		this.mc.gameSettings.gammaSetting = 1.5999999E7f;
	}

	@Override
	public void onDisable() {
		this.mc.gameSettings.gammaSetting = this.old;
	}
}
