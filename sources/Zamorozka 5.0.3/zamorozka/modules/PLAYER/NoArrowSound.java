package zamorozka.modules.PLAYER;

import org.lwjgl.input.Keyboard;

import zamorozka.module.Category;
import zamorozka.module.Module;

public class NoArrowSound extends Module {

	public NoArrowSound() {
		super("No Arrow Sound", Keyboard.KEY_NONE, Category.PLAYER);
	}

}
