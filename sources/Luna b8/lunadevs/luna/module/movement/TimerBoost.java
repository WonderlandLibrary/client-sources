package lunadevs.luna.module.movement;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;
import com.zCoreEvent.Event;

import lunadevs.luna.category.Category;
import lunadevs.luna.events.EventMotion;
import lunadevs.luna.module.Module;
import lunadevs.luna.option.Option;
import net.minecraft.util.Timer;

public class TimerBoost extends Module{

	@Option.Op(min=1.0D, max=20.0D, increment=0.1D, name="Speed")
	public static double speed = 5;

	public TimerBoost() {
		super("Timer", Keyboard.KEY_K, Category.MOVEMENT, false);
	}
	@EventTarget
	public void onUpdate() {
		if (!this.isEnabled) return;
		Timer.timerSpeed = (float) speed;
		super.onUpdate();
	}

	@Override
	public void onDisable() {
		Timer.timerSpeed = 1.0F;
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
