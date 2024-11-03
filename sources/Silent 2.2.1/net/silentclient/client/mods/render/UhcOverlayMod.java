package net.silentclient.client.mods.render;

import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;

public class UhcOverlayMod extends Mod {
	public UhcOverlayMod() {
		super("UHC Overlay", ModCategory.MODS, "silentclient/icons/mods/uhcoverlay.png");
	}
	
	@Override
	public void setup() {
		this.addSliderSetting("Gold Ingot Scale", this, 1.5F, 1.0F, 5.0F, false);
		this.addSliderSetting("Gold Nugget Scale", this, 1.5F, 1.0F, 5.0F, false);
		this.addSliderSetting("Gold Ore Scale", this, 1.5F, 1.0F, 5.0F, false);
		this.addSliderSetting("Gold Apple Scale", this, 1.5F, 1.0F, 5.0F, false);
		this.addSliderSetting("Skull Scale", this, 1.5F, 1.0F, 5.0F, false);
	} 
}
