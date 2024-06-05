package zamorozka.modules.VISUALLY;

import de.Hero.settings.Setting;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class ItemPhysic extends Module {
	
	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("PhysicSpeed", this, 1, 0.1, 10, true));
	}
	
	public ItemPhysic() {
		super("ItemPhysic", 0, Category.Zamorozka);
	}

}
