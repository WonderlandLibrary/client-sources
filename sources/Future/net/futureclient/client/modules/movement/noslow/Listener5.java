package net.futureclient.client.modules.movement.noslow;

import net.futureclient.client.events.Event;
import net.futureclient.client.modules.movement.NoSlow;
import net.futureclient.client.ID;
import net.futureclient.client.n;

public class Listener5 extends n<ID>
{
    public final NoSlow k;
    
    public Listener5(final NoSlow k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((ID)event);
    }
    
    @Override
    public void M(final ID id) {
        if (this.k.soulSand.M()) {
            id.M(true);
        }
    }
}
