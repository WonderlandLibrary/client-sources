package net.silentclient.client.mods.render;

import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;

public class NametagsMod extends Mod {
	public NametagsMod() {
		super("Nametags", ModCategory.MODS, "silentclient/icons/mods/nametags.png", true);
	}
	
	@Override
	public void setup() {
		this.addBooleanSetting("Show in F5", this, true);
		this.addBooleanSetting("Show in F1", this, false);
		this.addBooleanSetting("Background", this, true);
		this.addBooleanSetting("Font Shadow", this, false);
		this.addBooleanSetting("Show Nametag Icons", this, true);
		this.addBooleanSetting("Show Nametag Messages", this, true);
	}
}
