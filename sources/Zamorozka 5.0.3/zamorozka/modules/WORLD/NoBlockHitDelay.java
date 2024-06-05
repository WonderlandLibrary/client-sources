package zamorozka.modules.WORLD;

import java.util.ArrayList;

import de.Hero.settings.Setting;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class NoBlockHitDelay extends Module {

	public NoBlockHitDelay() {
		super("NoBlockHitDelay", 0, Category.WORLD);
	}	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		mc.playerController.blockHitDelay = 0;
	}
}