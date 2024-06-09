package io.github.liticane.clients.feature.module.impl.visual;

import io.github.liticane.clients.feature.event.api.EventListener;
import io.github.liticane.clients.feature.event.api.annotations.SubscribeEvent;
import io.github.liticane.clients.feature.event.impl.motion.PreMotionEvent;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.feature.property.impl.NumberProperty;

@Module.Info(name = "Partical Timer Test", category = Module.Category.VISUAL)
public class ParticleTimer extends Module {
    public NumberProperty timer = new NumberProperty("Particle Timer", this, 2, 0, 4, 1);

    @SubscribeEvent
    private final EventListener<PreMotionEvent> preMotionEventEventListener = e -> {
    };

}
