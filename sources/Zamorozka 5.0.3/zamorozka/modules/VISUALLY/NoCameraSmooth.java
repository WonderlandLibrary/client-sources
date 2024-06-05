package zamorozka.modules.VISUALLY;

import org.lwjgl.input.Keyboard;

import zamorozka.event.EventTarget;
import zamorozka.event.events.EventUpdate;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class NoCameraSmooth extends Module {

	public NoCameraSmooth() {
		super("NoCameraSmooth", Keyboard.KEY_NONE, Category.VISUALLY);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		mc.gameSettings.smoothCamera = false;
	}
}