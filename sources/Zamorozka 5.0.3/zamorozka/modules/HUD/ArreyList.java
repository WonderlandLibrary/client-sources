package zamorozka.modules.HUD;

import java.awt.Color;
import java.util.ArrayList;

import de.Hero.settings.Setting;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventRender2D;
import zamorozka.event.events.EventRender3D;
import zamorozka.event.events.EventUpdate;
import zamorozka.gui.GuiIngameHook;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.ColorUtilities;
import zamorozka.ui.Colors;

public class ArreyList extends Module {

	public ArreyList() {
		super("ArrayList", 0, Category.Hud);
	}

	@Override
	public void setup() {

		ArrayList<String> options1 = new ArrayList<>();
		options1.add("Width");
		options1.add("Height");
		Zamorozka.instance.settingsManager.rSetting(new Setting("ArraySort Mode", this, "Width", options1));

		ArrayList<String> options = new ArrayList<>();
		options.add("Custom");
		options.add("Rainbow");
		options.add("GreenWhite");
		options.add("White");
		options.add("Pulse");
		options.add("Astolfo");
		options.add("Red-Blue");
		options.add("Grape");
		options.add("None");
		options.add("Category");
		Zamorozka.instance.settingsManager.rSetting(new Setting("Array Mode", this, "Astolfo", options));
		Zamorozka.settingsManager.rSetting(new Setting("Rainbow Spread", this, .1, 0, 1, true));
		Zamorozka.settingsManager.rSetting(new Setting("Rainbow Saturation", this, .8, 0, 1, true));
		Zamorozka.settingsManager.rSetting(new Setting("ArrayRect", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("ArrayOutline", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("ArrayBackground", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("ArrayBackgroundAplha", this, 35, 1, 255, true));
		Zamorozka.settingsManager.rSetting(new Setting("ArrayBackgroundBrightness", this, 35, 1, 255, true));
		Zamorozka.settingsManager.rSetting(new Setting("CustomOneRed", this, 255, 0, 255, false));
		Zamorozka.settingsManager.rSetting(new Setting("CustomOneGreen", this, 255, 0, 255, false));
		Zamorozka.settingsManager.rSetting(new Setting("CustomOneBlue", this, 255, 0, 255, false));
		Zamorozka.settingsManager.rSetting(new Setting("CustomTwoRed", this, 255, 0, 255, false));
		Zamorozka.settingsManager.rSetting(new Setting("CustomTwoGreen", this, 255, 0, 255, false));
		Zamorozka.settingsManager.rSetting(new Setting("CustomTwoBlue", this, 255, 0, 255, false));
		Zamorozka.settingsManager.rSetting(new Setting("CustomColorTime", this, 10, 1, 100, false));
	}

	@EventTarget
	public void onRender(EventRender2D event) {
		String mode = Zamorozka.instance.settingsManager.getSettingByName("Array Mode").getValString();
		String modeput = Character.toUpperCase(mode.charAt(0)) + mode.substring(1);
		this.setDisplayName("ArrayList §f§" + " " + modeput);
		GuiIngameHook.renderArrayList(event.getResolution());
	}
}