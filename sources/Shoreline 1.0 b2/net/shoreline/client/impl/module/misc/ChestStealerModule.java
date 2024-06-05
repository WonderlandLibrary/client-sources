package net.shoreline.client.impl.module.misc;

import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class ChestStealerModule extends ToggleModule
{
    /**
     *
     */
    public ChestStealerModule()
    {
        super("ChestStealer", "Steals valuable items from chests",
                ModuleCategory.MISCELLANEOUS);
    }
}
