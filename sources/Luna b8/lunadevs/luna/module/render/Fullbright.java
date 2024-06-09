package lunadevs.luna.module.render;

import org.lwjgl.input.Keyboard;

import lunadevs.luna.category.Category;
import lunadevs.luna.main.Luna;
import lunadevs.luna.module.Module;

public class Fullbright extends Module{
	
	public Fullbright() {
		super("Fullbright", Keyboard.KEY_P, Category.RENDER, true);
	}

	@Override
	public void onUpdate() {
		if (this.isEnabled){
			mc.gameSettings.gammaSetting++;
		}
		super.onUpdate();
	}
	
	
	@Override
	public void onEnable() {
	}
	
	@Override
	public void onDisable() {
		mc.gameSettings.gammaSetting = 0.1F;
		super.onDisable();
	}
	
	@Override
	public String getValue() {
		return "Fade";
	}

}
