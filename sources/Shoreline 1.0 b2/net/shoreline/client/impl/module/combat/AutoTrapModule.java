package net.shoreline.client.impl.module.combat;

import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.PlaceBlockModule;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class AutoTrapModule extends PlaceBlockModule
{
    /**
     *
     */
    public AutoTrapModule()
    {
        super("AutoTrap", "Automatically traps nearby players in blocks",
                ModuleCategory.COMBAT);
    }
}
