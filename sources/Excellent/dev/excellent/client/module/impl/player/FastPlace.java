package dev.excellent.client.module.impl.player;

import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.value.impl.NumberValue;

@ModuleInfo(name = "Fast Place", description = "Убирает задержку на использование ПКМ.", category = Category.PLAYER)
public class FastPlace extends Module {
    private final NumberValue delay = new NumberValue("Задержка", this, 0, 0, 3, 1);
    private final Listener<UpdateEvent> onUpdate = event -> {
        if (mc.player == null) return;
        mc.setRightClickDelayTimer(Math.min(mc.getRightClickDelayTimer(), this.delay.getValue().intValue()));
    };
}
