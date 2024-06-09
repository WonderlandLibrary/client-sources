package lunadevs.luna.module.movement;

import org.lwjgl.input.Keyboard;

import lunadevs.luna.category.Category;
import lunadevs.luna.main.Luna;
import lunadevs.luna.module.Module;
import net.minecraft.client.Minecraft;

public class AACJesus extends Module{

	public AACJesus() {
		super("AACJesus", Keyboard.KEY_NONE, Category.MOVEMENT, true);
	}
	@Override
	public void onUpdate() {
		if (!this.isEnabled) return;
		if(mc.thePlayer.isInWater()){
			mc.thePlayer.motionY = 0.07f;
		}
		super.onUpdate();
	}
	@Override
	public void onDisable() {
		super.onDisable();
	}
	
	@Override
	public void onEnable() {
	}
	
	@Override
	public String getValue() {
		return "3.1.7";
	}

}
