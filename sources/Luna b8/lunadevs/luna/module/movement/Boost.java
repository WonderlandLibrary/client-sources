package lunadevs.luna.module.movement;

import org.lwjgl.input.Keyboard;

import lunadevs.luna.category.Category;
import lunadevs.luna.module.Module;
import net.minecraft.client.Minecraft;

public class Boost extends Module {

	private int counter = 0;
	public static Minecraft mc = Minecraft.getMinecraft();

	public Boost() {
		super("Boost", Keyboard.KEY_NONE, Category.MOVEMENT, false);
	}

	@Override
	public void onUpdate() {
		if (!this.isEnabled)
			return;
		if (Minecraft.thePlayer.onGround) {
			if (mc.gameSettings.keyBindJump.pressed) {
				net.minecraft.util.Timer.timerSpeed = 1.0F;
			}
		}
		Minecraft.getMinecraft();
		if (!Minecraft.thePlayer.isSneaking()) {
			Minecraft.getMinecraft();
			if (Minecraft.thePlayer.moveForward == 9.0F) {
				Minecraft.getMinecraft();
			}
		} else {
			return;
		}
		Minecraft.getMinecraft();
		if (Minecraft.thePlayer.onGround) {
			this.counter += 1;
			if (this.counter == 1) {
				net.minecraft.util.Timer.timerSpeed = 1.4F;
				Minecraft.thePlayer.motionX *= 1.5D;
				Minecraft.thePlayer.motionY *= 1.585825882D;
				Minecraft.thePlayer.motionZ *= 1.5D;
			}
			if (this.counter == 2) {
				net.minecraft.util.Timer.timerSpeed = 1.4987514F;
				Minecraft.thePlayer.motionX *= 1.5500000476837159D;
				Minecraft.thePlayer.motionY *= 1.585825882D;
				Minecraft.thePlayer.motionZ *= 1.5500000476837159D;
			}
			if (this.counter == 3) {
				net.minecraft.util.Timer.timerSpeed = 1.2F;
				Minecraft.thePlayer.motionX *= 1.0D;
				Minecraft.thePlayer.motionY *= 1.585825882D;
				Minecraft.thePlayer.motionZ *= 1.0D;
			}
			if (this.counter == 4) {
				net.minecraft.util.Timer.timerSpeed = 1.2503987F;
				Minecraft.thePlayer.motionX *= 1.258526265256D;
				Minecraft.thePlayer.motionY *= 1.585825882D;
				Minecraft.thePlayer.motionZ *= 1.258526265256D;
			}
			if (this.counter == 4) {
				net.minecraft.util.Timer.timerSpeed = 1.3667514F;
				Minecraft.thePlayer.motionX *= 1.2500000476837159D;
				Minecraft.thePlayer.motionY *= 1.585825882D;
				Minecraft.thePlayer.motionZ *= 1.2500000476837159D;
				this.counter = 0;
			}
		}
	}

	@Override
	public void onDisable() {
		net.minecraft.util.Timer.timerSpeed = 1.0F;
		super.onDisable();
	}

	@Override
	public void onEnable() {
		super.onEnable();
	}

	@Override
	public String getValue() {
		return null;
	}

}
