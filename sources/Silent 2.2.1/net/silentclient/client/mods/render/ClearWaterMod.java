package net.silentclient.client.mods.render;

import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;

public class ClearWaterMod extends Mod {
	public ClearWaterMod() {
		super("Clear Water", ModCategory.MODS, "silentclient/icons/mods/clearwater.png");
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
        mc.renderGlobal.loadRenderers();
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
        mc.renderGlobal.loadRenderers();
	}
}
