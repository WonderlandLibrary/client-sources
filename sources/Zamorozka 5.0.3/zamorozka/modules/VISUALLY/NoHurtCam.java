package zamorozka.modules.VISUALLY;

import org.lwjgl.input.Keyboard;

import de.Hero.settings.Setting;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class NoHurtCam extends Module{

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("HurtAngle", this, 0, -24.0, 24.0, true));
	}
	
	public NoHurtCam() {
		super("HurtCam", Keyboard.KEY_NONE, Category.VISUALLY);
	}

}