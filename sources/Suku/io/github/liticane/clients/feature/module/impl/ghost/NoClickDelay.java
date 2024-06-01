package io.github.liticane.clients.feature.module.impl.ghost;

import io.github.liticane.clients.feature.event.api.EventListener;
import io.github.liticane.clients.feature.event.api.annotations.SubscribeEvent;
import io.github.liticane.clients.feature.event.impl.motion.PreMotionEvent;
import io.github.liticane.clients.feature.module.Module;
@Module.Info(name = "NoClickDelay", category = Module.Category.GHOST)
public class NoClickDelay extends Module {
    @SubscribeEvent
    private final EventListener<PreMotionEvent> preMotionEventEventListener = e -> {
        if (mc.player != null && mc.world != null) {
            mc.leftClickCounter = 0;
        }
    };
}
