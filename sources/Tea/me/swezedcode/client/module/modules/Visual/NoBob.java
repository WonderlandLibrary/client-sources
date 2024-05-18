package me.swezedcode.client.module.modules.Visual;

import org.lwjgl.input.Keyboard;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.module.modules.Options.MemeNames;

public class NoBob extends Module {

	public NoBob() {
		super("NoBob", Keyboard.KEY_NONE, 0xFFFC804E, ModCategory.Visual);
		setDisplayName("No Bob");
	}
	
}