package net.silentclient.client.mods.world;

import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;

public class FullBrightMod extends Mod {
	
	public FullBrightMod() {
		super("FullBright", ModCategory.MODS, "silentclient/icons/mods/fullbright.png");
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		mc.renderGlobal.loadRenderers();
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		mc.renderGlobal.loadRenderers();
	}
	
	
	
}
