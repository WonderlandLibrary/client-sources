package net.shoreline.client.impl.module.combat;

import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class AutoXPModule extends ToggleModule
{

    public AutoXPModule()
    {
        super("AutoXP", "Automatically mends armor using XP bottles",
                ModuleCategory.COMBAT);
    }
}
