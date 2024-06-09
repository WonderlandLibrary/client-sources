package us.dev.direkt.module.internal.movement;

import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.player.update.EventPreMotionUpdate;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.module.internal.movement.speed.Speed;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

@ModData(label = "Timer", category = ModCategory.MOVEMENT)
public class Timer extends ToggleableModule {

	private boolean speedTick;

	@Listener
	protected Link<EventPreMotionUpdate> onPreMotionUpdate = new Link<>(event -> {
		if (!Direkt.getInstance().getModuleManager().getModule(Speed.class).isRunning()) {
			speedTick = !speedTick;
			Wrapper.getMinecraft().getTimer().timerSpeed = speedTick ? 1F : 1.1F;
		}
	});
	
	@Override
	public void onDisable() {
		Wrapper.getMinecraft().getTimer().timerSpeed = 1.0F;
	}
	
}
