package net.futureclient.client.modules.miscellaneous.automount;

import net.futureclient.client.events.Event;
import net.futureclient.client.modules.miscellaneous.AutoMount;
import net.futureclient.client.DE;
import net.futureclient.client.n;

public class Listener2 extends n<DE>
{
    public final AutoMount k;
    
    public Listener2(final AutoMount k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final DE de) {
        de.M(true);
    }
    
    public void M(final Event event) {
        this.M((DE)event);
    }
}
