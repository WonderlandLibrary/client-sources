package zamorozka.modules.PLAYER;

import org.lwjgl.input.Keyboard;

import de.Hero.settings.Setting;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class AntiCollision extends Module {

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("By Water", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("By Players", this, true));
	}
	
	public AntiCollision() {
		super("AntiCollision", Keyboard.KEY_NONE, Category.PLAYER);
	}

}
