package zamorozka.modules.COMBAT;

import de.Hero.settings.Setting;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.ClientUtils;

public class Reach extends Module {
	public Reach() {
		super("Reach", 0, Category.COMBAT);
	}

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("Reach", this, 3.2, 3, 5, true));
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		double reach = Zamorozka.settingsManager.getSettingByName("Reach").getValDouble();
		this.setDisplayName("Reach §f§ "
				+ ClientUtils.round((float) Zamorozka.settingsManager.getSettingByName("Reach").getValDouble(), 2));
	}

}