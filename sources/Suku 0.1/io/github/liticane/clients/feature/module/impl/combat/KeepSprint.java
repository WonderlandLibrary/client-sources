package io.github.liticane.clients.feature.module.impl.combat;

import io.github.liticane.clients.feature.event.api.EventListener;
import io.github.liticane.clients.feature.event.api.annotations.SubscribeEvent;
import io.github.liticane.clients.feature.event.impl.other.PacketEvent;
import io.github.liticane.clients.feature.event.impl.render.KeepSprintEvent;
import io.github.liticane.clients.feature.module.Module;

import java.lang.module.ModuleDescriptor;
@Module.Info(name = "KeepSprint", category = Module.Category.COMBAT)
public class KeepSprint extends Module {
    @SubscribeEvent
    private final EventListener<KeepSprintEvent> onsomething = e -> {e.setCancelled(true);};
}
