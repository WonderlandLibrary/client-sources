package lunadevs.luna.module.movement;

import org.lwjgl.input.Keyboard;

import com.zCore.Core.zCore;

import lunadevs.luna.category.Category;
import lunadevs.luna.events.Event;
import lunadevs.luna.main.Luna;
import lunadevs.luna.module.Module;
import net.minecraft.client.Minecraft;

public class Sprint extends Module{

	public Sprint() {
		super("Sprint", Keyboard.KEY_NONE, Category.MOVEMENT, true);
	}
	@Override
	public void onUpdate() {
		if (!this.isEnabled) return;
		if(zCore.p().isMoving()){
			zCore.p().setSprinting(true);
		}
		super.onUpdate();
	}

	@Override
	public void onDisable() {
		mc.gameSettings.keyBindSprint.pressed = false;
		super.onDisable();
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
	}
	
	@Override
	public String getValue() {
		return "Omni";
	}

}
