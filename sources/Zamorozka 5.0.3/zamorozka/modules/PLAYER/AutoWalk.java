package zamorozka.modules.PLAYER;

import org.lwjgl.input.Keyboard;

import zamorozka.event.EventTarget;
import zamorozka.event.events.EventUpdate;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class AutoWalk extends Module {

	public AutoWalk() {
		super("AutoWalk", Keyboard.KEY_NONE, Category.PLAYER);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		mc.gameSettings.keyBindForward.pressed = true;
	}
	
	@Override
	public void onDisable() {
		if (!mc.gameSettings.isKeyDown(mc.gameSettings.keyBindForward))
            mc.gameSettings.keyBindForward.pressed = false;
		super.onDisable();
	}
	
}