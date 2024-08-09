package fun.ellant.ui.display;

import fun.ellant.events.EventUpdate;
import fun.ellant.utils.client.IMinecraft;

public interface ElementUpdater extends IMinecraft {

    void update(EventUpdate e);
}
