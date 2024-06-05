package net.shoreline.client.impl.module.render;

import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.event.render.BobViewEvent;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class NoBobModule extends ToggleModule
{
    /**
     *
     */
    public NoBobModule()
    {
        super("NoBob", "Prevents the hand from bobbing while moving",
                ModuleCategory.RENDER);
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onBobView(BobViewEvent event)
    {
        event.cancel();
    }
}
