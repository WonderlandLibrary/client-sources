package net.futureclient.client.modules.miscellaneous.chestaura;

import net.futureclient.client.events.EventWorld;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.miscellaneous.ChestAura;
import net.futureclient.client.fF;
import net.futureclient.client.n;

public class Listener2 extends n<fF>
{
    public final ChestAura k;
    
    public Listener2(final ChestAura k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventWorld)event);
    }
    
    public void M(final EventWorld eventWorld) {
        ChestAura.M(this.k).clear();
    }
}
