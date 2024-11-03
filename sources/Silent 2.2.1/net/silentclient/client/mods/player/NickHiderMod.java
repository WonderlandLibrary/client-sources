package net.silentclient.client.mods.player;

import net.silentclient.client.Client;
import net.silentclient.client.event.EventTarget;
import net.silentclient.client.event.impl.EventText;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;

public class NickHiderMod extends Mod {
	public NickHiderMod() {
		super("Nick Hider", ModCategory.MODS, "silentclient/icons/mods/nickhider.png");
	}
	
	@Override
	public void setup() {
		this.addBooleanSetting("Hide Your Name", this, true);
		this.addBooleanSetting("Hide Skins", this, false);
		this.addBooleanSetting("Use Own Skin For All", this, false);
		this.addBooleanSetting("Use Real Skin For Self", this, true);
		this.addInputSetting("Custom Name", this, "You");
	}
	
	@EventTarget
	public void onText(EventText event) {
		if(Client.getInstance().getSettingsManager().getSettingByName(this, "Hide Your Name").getValBoolean()) {
			event.replace(mc.getSession().getUsername(), Client.getInstance().getSettingsManager().getSettingByName(this, "Custom Name").getValString());
		}
	}
}
