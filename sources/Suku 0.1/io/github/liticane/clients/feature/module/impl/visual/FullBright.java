package io.github.liticane.clients.feature.module.impl.visual;

import io.github.liticane.clients.feature.event.api.EventListener;
import io.github.liticane.clients.feature.event.api.annotations.SubscribeEvent;
import io.github.liticane.clients.feature.event.impl.motion.PreMotionEvent;
import io.github.liticane.clients.feature.module.Module;
@Module.Info(name = "FullBright", category = Module.Category.VISUAL)
public class FullBright extends Module {
    @Override
    protected void onDisable() {
        mc.settings.gammaSetting = 100;
        super.onDisable();
    }

    @SubscribeEvent
    private final EventListener<PreMotionEvent> preMotionEventEventListener = e -> {
        mc.settings.gammaSetting = 100;
    };
}
