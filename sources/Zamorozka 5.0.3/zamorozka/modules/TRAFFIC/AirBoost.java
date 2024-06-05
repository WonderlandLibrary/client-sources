package zamorozka.modules.TRAFFIC;

import de.Hero.settings.Setting;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class AirBoost extends Module {

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("AirBoostValue", this, 1.6, 0.5, 2, true));
	}

	public AirBoost() {
		super("AirBoost", 0, Category.TRAFFIC);
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		float f = (float) Zamorozka.settingsManager.getSettingByName("AirBoostValue").getValDouble();
		if (mc.player.hurtTime > 9) {
			mc.player.motionY = f;
		}
	}
}