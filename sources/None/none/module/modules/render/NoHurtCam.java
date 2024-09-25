package none.module.modules.render;

import org.lwjgl.input.Keyboard;

import none.event.Event;
import none.module.Category;
import none.module.Module;

public class NoHurtCam extends Module{

	public NoHurtCam() {
		super("NoHurtCam", "NoHurtCam", Category.RENDER, Keyboard.KEY_NONE);
	}

	@Override
	public void onEvent(Event event) {
		
	}

}
