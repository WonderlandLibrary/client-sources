package net.shoreline.client.impl.module.world;

import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.RotationModule;
import net.shoreline.client.impl.event.network.PlayerTickEvent;

/**
 * @author xgraza
 * @since 03/29/24
 */
public final class ScaffoldModule extends RotationModule {

    public ScaffoldModule() {
        super("Scaffold", "Rapidly places blocks under your feet", ModuleCategory.WORLD);
    }

    @EventListener
    public void onUpdate(final PlayerTickEvent event) {
    }
}
