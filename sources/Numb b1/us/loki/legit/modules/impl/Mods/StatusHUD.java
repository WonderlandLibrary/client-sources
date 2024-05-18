package us.loki.legit.modules.impl.Mods;

import org.lwjgl.input.Keyboard;

import us.loki.legit.modules.*;

public class StatusHUD extends Module {

	public StatusHUD() {
		super("StatusHUD", "StatusHUD", Keyboard.KEY_NONE, Category.MODS);
	}

}