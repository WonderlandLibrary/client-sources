package me.swezedcode.client.module.modules.Visual;

import org.lwjgl.input.Keyboard;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.module.modules.Options.MemeNames;

public class NoScoreBoard extends Module {

	public static boolean enabled;
	
	public NoScoreBoard() {
		super("NoScoreBoard", Keyboard.KEY_NONE, 0xFFF533A4, ModCategory.Visual);
		if(!MemeNames.enabled) {
			setDisplayName("NoScoreBoard");
		}else{
			setDisplayName("RemovesADisturbingBoxToTheLeft");
		}
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
