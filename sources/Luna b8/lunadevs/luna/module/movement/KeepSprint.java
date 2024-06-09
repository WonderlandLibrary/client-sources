package lunadevs.luna.module.movement;

import org.lwjgl.input.Keyboard;

import com.zCore.Core.zCore;

import lunadevs.luna.category.Category;
import lunadevs.luna.module.Module;

public class KeepSprint extends Module{

	private boolean active;

	public KeepSprint() {
		super("KeepSprint", Keyboard.KEY_NONE, Category.MOVEMENT, false /** no modes XD**/);
	}

	@Override
	public void onUpdate() {
		if (!this.isEnabled) return;
			super.onUpdate();
	}

	
	@Override
	public void onEnable() {
		
		super.onDisable();
	}
	@Override
	public void onDisable() {
		super.onDisable();
	}

	@Override
	public String getValue() {
		return null; //doesnt have any modes lmao its keepsprint
		
	}

}

	
