package us.dev.direkt.event.internal.filters;

import net.minecraft.client.gui.GuiScreen;
import us.dev.direkt.event.internal.events.game.gui.screen.EventDisplayGui;
import us.dev.dvent.Link;
import us.dev.dvent.filter.Filter;

/**
 * @author Foundry
 */
public class GuiScreenFilter implements Filter<EventDisplayGui> {

    private final Class<? extends GuiScreen>[] screens;

    @SafeVarargs
    public GuiScreenFilter(Class<? extends GuiScreen>... screens) {
        this.screens = screens;
    }

    @Override
    public boolean test(Link<EventDisplayGui> link, EventDisplayGui event) {
        for (Class<? extends GuiScreen> screenClass : screens) {
            if (screenClass.isAssignableFrom(event.getGuiScreen().getClass())) {
                return true;
            }
        }
        return false;
    }
}
