package net.futureclient.client.modules.render.nametags;

import net.futureclient.client.events.Event;
import net.futureclient.client.modules.render.Nametags;
import net.futureclient.client.AD;
import net.futureclient.client.n;

public class Listener2 extends n<AD>
{
    public final Nametags k;
    
    public Listener2(final Nametags k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final AD ad) {
        ad.M(true);
    }
    
    public void M(final Event event) {
        this.M((AD)event);
    }
}
