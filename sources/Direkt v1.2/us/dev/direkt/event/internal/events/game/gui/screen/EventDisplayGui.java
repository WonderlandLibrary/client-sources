package us.dev.direkt.event.internal.events.game.gui.screen;

import net.minecraft.client.gui.GuiScreen;
import us.dev.direkt.event.CancellableEvent;

/**
 * @author Foundry
 */
public class EventDisplayGui extends CancellableEvent {
    private GuiScreen guiScreen;

    public EventDisplayGui(GuiScreen guiScreen) {
        this.guiScreen = guiScreen;
    }

    public GuiScreen getGuiScreen() {
        return this.guiScreen;
    }

    public void setGuiScreen(GuiScreen guiScreen) {
        this.guiScreen = guiScreen;
    }
}
