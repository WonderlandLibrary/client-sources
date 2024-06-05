package net.shoreline.client.impl.module.render;

import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.event.gui.hud.PlayerListEvent;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class ExtraTabModule extends ToggleModule
{
    /**
     *
     */
    public ExtraTabModule()
    {
        super("ExtraTab", "Expands the tab list size to allow for more players",
                ModuleCategory.RENDER);
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onPlayerList(PlayerListEvent event)
    {
        event.cancel();
    }
}
