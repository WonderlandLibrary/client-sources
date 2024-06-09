package chaos.modules.impl.GUI;

import org.lwjgl.input.Keyboard;

import chaos.modules.Category;
import chaos.modules.Module;

public class HUD extends Module {

	public HUD() {
		super( "HUD", "HUD", Keyboard.KEY_P, Category.GUI);
	}

}
