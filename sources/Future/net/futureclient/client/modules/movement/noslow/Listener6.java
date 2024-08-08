package net.futureclient.client.modules.movement.noslow;

import net.futureclient.client.events.Event;
import net.futureclient.client.modules.movement.NoSlow;
import net.futureclient.client.DE;
import net.futureclient.client.n;

public class Listener6 extends n<DE>
{
    public final NoSlow k;
    
    public Listener6(final NoSlow k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final DE de) {
        if (this.k.items.M()) {
            de.M(true);
        }
    }
    
    public void M(final Event event) {
        this.M((DE)event);
    }
}
