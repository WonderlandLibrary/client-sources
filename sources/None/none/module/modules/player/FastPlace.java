package none.module.modules.player;

import org.lwjgl.input.Keyboard;

import none.event.Event;
import none.module.Category;
import none.module.Module;
import none.valuesystem.NumberValue;

public class FastPlace extends Module{

	public static NumberValue<Integer> speed = new NumberValue<>("Speed", 0, 0, 4);
	
	public FastPlace() {
		super("FastPlace", "FastPlace", Category.PLAYER, Keyboard.KEY_NONE);
	}

	@Override
	public void onEvent(Event event) {
		if (!isEnabled()) return;
	}

}
