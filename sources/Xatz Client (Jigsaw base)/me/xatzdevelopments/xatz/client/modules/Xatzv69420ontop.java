package me.xatzdevelopments.xatz.client.modules;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.xatz.client.main.Xatz;
import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.module.Module;
import me.xatzdevelopments.xatz.utils.Wrapper;

public class Xatzv69420ontop extends Module {

	public Xatzv69420ontop() {
		super("Xatzv69420ontop", Keyboard.KEY_NONE, Category.HIDDEN, "Sex.");
	}

	@Override
	public void onDisable() {

		super.onDisable();
	}

	@Override
	public void onEnable() {

		super.onEnable();
	}

	@Override
	public void onUpdate() {
		Wrapper.tellPlayer("Lmao i guess you activated this module, congrats..");
		Xatz.getModuleByName("Xatzv69420ontop").toggle();

		super.onUpdate();
	}

}