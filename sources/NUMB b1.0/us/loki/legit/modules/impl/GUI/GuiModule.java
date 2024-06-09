package us.loki.legit.modules.impl.GUI;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import de.Hero.settings.Setting;
import us.loki.legit.*;
import us.loki.legit.gui.clickgui.ClickGui;
import us.loki.legit.modules.*;
import us.loki.legit.utils.TimeHelper;

/**
 * Made by HeroCode & xTrM_ it's free to use but you have to credit us
 *
 * @author HeroCode
 */
public class GuiModule extends Module {

	public ClickGui clickgui;
	public static boolean active;

	public GuiModule() {
		super("ClickGUI", "ClickGUI", Keyboard.KEY_RSHIFT, Category.GUI);
		ArrayList<String> options = new ArrayList<>();
		options.add("DefaultOption");
		options.add("Option2");
		options.add("Option3");
		Client.instance.setmgr.rSetting(new Setting("OptionSelector", this, "DefaultOption", options));
		Client.instance.setmgr.rSetting(new Setting("BooleanOption", this, false));
		Client.instance.setmgr.rSetting(new Setting("SliderOptionInt", this, 255, 0, 255, true));
		Client.instance.setmgr.rSetting(new Setting("SliderOptionDouble", this, 10, 0, 20, false));

	}

	@Override
	public void onEnable() {
		if (this.clickgui == null)
			this.clickgui = new ClickGui();

		mc.displayGuiScreen(this.clickgui);
		toggle();
		active = true;
		super.onEnable();
	}
	@Override
	public void onUpdate() {
		if (!this.isEnabled())
			return;
		super.onUpdate();
	}
	@Override
	public void onDisable() {
		active = false;
		super.onDisable();
	}
}
