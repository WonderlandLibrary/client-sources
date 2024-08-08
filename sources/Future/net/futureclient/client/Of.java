package net.futureclient.client;

import net.futureclient.client.events.Event;

public class Of extends n<xe>
{
    public final Ea k;
    
    public Of(final Ea k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((xe)event);
    }
    
    @Override
    public void M(final xe xe) {
        if (Ea.M(this.k) && this.k.M < 0.0f) {
            final Ea k = this.k;
            k.M += 5.0f;
            return;
        }
        pg.M().M().e(this);
    }
}
