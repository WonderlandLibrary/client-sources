package net.silentclient.client.mods.render;

import net.silentclient.client.Client;
import net.silentclient.client.event.EventTarget;
import net.silentclient.client.event.impl.EventHitOverlay;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;

import java.awt.*;

public class HitColorMod extends Mod {
	public HitColorMod() {
		super("Hit Color", ModCategory.MODS, "silentclient/icons/mods/hitcolor.png");
	}
	
	@Override
	public void setup() {
		this.addColorSetting("Color", this, new Color(255, 0, 0), 76);
	}
	
	@EventTarget
	public void onHitOverlay(EventHitOverlay event) {
		Color color = Client.getInstance().getSettingsManager().getSettingByName(this, "Color").getValColor();
		
		event.setRed(color.getRed() / 255F);
		event.setGreen(color.getGreen() / 255F);
		event.setBlue(color.getBlue() / 255F);
		event.setAlpha(color.getAlpha() / 255F);
	}
}
