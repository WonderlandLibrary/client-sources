package zamorozka.modules.PLAYER;

import org.lwjgl.input.Keyboard;

import zamorozka.event.EventTarget;
import zamorozka.event.events.EventUpdate;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class PerfectHorseJump extends Module {

	public PerfectHorseJump() {
		super("PerfectHorseJump", Keyboard.KEY_NONE, Category.PLAYER);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		mc.player.horseJumpPowerCounter = 9;
		mc.player.horseJumpPower = 1f;
	}

}