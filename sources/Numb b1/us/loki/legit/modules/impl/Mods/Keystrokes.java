package us.loki.legit.modules.impl.Mods;

import org.lwjgl.input.Keyboard;

import us.loki.legit.modules.*;

public class Keystrokes extends Module {

	public Keystrokes() {
		super("Keystrokes", "Keystrokes", Keyboard.KEY_NONE, Category.MODS);
	}

}
