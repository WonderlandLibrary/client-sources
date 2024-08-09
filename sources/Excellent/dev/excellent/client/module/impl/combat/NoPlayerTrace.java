package dev.excellent.client.module.impl.combat;

import dev.excellent.api.event.CancellableEvent;
import dev.excellent.api.event.impl.player.EntityRayTraceEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;

@ModuleInfo(name = "No Player Trace", category = Category.COMBAT, description = "Позволяет открывать сундуки через игроков.")
public class NoPlayerTrace extends Module {
    private final Listener<EntityRayTraceEvent> onRayTrace = CancellableEvent::cancel;
}
