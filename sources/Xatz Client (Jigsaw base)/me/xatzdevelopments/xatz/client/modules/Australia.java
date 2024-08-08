package me.xatzdevelopments.xatz.client.modules;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.module.Module;

public class Australia extends Module {

	public Australia() {
		super("Australia", Keyboard.KEY_NONE, Category.RENDER, "The land of the upside down!");
	}

	@Override
	public void onDisable() {
        mc.gameSettings.invertMouse = false;
		super.onDisable();
	}

	@Override
	public void onEnable() {

		super.onEnable();
	}

	@Override
	public void onUpdate() {

		super.onUpdate();
	}

}
