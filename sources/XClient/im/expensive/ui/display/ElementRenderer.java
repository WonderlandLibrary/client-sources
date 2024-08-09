package im.expensive.ui.display;

import im.expensive.events.EventDisplay;
import im.expensive.utils.client.IMinecraft;

public interface ElementRenderer extends IMinecraft {
    void render(EventDisplay eventDisplay);
}
