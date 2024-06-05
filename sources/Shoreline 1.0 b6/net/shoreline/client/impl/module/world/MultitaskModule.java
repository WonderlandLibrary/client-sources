package net.shoreline.client.impl.module.world;

import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.event.ItemMultitaskEvent;

/**
 * @author linus
 * @since 1.0
 */
public class MultitaskModule extends ToggleModule {

    public MultitaskModule() {
        super("MultiTask", "Allows you to mine and use items simultaneously", ModuleCategory.WORLD);
    }

    @EventListener
    public void onItemMultitask(ItemMultitaskEvent event) {
        event.cancel();
    }
}
