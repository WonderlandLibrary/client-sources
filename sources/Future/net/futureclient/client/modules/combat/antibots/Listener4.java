package net.futureclient.client.modules.combat.antibots;

import net.minecraft.client.gui.GuiDownloadTerrain;
import net.futureclient.client.events.EventGuiScreen;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.combat.AntiBots;
import net.futureclient.client.IF;
import net.futureclient.client.n;

public class Listener4 extends n<IF>
{
    public final AntiBots k;
    
    public Listener4(final AntiBots k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventGuiScreen)event);
    }
    
    public void M(final EventGuiScreen eventGuiScreen) {
        if (eventGuiScreen.M() instanceof GuiDownloadTerrain) {
            this.k.K.clear();
        }
    }
}
