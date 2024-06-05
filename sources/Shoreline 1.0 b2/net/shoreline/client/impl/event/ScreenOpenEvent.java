package net.shoreline.client.impl.event;

import net.shoreline.client.api.event.Event;
import net.minecraft.client.gui.screen.Screen;

/**
 *
 * @author linus
 * @since 1.0
 */
public class ScreenOpenEvent extends Event
{
    //
    private final Screen screen;

    public ScreenOpenEvent(Screen screen)
    {
        this.screen = screen;
    }

    public Screen getScreen()
    {
        return screen;
    }
}
