package us.loki.legit.modules.impl.Movement;

import org.lwjgl.input.Keyboard;

import us.loki.legit.modules.*;

public class Sneak extends Module {

	public Sneak() {
		super("Sneak", "Sneak", Keyboard.KEY_NONE, Category.MOVEMENT);
	}

	public void onEnable() {
		mc.gameSettings.keyBindSneak.pressed = true;
		if (ModuleManager.getModuleByName("Sprint").isEnabled()) {
			toggle();
		}
	}

	public void onDisable() {
		mc.gameSettings.keyBindSneak.pressed = false;
	}
}