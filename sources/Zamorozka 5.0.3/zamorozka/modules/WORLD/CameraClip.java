package zamorozka.modules.WORLD;

import org.lwjgl.input.Keyboard;

import zamorozka.module.Category;
import zamorozka.module.Module;

public class CameraClip extends Module {

	public CameraClip() {
		super("CameraClip", Keyboard.KEY_NONE, Category.WORLD);
	}

}
