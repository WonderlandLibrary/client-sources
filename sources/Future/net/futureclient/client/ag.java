package net.futureclient.client;

import net.minecraft.client.gui.GuiDownloadTerrain;
import net.futureclient.client.events.EventGuiScreen;
import net.futureclient.client.events.Event;

public class ag extends n<IF>
{
    public final te k;
    
    public ag(final te k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventGuiScreen)event);
    }
    
    public void M(final EventGuiScreen eventGuiScreen) {
        if (!((te.sF)te.M(this.k).M()).equals((Object)te.sF.a) && eventGuiScreen.M() instanceof GuiDownloadTerrain) {
            this.k.M(false);
        }
    }
}
