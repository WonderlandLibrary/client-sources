package lunadevs.luna.module.movement;

import org.lwjgl.input.Keyboard;

import lunadevs.luna.category.Category;
import lunadevs.luna.events.EventUpdate;
import lunadevs.luna.module.Module;
import lunadevs.luna.option.Option;
import lunadevs.luna.utils.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.Timer;

public class Frames extends Module {

	// Coded By Faith

	public Frames() {
		super("Frames", Keyboard.KEY_NONE, Category.MOVEMENT, false);
	}

	private TimerUtil framesDelay = new TimerUtil();
	boolean hop;
	boolean move;
	private int motionDelay;
	private int motionTicks;
	private double prevY;

	public void onUpdate() {
		if (!this.isEnabled) return;
			dothatjumpfam(4.25D);
	}

	private void dothatjumpfam(double speed) {
		if ((Minecraft.thePlayer.movementInput.moveForward > 0.0F)
				|| (Minecraft.thePlayer.movementInput.moveStrafe > 0.0F)) {
			if (Minecraft.thePlayer.onGround) {
				this.prevY = Minecraft.thePlayer.posY;
				this.hop = true;
				Minecraft.thePlayer.jump();
				if (this.motionTicks == 1) {
					this.framesDelay.reset();
					if (this.hop) {
						EntityPlayerSP thethePlayer = Minecraft.thePlayer;
						thethePlayer.motionX /= speed * 2.2D;
						EntityPlayerSP thethePlayer2 = Minecraft.thePlayer;
						thethePlayer2.motionZ /= speed * 2.2D;
						this.move = false;
					}
					this.motionTicks = 0;
				} else {
					this.motionTicks = 1;
				}
			} else if ((!this.move) && (this.motionTicks == 1) && (this.framesDelay.isDelayComplete(450L))) {
				EntityPlayerSP thethePlayer3 = Minecraft.thePlayer;
				thethePlayer3.motionX *= speed;
				EntityPlayerSP thethePlayer4 = Minecraft.thePlayer;
				thethePlayer4.motionZ *= speed;
				this.move = true;
			}
		}
	}

	public void onDisable() {
		Timer timer = Minecraft.getMinecraft().timer;
		Timer.timerSpeed = 1.0F;
	}

	@Override
	public String getValue() {
		return null;
	}

}
