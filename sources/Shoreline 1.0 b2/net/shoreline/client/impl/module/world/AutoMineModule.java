package net.shoreline.client.impl.module.world;

import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;

/**
 *
 *
 * @author Shoreline
 * @since 1.0
 */
public class AutoMineModule extends ToggleModule
{
    public AutoMineModule()
    {
        super("AutoMine", "Automatically mines enemy blocks", ModuleCategory.WORLD);
    }
}
