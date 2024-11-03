package net.silentclient.client.mods.staff;

import java.awt.Color;
import java.util.ArrayList;

import net.silentclient.client.Client;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;
import net.silentclient.client.mods.Setting;

public class TestMod extends Mod {
	public TestMod() {
		super("Test", ModCategory.MODS, null);
	}
	
	@Override
	public void setup() {
		this.addBooleanSetting("Test Boolean", this, true);
		this.addSliderSetting("Test Slider", this, 10, 0, 20, true);
		this.addInputSetting("Test Input", this, "Test Value");
		this.addColorSetting("Test Color", this, new Color(255, 255, 255));
		ArrayList<String> options = new ArrayList<String>();
		options.add("Test Value 1");
		options.add("Test Value 2");
		options.add("Test Value 3");
		this.addModeSetting("Test Select", this, "Test Value 2", options);
	}
	
	@Override
	public void onChangeSettingValue(Setting setting) {
		Client.logger.info("[TestMod]: Changed Field: " + setting.getName());
		if(setting.isInput() || setting.isCombo()) {
			Client.logger.info("[TestMod]: Current Value: " + setting.getValString());
		}
		if(setting.isCheck()) {
			Client.logger.info("[TestMod]: Current Value: " + setting.getValBoolean());
		}
		if(setting.isSlider()) {
			Client.logger.info("[TestMod]: Current Value: " + setting.getValFloat());
		}
		if(setting.isColor()) {
			Client.logger.info("[TestMod]: Current Value: " + setting.getValColor().getRGB());
		}
	}
}
