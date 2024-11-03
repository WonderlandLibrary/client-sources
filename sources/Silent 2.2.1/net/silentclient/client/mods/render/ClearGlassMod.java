package net.silentclient.client.mods.render;

import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;

public class ClearGlassMod extends Mod {
	public ClearGlassMod() {
		super("Clear Glass", ModCategory.MODS, "silentclient/icons/mods/clearglass.png");
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
