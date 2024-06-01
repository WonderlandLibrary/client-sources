package io.github.liticane.clients.feature.module.impl.player;

import io.github.liticane.clients.feature.event.api.EventListener;
import io.github.liticane.clients.feature.event.api.annotations.SubscribeEvent;
import io.github.liticane.clients.feature.event.impl.motion.PreMotionEvent;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.feature.property.impl.NumberProperty;

@Module.Info(name = "FastPlace", category = Module.Category.PLAYER)
public class FastPlace extends Module {
    public NumberProperty delay = new NumberProperty("Right Click Delay", this, 2, 0, 4, 1);

    @SubscribeEvent
    private final EventListener<PreMotionEvent> preMotionEventEventListener = e -> {
        mc.rightClickDelayTimer = (int)delay.getValue();
    };

}
