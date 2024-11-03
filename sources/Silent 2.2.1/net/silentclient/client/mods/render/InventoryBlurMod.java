package net.silentclient.client.mods.render;

import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;
import net.silentclient.client.utils.NotificationUtils;

public class InventoryBlurMod extends Mod {
	public InventoryBlurMod() {
		super("Inventory Blur", ModCategory.MODS, "silentclient/icons/mods/inventoryblur.png");
	}
	
	@Override
	public void setup() {
		super.setup();
		this.addBooleanSetting("Dark Background", this, false);
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
	}
}
