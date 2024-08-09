package im.expensive.ui.display;

import im.expensive.events.EventUpdate;
import im.expensive.utils.client.IMinecraft;

public interface ElementUpdater extends IMinecraft {

    void update(EventUpdate e);
}
