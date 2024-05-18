package us.loki.legit.modules.impl.GUI;

import org.lwjgl.input.Keyboard;

import us.loki.legit.modules.Category;
import us.loki.legit.modules.Module;

public class HUD extends Module {

	public HUD() {
		super("HUD", "HUD", Keyboard.KEY_P, Category.GUI);
	}

}
