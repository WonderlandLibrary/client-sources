package zamorozka.modules.WORLD;

import org.lwjgl.input.Keyboard;

import de.Hero.settings.Setting;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.Timer2;
import zamorozka.ui.TimerHelper;

public class Spider extends Module {

	Timer2 timer = new Timer2();

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("SpiderBoost", this, 1.4, 0.1, 5, true));
	}

	public Spider() {
		super("Spider", Keyboard.KEY_NONE, Category.TRAFFIC);
	}

	@EventTarget
	public void onUpdate(EventPreMotionUpdates event) {
		if (!getState())
			return;
		if (mc.player.isMoving() && mc.player.isCollidedHorizontally && timer.check(500.0F)) {
			this.mc.player.jump();
			mc.player.motionY *= Zamorozka.settingsManager.getSettingByName("SpiderBoost").getValDouble();
			timer.reset();
		}
	}
}