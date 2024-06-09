package us.loki.legit.modules.impl.Movement;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import us.loki.legit.events.EventUpdate;
import us.loki.legit.modules.*;
import us.loki.legit.utils.TimeHelper;

public class Sprint extends Module {

	public Sprint() {
		super("Sprint", "Sprint", Keyboard.KEY_O, Category.MOVEMENT);
	}

	TimeHelper time1 = new TimeHelper();

	@EventTarget
	public void onUpdate(EventUpdate e) {
		if ((mc.thePlayer != null) && (mc.thePlayer.getFoodStats().getFoodLevel() > 6)
				&& (!mc.gameSettings.keyBindSneak.pressed)
				&& ((mc.gameSettings.keyBindForward.pressed) || (mc.gameSettings.keyBindLeft.pressed)
						|| (mc.gameSettings.keyBindRight.pressed) || (mc.gameSettings.keyBindBack.pressed))) {
			mc.thePlayer.setSprinting(true);
		}

	}

	@Override
	public void onDisable() {
		EventManager.unregister(this);
		mc.thePlayer.setSprinting(false);
	}

	@Override
	public void onEnable() {
		EventManager.register(this);
		if (ModuleManager.getModuleByName("Sneak").isEnabled()) {
			toggle();
		}
	}

}