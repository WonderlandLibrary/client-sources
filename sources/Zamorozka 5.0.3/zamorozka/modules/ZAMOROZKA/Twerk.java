package zamorozka.modules.ZAMOROZKA;

import de.Hero.settings.Setting;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class Twerk extends Module {

	private int timer = 0;
	private int interval = 0;
	
	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("TwerkDelay", this, 5, 1, 10, true));
		Zamorozka.settingsManager.rSetting(new Setting("TwerkTick", this, 5, 1, 40, true));
	}
	
	public Twerk() {
		super("Twerk", 0, Category.Zamorozka);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		timer++;
		interval++;
		if(timer < 10 - (float)Zamorozka.settingsManager.getSettingByName("TwerkDelay").getValDouble())
			return;
		if(interval>=(float)Zamorozka.settingsManager.getSettingByName("TwerkTick").getValDouble()) {
			interval = 0;
			mc.gameSettings.keyBindSneak.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
		}else {
			mc.gameSettings.keyBindSneak.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
		}	
		timer = -1;
	}
}