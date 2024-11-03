package net.silentclient.client.mods.render;

import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;

public class TitlesMod extends Mod {
	public TitlesMod() {
		super("Titles", ModCategory.MODS, "silentclient/icons/mods/titles.png", true);
	}
	
	@Override
	public void setup() {
		this.addSliderSetting("Scale", this, 1.0F, 0.2F, 2.0F, false);
	}
}
