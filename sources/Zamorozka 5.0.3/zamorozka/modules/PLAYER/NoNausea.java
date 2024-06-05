package zamorozka.modules.PLAYER;

import org.lwjgl.input.Keyboard;

import zamorozka.module.Category;
import zamorozka.module.Module;

public class NoNausea extends Module {

	public NoNausea() {
		super("NoNausea", Keyboard.KEY_NONE, Category.POTION);
	}
}