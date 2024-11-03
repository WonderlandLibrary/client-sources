package net.silentclient.client.mods.staff;

import net.silentclient.client.Client;
import net.silentclient.client.event.EventTarget;
import net.silentclient.client.event.impl.EventDebugFps;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;

public class FPSSpoofer extends Mod {
	public FPSSpoofer() {
		super("FPS Spoofer", ModCategory.MODS, null);
	}
	
	@Override
	public void setup() {
		this.addSliderSetting("Multiplication", this, 3, 1, 20, true);
	}
	
	@EventTarget
	public void onDebugFps(EventDebugFps event) {
		event.setFps(Client.getInstance().getSettingsManager().getSettingByName(this, "Multiplication").getValInt() * event.getFps());
	}
}
