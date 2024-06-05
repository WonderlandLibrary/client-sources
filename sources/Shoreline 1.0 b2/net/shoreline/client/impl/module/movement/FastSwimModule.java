package net.shoreline.client.impl.module.movement;

import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;

// Is this still necessary in 1.19?
public class FastSwimModule extends ToggleModule
{
    /**
     *
     */
    public FastSwimModule()
    {
        super("FastSwim", "Allows the player to swim faster",
                ModuleCategory.MOVEMENT);
    }
}
