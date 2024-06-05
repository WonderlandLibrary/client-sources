package net.shoreline.client.impl.module.misc;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.BooleanConfig;
import net.shoreline.client.api.config.setting.NumberConfig;
import net.shoreline.client.api.event.EventStage;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.event.TickEvent;

/**
 * @author linus
 * @since 1.0
 */
public class AutoMapartModule extends ToggleModule {
    //
    Config<Float> rangeConfig = new NumberConfig<>("Range", "The range to place maps around the player", 0.1f, 6.0f, 10.0f);
    Config<Boolean> rotateConfig = new BooleanConfig("Rotate", "Rotates before placing maps", false);

    /**
     *
     */
    public AutoMapartModule() {
        super("AutoMapart", "Automatically places maparts on walls",
                ModuleCategory.MISCELLANEOUS);
    }

    @EventListener
    public void onTick(TickEvent event) {
        if (event.getStage() == EventStage.PRE) {

        }
    }
}
