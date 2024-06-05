package zamorozka.modules.VISUALLY;

import de.Hero.settings.Setting;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class Outline extends Module {

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("OutlineWidth", this, 4, 0.1, 10, true));
	}
	
	public Outline() {
		super("PlayerOutline", 0, Category.VISUALLY);
	}
		
}
