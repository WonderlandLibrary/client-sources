package zamorozka.modules.TRAFFIC;

import de.Hero.settings.Setting;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class CustomJumpHeight extends Module {

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("JumpHeight", this, 0.42, 0, 1, true));
	}
	
	public CustomJumpHeight() {
		super("CustomJumpHeight", 0, Category.TRAFFIC);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		float height = (float) Zamorozka.settingsManager.getSettingByName("JumpHeight").getValDouble();
	}
}