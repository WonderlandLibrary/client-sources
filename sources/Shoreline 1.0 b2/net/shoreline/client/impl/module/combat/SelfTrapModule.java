package net.shoreline.client.impl.module.combat;

import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.PlaceBlockModule;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class SelfTrapModule extends PlaceBlockModule
{
    /**
     *
     */
    public SelfTrapModule()
    {
        super("SelfTrap", "Fully surrounds the player with blocks",
                ModuleCategory.COMBAT);
    }
}
