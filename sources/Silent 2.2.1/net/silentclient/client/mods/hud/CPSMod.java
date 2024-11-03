package net.silentclient.client.mods.hud;

import java.util.ArrayList;

import net.silentclient.client.Client;
import net.silentclient.client.mods.HudMod;
import net.silentclient.client.mods.ModCategory;

public class CPSMod extends HudMod {
	public CPSMod() {
		super("CPS", ModCategory.MODS, "silentclient/icons/mods/cps.png");
	}
	
	@Override
	public void setup() {
		super.setup();
		ArrayList<String> options = new ArrayList<>();
		
		options.add("LMB");
		options.add("RMB");
		options.add("LMB & RMB");

		this.addModeSetting("CPS Type", this, "LMB", options);
		this.addBooleanSetting("Remove CPS Text", this, false);
	}
	
	@Override
	public String getText() {
		String text = "";
		switch(Client.getInstance().getSettingsManager().getSettingByName(this, "CPS Type").getValString()) {
			case "LMB & RMB":
				if(Client.getInstance().getSettingsManager().getSettingByName(this, "Fancy Font").getValBoolean()) {
					text = "00 | 00" + getPostText();
				} else {
					text = "00 ┃ 00 " + getPostText();
				}
				break;
			default:
				text = "00 " + getPostText();
				break;
		}
		
		return text;
	}
	
	@Override
	public String getDefautPostText() {
		return "CPS";
	}
	
	@Override
	public String getTextForRender() {
		String text = "";
		switch(Client.getInstance().getSettingsManager().getSettingByName(this, "CPS Type").getValString()) {
			case "LMB & RMB":
				text = Client.getInstance().getCPSTracker().getLCPS() + (Client.getInstance().getSettingsManager().getSettingByName(this, "Fancy Font").getValBoolean() ? " | " : " ┃ ") + Client.getInstance().getCPSTracker().getRCPS() + (Client.getInstance().getSettingsManager().getSettingByName(this, "Remove CPS Text").getValBoolean() ? "" : " " + getPostText());
				break;
			case "RMB":
				text = Client.getInstance().getCPSTracker().getRCPS() + (Client.getInstance().getSettingsManager().getSettingByName(this, "Remove CPS Text").getValBoolean() ? "" : " " + getPostText());
				break;
			default:
				text = Client.getInstance().getCPSTracker().getLCPS() + (Client.getInstance().getSettingsManager().getSettingByName(this, "Remove CPS Text").getValBoolean() ? "" : " " + getPostText());
				break;
		}

		return text;
	}
}
