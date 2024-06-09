package us.dev.direkt.module.internal.world;

import us.dev.api.timing.Timer;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.player.update.EventPreMotionUpdate;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.dvent.Listener;
import us.dev.dvent.Link;

@ModData(label = "Fast Place", category = ModCategory.WORLD)
public class FastPlace extends ToggleableModule {
	private Timer timer = new Timer();

	@Listener
	protected Link<EventPreMotionUpdate> onPreMotionUpdate = new Link<>(event -> {
		if(timer.hasReach(56)) {
			Wrapper.getMinecraft().setRightClickDelayTimer(0);
			timer.reset();
		}
	});
	
}