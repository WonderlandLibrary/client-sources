package wtf.diablo.client.module.impl.combat;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import wtf.diablo.client.event.impl.player.SlowDownEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;

@ModuleMetaData(name = "Keep Sprint", description = "Continue sprinting after attack.", category = ModuleCategoryEnum.COMBAT)
public final class KeepSprintModule extends AbstractModule {
    @EventHandler
    private final Listener<SlowDownEvent> slowDownEventListener = e -> {
        if (mc.thePlayer == null) return;

        if (e.getMode() == SlowDownEvent.Mode.Sprint) {
            e.setCancelled(true);
        }
    };
}
