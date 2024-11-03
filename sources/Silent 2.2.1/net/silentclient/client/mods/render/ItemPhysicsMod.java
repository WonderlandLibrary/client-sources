package net.silentclient.client.mods.render;

import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;

public class ItemPhysicsMod extends Mod {
	
	public ItemPhysicsMod() {
		super("Item Physics", ModCategory.MODS, "silentclient/icons/mods/itemphysics.png");
	}
	
	@Override
	public void setup() {
		this.addSliderSetting("Speed", this, 1, 0.5, 4, false);
	}
}
