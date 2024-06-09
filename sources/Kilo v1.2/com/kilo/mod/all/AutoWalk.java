package com.kilo.mod.all;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.kilo.mod.Category;
import com.kilo.mod.Module;

public class AutoWalk extends Module {
	
	public AutoWalk(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
	}
	
	public void onDisable() {
		super.onDisable();
		if (mc.gameSettings.keyBindForward.getKeyCode() < 0) {
			KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), Mouse.isButtonDown(mc.gameSettings.keyBindForward.getKeyCode()+100));
		} else {
			KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()));
		}
	}
	
	public void onPlayerPreUpdate() {
		KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
	}
}
