package wtf.resolute.moduled.impl.render.HUD.HudElement;

import wtf.resolute.evented.EventDisplay;
import wtf.resolute.utiled.client.IMinecraft;

public interface ElementRenderer extends IMinecraft {
    void render(EventDisplay eventDisplay);
}
