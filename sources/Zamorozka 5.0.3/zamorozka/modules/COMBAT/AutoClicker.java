package zamorozka.modules.COMBAT;

import org.lwjgl.input.Mouse;

import de.Hero.settings.Setting;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.MathUtils;

public class AutoClicker extends Module {

	public AutoClicker() {
		super("AutoClicker", 0, Category.COMBAT);
	}
	
	@EventTarget
	public void onUpdate(EventPreMotionUpdates event) {
        if (mc.player.getCooledAttackStrength(0) == 1.0f && mc.gameSettings.keyBindAttack.pressed) {
            mc.clickMouse();
        }
	}
}