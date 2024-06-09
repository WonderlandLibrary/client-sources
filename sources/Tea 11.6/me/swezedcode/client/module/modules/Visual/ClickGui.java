package me.swezedcode.client.module.modules.Visual;

import org.lwjgl.input.Keyboard;

import me.swezedcode.client.manager.managers.ModuleManager;
import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;

public class ClickGui extends Module {

	public ClickGui() {
		super("ClickGui", Keyboard.KEY_RSHIFT, 0xFF0FFFEF, ModCategory.Visual);
	}
	
	@Override
	public void onEnable() {
		mc.displayGuiScreen(ModuleManager.clickGui);
		this.setToggled(false);
	}

}
