package lunadevs.luna.module.movement;

import org.lwjgl.input.Keyboard;

import lunadevs.luna.category.Category;
import lunadevs.luna.module.Module;
import net.minecraft.client.Minecraft;

//Coded By Faith

public class VanillaSpeed extends Module {

	public VanillaSpeed() {
		super("VanillaSpeed", Keyboard.KEY_NONE, Category.MOVEMENT, false);

	}
	
	@Override
	public void onUpdate() {
		if(!this.isEnabled) return;
		
		//Did this to make it easier for myself.
		
		vanillaspeed();
	}
	
	public void vanillaspeed() {
		if ((Minecraft.getMinecraft().thePlayer.isSneaking())
				|| ((Minecraft.getMinecraft().thePlayer.moveForward == 0.0F)
						&& (Minecraft.getMinecraft().thePlayer.moveStrafing == 0.0F))) {
			return;
		}
		if ((Minecraft.getMinecraft().thePlayer.moveForward > 0.0F)
				&& (!Minecraft.getMinecraft().thePlayer.isCollidedHorizontally)) {
			Minecraft.getMinecraft().thePlayer.setSprinting(true);
		}
		if (Minecraft.getMinecraft().thePlayer.onGround) {
			Minecraft.getMinecraft().thePlayer.motionY += 0.1D;
			Minecraft.getMinecraft().thePlayer.motionX *= 1.8D;
			Minecraft.getMinecraft().thePlayer.motionZ *= 1.8D;
			double currentSpeed = Math.sqrt(Math.pow(Minecraft.getMinecraft().thePlayer.motionX, 2.0D)
					+ Math.pow(Minecraft.getMinecraft().thePlayer.motionZ, 2.0D));

			double maxSpeed = 0.6600000262260437D;
			if (currentSpeed > maxSpeed) {
				Minecraft.getMinecraft().thePlayer.motionX = (Minecraft.getMinecraft().thePlayer.motionX / currentSpeed
						* maxSpeed);
				Minecraft.getMinecraft().thePlayer.motionZ = (Minecraft.getMinecraft().thePlayer.motionZ / currentSpeed
						* maxSpeed);

			}
		}
	}
	
	@Override
	public String getValue() {
		return null;
	}
}
