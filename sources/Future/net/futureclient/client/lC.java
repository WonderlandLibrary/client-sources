package net.futureclient.client;

import net.futureclient.client.events.Event;
import net.minecraft.client.gui.GuiScreen;
import net.futureclient.client.events.EventUpdate;
import net.futureclient.client.modules.render.ClickGui;

public class lC extends n<lF>
{
    public final ClickGui k;
    
    public lC(final ClickGui k) {
        this.k = k;
        super();
    }
    
    public void M(final EventUpdate eventUpdate) {
        KC.M().k = this.k.scale.B().doubleValue();
        ClickGui.getMinecraft().displayGuiScreen((GuiScreen)KC.M());
        pg.M().M().e(this);
    }
    
    public void M(final Event event) {
        this.M((EventUpdate)event);
    }
}
