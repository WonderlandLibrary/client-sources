package net.silentclient.client.mods.settings;

import net.silentclient.client.Client;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;
import net.silentclient.client.mods.Setting;

import java.util.ArrayList;

public class CosmeticsMod extends Mod {
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public CosmeticsMod() {
		super("Cosmetics", ModCategory.SETTINGS, "silentclient/icons/settings/cosmetics.png");
	}
	
	@Override
	public void setup() {
		this.addBooleanSetting("Capes", this, true);
		ArrayList<String> options = new ArrayList<>();
		
		options.add("Rectangle");
		options.add("Curved Rectangle");
		options.add("Dynamic Curved");
		
		this.addModeSetting("Cape Type", this, "Dynamic Curved", options);
		this.addBooleanSetting("Cape Shoulders", this, true);
		
		this.addBooleanSetting("Wings", this, true);
		this.addSliderSetting("Wings Scale", this, 1, 0.7, 1, false);
		
		this.addBooleanSetting("Bandanas", this, true);
		this.addBooleanSetting("Hats", this, true);
		this.addBooleanSetting("Shields", this, true);

		this.addBooleanSetting("Custom Skins", this, true);
 	}
	
	@Override
	public void onChangeSettingValue(Setting setting) {
		switch(setting.getName()) {
		case "Cape Shoulders":
			Client.getInstance().getAccount().setCapeShoulders(setting.getValBoolean());
			break;
		case "Cape Type":
            Client.getInstance().getAccount().setCapeType(setting.getValString().equals("Dynamic Curved") ? "dynamic_curved" : setting.getValString().equals("Curved Rectangle") ? "curved_rectangle" : "rectangle");
			break;
		}
	}
}
