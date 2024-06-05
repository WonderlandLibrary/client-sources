package net.shoreline.client.impl.module.combat;

import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.event.AttackCooldownEvent;

/**
 *
 *
 * @author Shoreline
 * @since 1.0
 */
public class NoHitDelayModule extends ToggleModule
{
    /**
     *
     */
    public NoHitDelayModule()
    {
        super("NoHitDelay", "Prevents hit delay when clicking fast", ModuleCategory.COMBAT);
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onAttackCooldown(AttackCooldownEvent event)
    {
        event.cancel();
    }
}
