package net.silentclient.client.mods.render;

import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;

public class TabMod extends Mod {

	public TabMod() {
		super("Tab", ModCategory.MODS, "silentclient/icons/mods/tab.png", true);
	}
	
	@Override
	public void setup() {
		this.addBooleanSetting("Background", this, true);
		this.addBooleanSetting("Disable Header", this, false);
		this.addBooleanSetting("Disable Footer", this, false);
		this.addBooleanSetting("Show Ping Numbers", this, true);
		this.addBooleanSetting("Show Nametag Icons", this, true);
	}
}
