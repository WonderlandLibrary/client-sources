package lunadevs.luna.module.movement;

import org.lwjgl.input.Keyboard;

import com.zCore.Core.zCore;

import lunadevs.luna.category.Category;
import lunadevs.luna.module.Module;

/**
 * 
 * @author Timothy
 *
 */
public class AntiPlate extends Module {

	private boolean active;

	/** private int airTicks; Removed due to messing the ""glide"" up */

	public AntiPlate() {
		super("AntiPlate", Keyboard.KEY_NONE, Category.MOVEMENT, true);
	}

	@Override
	public void onUpdate() {
		if (!this.isEnabled)
			return;
		if (zCore.p().isMoving()) {
			if (zCore.p().onGround) {
				zCore.p().setSpeed(0.0D);
			} else {
				zCore.p().setSpeed(zCore.p().getSpeed());
				zCore.p().setSpeed(zCore.p().getSpeed() < 0.062D ? 0.062D : zCore.p().getSpeed());
				zCore.p().setPosition(zCore.p().posX, zCore.p().posY - 1.0E-14D, zCore.p().posZ);
				zCore.p().motionY = -0.0D;
			}
			super.onUpdate();
		}
	}

	@Override
	public void onEnable() {
		active = true;
	}

	@Override
	public void onDisable() {
		mc.timer.timerSpeed = 1.0f;
		active = false;
		super.onDisable();
	}

	@Override
	public String getValue() {
		return "NCP";
	}

}
