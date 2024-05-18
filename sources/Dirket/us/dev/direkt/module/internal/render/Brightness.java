package us.dev.direkt.module.internal.render;

import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.EventGameTick;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.dvent.Listener;
import us.dev.dvent.Link;

@ModData(label = "Brightness", aliases = {"fullbright", "bright"}, category = ModCategory.RENDER)
public class Brightness extends ToggleableModule {

	private int delay;
	private float oldBrightness;

	@Listener
	protected Link<EventGameTick> onGameTick = new Link<>(event -> {
		if (this.isRunning()) {
			if (Wrapper.getGameSettings().gammaSetting < 20F)
				Wrapper.getGameSettings().gammaSetting += 0.5F;
		} else {
			if (Wrapper.getGameSettings().gammaSetting > this.oldBrightness)
				Wrapper.getGameSettings().gammaSetting -= 0.5F;
			if (Wrapper.getGameSettings().gammaSetting == this.oldBrightness)
				Direkt.getInstance().getEventManager().unregister(this);
		}
	});
	
	@Override
	public void onEnable() {
		this.oldBrightness = Wrapper.getGameSettings().gammaSetting > 1 ? 1 : Wrapper.getGameSettings().gammaSetting;
	}
	
	@Override
	public void onDisable() {
		Direkt.getInstance().getEventManager().register(this);
	}

	
}
