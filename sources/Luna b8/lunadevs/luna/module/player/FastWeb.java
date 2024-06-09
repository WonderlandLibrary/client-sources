package lunadevs.luna.module.player;

import org.lwjgl.input.Keyboard;

import lunadevs.luna.category.Category;
import lunadevs.luna.module.Module;
import lunadevs.luna.utils.TimeHelper;

public class FastWeb extends Module {

	int delay = 0;
	private double start;
	private int d;
	private TimeHelper time = new TimeHelper();

	public FastWeb() {
		super("FastFall", Keyboard.KEY_NONE, Category.PLAYER, true);
	}

	@Override
	public void onUpdate() {
		if (!this.isEnabled)
			return;
		 if ((mc.thePlayer.fallDistance > 3.0F) && (!mc.thePlayer.onGround)) {
			mc.thePlayer.motionY = -4.17F;
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
		return "NCP";
	}

}
