package dev.excellent.client.module.impl.player;

import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;

@ModuleInfo(name = "No Jump Delay", description = "Убирает задержку на прыжки.", category = Category.PLAYER)
public class NoJumpDelay extends Module {
    private final Listener<UpdateEvent> onUpdate = event -> {
        if (mc.player == null) return;
        mc.player.setJumpTicks(0);
    };
}
