package fun.ellant.ui.display;

import fun.ellant.events.EventDisplay;
import fun.ellant.utils.client.IMinecraft;

public interface ElementRenderer extends IMinecraft {
    void render(EventDisplay eventDisplay);
}
