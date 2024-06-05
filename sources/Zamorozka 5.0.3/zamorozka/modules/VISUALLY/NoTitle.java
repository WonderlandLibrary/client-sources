package zamorozka.modules.VISUALLY;

import de.Hero.settings.Setting;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class NoTitle extends Module {

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("MainTitle", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("SubTitle", this, true));
	}
	
	public NoTitle() {
		super("NoTitle", 0, Category.VISUALLY);
	}
}