package zamorozka.modules.VISUALLY;

import java.util.ArrayList;

import de.Hero.settings.Setting;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class NoOverlay extends Module {

	@Override
	public void setup() {
		Zamorozka.instance.settingsManager.rSetting(new Setting("AntiTotemAnimation", this, true));
		Zamorozka.instance.settingsManager.rSetting(new Setting("NoPotionDebug", this, true));
		Zamorozka.instance.settingsManager.rSetting(new Setting("NoExpBar", this, true));
		Zamorozka.instance.settingsManager.rSetting(new Setting("NoPumpkinOverlay", this, true));
		Zamorozka.instance.settingsManager.rSetting(new Setting("NoElementOverlay", this, true));
		Zamorozka.instance.settingsManager.rSetting(new Setting("NoBossBar", this, true));
		Zamorozka.instance.settingsManager.rSetting(new Setting("NoWeather", this, true));
		Zamorozka.instance.settingsManager.rSetting(new Setting("NoHeartOverlay", this, true));
		Zamorozka.instance.settingsManager.rSetting(new Setting("NoArrowInPlayer", this, true));
	}
	
	public NoOverlay() {
		super("NoOverlay", 0, Category.VISUALLY);
	}

}
