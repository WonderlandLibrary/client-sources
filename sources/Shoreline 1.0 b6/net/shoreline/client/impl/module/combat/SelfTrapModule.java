package net.shoreline.client.impl.module.combat;

import net.shoreline.client.api.module.BlockPlacerModule;
import net.shoreline.client.api.module.ModuleCategory;

/**
 * @author linus
 * @since 1.0
 */
public class SelfTrapModule extends BlockPlacerModule {
    /**
     *
     */
    public SelfTrapModule() {
        super("SelfTrap", "Fully surrounds the player with blocks",
                ModuleCategory.COMBAT);
    }
}
