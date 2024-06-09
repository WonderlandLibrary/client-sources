package io.github.liticane.clients.feature.module.impl.visual;

import io.github.liticane.clients.feature.event.api.EventListener;
import io.github.liticane.clients.feature.event.api.annotations.SubscribeEvent;
import io.github.liticane.clients.feature.event.impl.motion.PreMotionEvent;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.feature.property.impl.NumberProperty;
import io.github.liticane.clients.feature.property.impl.StringProperty;

@Module.Info(name = "Animations", category = Module.Category.VISUAL)
public class Animations extends Module {
    public StringProperty animations = new StringProperty("Animation Mode",this,"Old","Old","Old 2","Exhibition","Xiv","Spin");
    public NumberProperty swingSpeed = new NumberProperty("Swing Speed", this, 4, 1, 25, 1);
    @SubscribeEvent
    private final EventListener<PreMotionEvent> preMotionEventEventListener = e -> {
        setSuffix(animations.getMode());
    };
}
