package zamorozka.modules.HUD;

import java.util.ArrayList;

import de.Hero.settings.Setting;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class Logo extends Module {

	public Logo() {
		super("Logo", 0, Category.Hud);
	}

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("ArrayColor");
		options.add("OLdColor");
		options.add("FullColor");
		options.add("OneLetter");
		Zamorozka.instance.settingsManager.rSetting(new Setting("Logo Mode", this, "ArrayColor", options));
		Zamorozka.settingsManager.rSetting(new Setting("LogoBackground", this, true));
	}
}