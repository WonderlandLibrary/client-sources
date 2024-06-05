package net.shoreline.client.impl.module.combat;

import net.shoreline.client.api.module.BlockPlacerModule;
import net.shoreline.client.api.module.ModuleCategory;

/**
 * @author linus
 * @since 1.0
 */
public class AutoTrapModule extends BlockPlacerModule {

    /**
     *
     */
    public AutoTrapModule() {
        super("AutoTrap", "Automatically traps nearby players in blocks",
                ModuleCategory.COMBAT);
    }
}
