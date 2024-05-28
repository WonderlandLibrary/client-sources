package arsenic.event.impl;

import arsenic.event.types.CancellableEvent;
import arsenic.event.types.Event;
import net.minecraft.client.gui.GuiScreen;

public class EventDisplayGuiScreen implements Event {

    private final GuiScreen guiScreen;

    public EventDisplayGuiScreen(GuiScreen guiScreen) {
        this.guiScreen = guiScreen;
    }

    public GuiScreen getGuiScreen() {
        return guiScreen;
    }
}
