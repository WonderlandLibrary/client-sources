package xyz.vergoclient.modules.impl.movement;

import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.Vergo;
import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventUpdate;
import xyz.vergoclient.modules.Module;

public class Sprint extends Module implements OnEventInterface {

	public Sprint() {
		super("Sprint", Category.MOVEMENT);
	}

	@Override
	public void onEvent(Event e) {
		if ((e instanceof EventUpdate) && !mc.thePlayer.isSprinting() && mc.thePlayer.moveForward > 0 && !mc.thePlayer.isCollidedHorizontally
				&& (Vergo.config.modScaffold.isDisabled()))
			if (Vergo.config.modNoSlow.isEnabled() || !mc.thePlayer.isUsingItem())
				mc.thePlayer.setSprinting(true);
	}
	
}
