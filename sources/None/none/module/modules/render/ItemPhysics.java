package none.module.modules.render;

import org.lwjgl.input.Keyboard;

import none.event.Event;
import none.module.Category;
import none.module.Module;

public class ItemPhysics extends Module{

	public ItemPhysics() {
		super("ItemPhysics", "ItemPhysics", Category.RENDER, Keyboard.KEY_NONE);
	}

	@Override
	public void onEvent(Event event) {
		
	}

}
