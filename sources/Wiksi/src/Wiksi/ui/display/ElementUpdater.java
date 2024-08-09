package src.Wiksi.ui.display;

import src.Wiksi.events.EventUpdate;
import src.Wiksi.utils.client.IMinecraft;

public interface ElementUpdater extends IMinecraft {

    void update(EventUpdate e);
}
