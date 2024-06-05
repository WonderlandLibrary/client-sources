package zamorozka.modules.VISUALLY;

import java.util.ArrayList;

import de.Hero.settings.Setting;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class ShaderESP extends Module {
	public ShaderESP() {
		super("ShaderESP", 0, Category.VISUALLY);
	}

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("ShaderRed", this, 10, 0, 255, true));
		Zamorozka.settingsManager.rSetting(new Setting("ShaderGreen", this, 255, 0, 255, true));
		Zamorozka.settingsManager.rSetting(new Setting("ShaderBlue", this, 255, 0, 255, true));
	}
}