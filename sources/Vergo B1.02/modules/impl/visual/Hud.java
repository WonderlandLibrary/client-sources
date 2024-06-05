package xyz.vergoclient.modules.impl.visual;

import java.util.Arrays;

import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.OnSettingChangeInterface;
import xyz.vergoclient.settings.BooleanSetting;
import xyz.vergoclient.settings.ModeSetting;
import xyz.vergoclient.settings.NumberSetting;
import xyz.vergoclient.settings.SettingChangeEvent;

public class Hud extends Module {

	public Hud() {
		super("Hud", Category.VISUAL);
	}

	public ModeSetting currentTheme = new ModeSetting("Theme", "Default", "Default", "New"),
						waterMark = new ModeSetting("Watermark", "Simple", "Simple", "vergosense", "Text", "Planet"),
						bpsMode = new ModeSetting("BPS Count", "Always On", "Always On", "Speed Only", "Never"),
						vergoColor = new ModeSetting("Colours", "Burgundy", "Burgundy", "Sea Blue", "Nuclear Green", "Rainbow");

	public BooleanSetting theFunny = new BooleanSetting("TheFunnyName", false), blurToggle = new BooleanSetting("ArrayBlur", true);

	@Override
	public void loadSettings() {
		currentTheme.modes.addAll(Arrays.asList("Default", "New"));

		waterMark.modes.addAll(Arrays.asList("Simple", "vergosense", "Text", "Planet"));

		addSettings(currentTheme, vergoColor, waterMark, bpsMode, theFunny, blurToggle);

	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public boolean isDisabled() {
		return false;
	}
	
}
