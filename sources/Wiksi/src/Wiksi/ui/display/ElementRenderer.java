package src.Wiksi.ui.display;

import src.Wiksi.events.EventDisplay;
import src.Wiksi.utils.client.IMinecraft;

public interface ElementRenderer extends IMinecraft {
    void render(EventDisplay eventDisplay);
}

