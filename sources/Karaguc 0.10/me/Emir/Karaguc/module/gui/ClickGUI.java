package me.Emir.Karaguc.module.gui;

import de.Hero.settings.Setting;
import me.Emir.Karaguc.Karaguc;
import me.Emir.Karaguc.module.Category;
import me.Emir.Karaguc.module.Module;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class ClickGUI extends Module {
	
	public ClickGUI() {
		super("ClickGUI", Keyboard.KEY_RSHIFT, Category.GUI);
		
		ArrayList<String> options = new ArrayList<>();
        options.add("Karaguc");
        options.add("Karaguc2");
		options.add("HeroGui-New");
		options.add("JellyLike");
		//options.add("Exhibition CSGO SKID");
		//options.add("Virtue");
		//options.add("Pandora");
		//options.add("Bad GUI");
        Karaguc.instance.settingsManager.rSetting(new Setting("Design", this, "Karaguc", options));
        Karaguc.instance.settingsManager.rSetting(new Setting("Sound", this, false));
        Karaguc.instance.settingsManager.rSetting(new Setting("GuiRed", this, 255, 0, 255, true));
        Karaguc.instance.settingsManager.rSetting(new Setting("GuiGreen", this, 26, 0, 255, true));
        Karaguc.instance.settingsManager.rSetting(new Setting("GuiBlue", this, 42, 0, 255, true));

	}

	@Override
	public void onEnable() {
		super.onEnable();
		
		mc.displayGuiScreen(Karaguc.instance.clickGUI);


		this.toggle();
	}
}
