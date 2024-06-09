package lunadevs.luna.module.movement;

import org.lwjgl.input.Keyboard;

import lunadevs.luna.category.Category;
import lunadevs.luna.main.Luna;
import lunadevs.luna.module.Module;
import net.minecraft.client.Minecraft;

public class AACGlide extends Module{

	public AACGlide() {
		super("AACGlide", Keyboard.KEY_NONE, Category.MOVEMENT, false);
	}
	@Override
	public void onUpdate() {
		if (!this.isEnabled) return;
	if(mc.gameSettings.keyBindJump.pressed==true){
		mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.01f, mc.thePlayer.posZ);
	}
	mc.thePlayer.motionY = -0.1f;
	{
		if(mc.gameSettings.keyBindSneak.pressed==true){
			mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.5f, mc.thePlayer.posZ);
		}
		super.onUpdate();
	}
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
		return null;
	}

}
