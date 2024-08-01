package wtf.diablo.client.module.impl.player;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import wtf.diablo.client.event.api.EventTypeEnum;
import wtf.diablo.client.event.impl.player.motion.MotionEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;

@ModuleMetaData(
        name = "Fast Place",
        description = "Allows for faster block placement",
        category = ModuleCategoryEnum.PLAYER
)
public final class FastPlaceModule extends AbstractModule {
    @EventHandler
    private final Listener<MotionEvent> motionEventListener = event -> {
        if (event.getEventType() != EventTypeEnum.PRE)
            return;

        mc.rightClickDelayTimer = 0;
    };
}
