package zamorozka.modules.PLAYER;

import org.lwjgl.input.Keyboard;

import zamorozka.module.Category;
import zamorozka.module.Module;

public class NoJumpDelay extends Module {

	public NoJumpDelay() {
		super("NoJumpDelay", Keyboard.KEY_NONE, Category.PLAYER);
	}

}
