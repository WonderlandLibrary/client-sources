package wtf.resolute.moduled.impl.render.HUD.HudElement;

import wtf.resolute.evented.EventUpdate;
import wtf.resolute.utiled.client.IMinecraft;

public interface ElementUpdater extends IMinecraft {

    void update(EventUpdate e);
}
