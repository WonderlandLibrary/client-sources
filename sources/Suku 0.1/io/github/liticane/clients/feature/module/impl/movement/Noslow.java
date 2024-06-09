package io.github.liticane.clients.feature.module.impl.movement;

import io.github.liticane.clients.feature.event.api.EventListener;
import io.github.liticane.clients.feature.event.api.annotations.SubscribeEvent;
import io.github.liticane.clients.feature.event.impl.motion.NoSlowDownEvent;
import io.github.liticane.clients.feature.event.impl.motion.PreMotionEvent;
import io.github.liticane.clients.feature.event.impl.other.PacketEvent;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.feature.property.impl.StringProperty;

@Module.Info(name = "NoSlow", category = Module.Category.MOVEMENT)
public class Noslow extends Module {
    public StringProperty mode = new StringProperty("Mode", this, "Vanilla", "Vanilla", "UNPC");
    @SubscribeEvent
    private final EventListener<PreMotionEvent> preMotionEventEventListener = e -> {
        setSuffix(mode.getMode());
        switch (mode.getMode()) {
            case "UNCP":
                //soon tm
                break;
        }
    };
    @SubscribeEvent
    private final EventListener<NoSlowDownEvent> onNoslow = e -> { e.setCancelled(true);};

}
