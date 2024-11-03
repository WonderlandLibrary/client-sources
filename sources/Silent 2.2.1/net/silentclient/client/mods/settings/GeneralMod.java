package net.silentclient.client.mods.settings;

import net.silentclient.client.Client;
import net.silentclient.client.gui.lite.clickgui.ClickGUI;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;
import net.silentclient.client.mods.Setting;
import net.silentclient.client.utils.MenuBlurUtils;
import net.silentclient.client.utils.OSUtil;
import net.silentclient.client.utils.RawInputHandler;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class GeneralMod extends Mod {
	@Override
	public boolean isEnabled() {
		return true;
	}

	public GeneralMod() {
		super("General", ModCategory.SETTINGS, "silentclient/icons/settings/general.png");
	}
	
	public void setup() {
		this.addBooleanSetting("Gui Debug", this, false);
		this.addBooleanSetting("Hide mods in F3", this, true);
		this.addBooleanSetting("Vanilla ESC Menu Layout", this, false);
		this.addBooleanSetting("Menu Background Blur", this, true);
		this.addKeybindSetting("Mod Menu Keybind", this, Keyboard.KEY_RSHIFT);
		ArrayList<String> options = new ArrayList<>();
		options.add("Bottom Right Corner");
		options.add("Bottom Left Corner");
		options.add("Top Right Corner");
		options.add("Top Left Corner");
		this.addModeSetting("Silent Logo Location", this, "Bottom Right Corner", options);
		this.addBooleanSetting("Silent Button Sounds", this, false);
		if(OSUtil.isWindows()) {
			this.addBooleanSetting("Raw Mouse Input", this, true);
		}
		this.addBooleanSetting("Menu Animations", this, true);
		this.addSliderSetting("Menu Animations Speed", this, 300, 100, 500, true);
		this.addBooleanSetting("Disable Scroll Wheel", this, false);
	}
	
	@Override
	public void onChangeSettingValue(Setting setting) {
		switch(setting.getName()) {
		case "Raw Mouse Input":
			RawInputHandler.reload();
			RawInputHandler.toggleRawInput(setting.getValBoolean());
			break;
		case "Menu Background Blur":
			if(setting.getValBoolean()) {
				MenuBlurUtils.loadBlur(true);
			} else {
				MenuBlurUtils.unloadBlur(true);
			}
			break;
		case "Menu Animations":
		case "Menu Animations Speed":
			if(ClickGUI.introAnimation != null) {
				ClickGUI.introAnimation.setDuration(Client.getInstance().getSettingsManager().getSettingByName(this, "Menu Animations").getValBoolean() ? Client.getInstance().getSettingsManager().getSettingByName(this, "Menu Animations Speed").getValInt() : 1);
			}
			break;
		}
	}
}
