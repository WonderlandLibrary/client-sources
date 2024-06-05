package zamorozka.modules.VISUALLY;

import de.Hero.settings.Setting;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class CustomHitColor extends Module {

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("DamageRed", this, 0, 1, 255, false));
		Zamorozka.settingsManager.rSetting(new Setting("DamageGreen", this, 0, 1, 255, false));
		Zamorozka.settingsManager.rSetting(new Setting("DamageBlue", this, 255, 1, 255, false));
		Zamorozka.settingsManager.rSetting(new Setting("DamageAlpha", this, 0.3F, 0.3F, 1F, false));
	}
	
	public CustomHitColor() {
		super("CustomHitColor", 0, Category.VISUALLY);
	}

}
