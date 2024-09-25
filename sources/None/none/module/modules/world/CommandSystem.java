package none.module.modules.world;

import org.lwjgl.input.Keyboard;

import none.event.Event;
import none.module.Category;
import none.module.Module;

public class CommandSystem extends Module{

	public CommandSystem() {
		super("DisableCommand", "DisableCommand", Category.WORLD, Keyboard.KEY_NONE);
	}
	
	@Override
	protected void onEnable() {
		super.onEnable();
	}
	
	@Override
	protected void onDisable() {
		super.onDisable();
	}
	
	@Override
	public void onEvent(Event event) {
		
	}

}
