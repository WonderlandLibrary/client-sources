package zamorozka.event.events;

import net.minecraft.client.gui.GuiScreen;
import zamorozka.event.Event;

public class EventDisplayGui extends Event {
	private GuiScreen screen;

    public EventDisplayGui(GuiScreen screen) {
        this.screen = screen;
    }

    public GuiScreen getScreen() {
        return screen;
    }

    public void setScreen(GuiScreen screen) {
        this.screen = screen;
    }
}