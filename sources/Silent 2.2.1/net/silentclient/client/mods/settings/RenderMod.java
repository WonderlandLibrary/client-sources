package net.silentclient.client.mods.settings;

import net.silentclient.client.Client;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;

public class RenderMod extends Mod {
	@Override
	public boolean isEnabled() {
		return true;
	}

	public RenderMod() {
		super("Render", ModCategory.SETTINGS, "silentclient/icons/settings/render.png");
	}
	
	public void setup() {
		this.addBooleanSetting("Crosshair in F5", this, false);
		this.addBooleanSetting("Centered Potion Inventory", this, true);
		this.addBooleanSetting("Disable Achievements", this, false);
		this.addBooleanSetting("Model Bobbing Only", this, false);
		this.addBooleanSetting("Borderless Fullscreen", this, false);
	}
	
	public static boolean isBorderlessFullScreen() {
		return Client.getInstance().getSettingsManager().getSettingByClass(RenderMod.class, "Borderless Fullscreen").getValBoolean();
	}
}
