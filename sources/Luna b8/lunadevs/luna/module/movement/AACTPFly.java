package lunadevs.luna.module.movement;

import org.lwjgl.input.Keyboard;

import lunadevs.luna.category.Category;
import lunadevs.luna.main.Luna;
import lunadevs.luna.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Timer;

public class AACTPFly extends Module{

	public AACTPFly() {
		super("AACTPFly", Keyboard.KEY_NONE, Category.MOVEMENT, false);
	}
	@Override
	public void onUpdate() {
		if (!this.isEnabled) return;
	if(z.gsettings().keyBindAttack.pressed){
		Minecraft.thePlayer.capabilities.isFlying = true;
		double yaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
		float increment = +8.5F;
        mc.thePlayer.setPosition(
        mc.thePlayer.posX + 
        Math.sin(Math.toRadians(-yaw)) * increment, 
        mc.thePlayer.posY, 
        mc.thePlayer.posZ + 
        Math.cos(Math.toRadians(-yaw)) * increment);
	}else if (z.gsettings().keyBindAttack.pressed==false){
	}
		super.onUpdate();
	}

	@Override
	public void onDisable() {
		Minecraft.thePlayer.capabilities.isFlying = false;
		super.onDisable();
	}
	
	@Override
	public void onEnable() {
		z.addChatMessageP("Press your ATTACK Mouse Button to Teleport forward.");
		super.onEnable();
	}
	
	@Override
	public String getValue() {
		return null;
	}

}
