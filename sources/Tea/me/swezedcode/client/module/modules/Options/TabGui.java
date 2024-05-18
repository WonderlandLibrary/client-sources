package me.swezedcode.client.module.modules.Options;

import org.lwjgl.input.Keyboard;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;

public class TabGui extends Module {

	public TabGui() {
		super("TabGui", Keyboard.KEY_0, -1, ModCategory.Options);
	}
	
}