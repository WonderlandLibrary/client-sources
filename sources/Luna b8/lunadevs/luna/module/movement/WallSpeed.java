package lunadevs.luna.module.movement;

import org.lwjgl.input.Keyboard;

import lunadevs.luna.category.Category;
import lunadevs.luna.events.Event;
import lunadevs.luna.main.Luna;
import lunadevs.luna.module.Module;
import net.minecraft.client.Minecraft;

public class WallSpeed extends Module{

	public WallSpeed() {
		super("WallSpeed", Keyboard.KEY_NONE, Category.MOVEMENT, true);
	}
	@Override
	public void onUpdate() {
		if (!this.isEnabled) return;
		if(mc.thePlayer.isCollidedHorizontally){
			mc.thePlayer.motionX *= 1.9f;
			mc.thePlayer.motionZ *= 1.9f;
//			mc.thePlayer.setPositionAndUpdate(mc.thePlayer.motionX, mc.thePlayer.motionY+0.1f, mc.thePlayer.motionZ);
//			mc.thePlayer.posX *= 1.2f;
//					mc.thePlayer.posZ *= 1.2f;
//			mc.renderGlobal.loadRenderers();
		}
		super.onUpdate();
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
	public String getValue() {
		return "AAC 3.1.6";
	}

}
