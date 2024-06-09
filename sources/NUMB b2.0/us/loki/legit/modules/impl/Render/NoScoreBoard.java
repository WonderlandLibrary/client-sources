package us.loki.legit.modules.impl.Render;

import org.lwjgl.input.Keyboard;

import us.loki.legit.modules.*;

public class NoScoreBoard extends Module {

	public static boolean enabled;

	public NoScoreBoard() {
		super("NoScoreBoard", "NoScoreBoard", Keyboard.KEY_NONE, Category.MODS);
	}

	@Override
	public void onEnable() {
		enabled = true;
	}

	@Override
	public void onDisable() {
		enabled = false;
	}

}