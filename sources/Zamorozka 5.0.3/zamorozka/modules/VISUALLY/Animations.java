package zamorozka.modules.VISUALLY;

import de.Hero.settings.Setting;
import zamorozka.event.EventTarget;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class Animations extends Module {

	@Override
    public void setup() {
        Zamorozka.settingsManager.rSetting(new Setting("HandSpeed", this, 6.0D, 0.5D, 30.0D, false));
        Zamorozka.settingsManager.rSetting(new Setting("ItemDistance", this, 1.0F, 1.0F, 2.5F, false));
        Zamorozka.settingsManager.rSetting(new Setting("ItemSize", this, 1.0F, 0.1F, 5F, false));
    }
	
	public Animations() {
		super("Animations", 0, Category.VISUALLY);
	}
}
