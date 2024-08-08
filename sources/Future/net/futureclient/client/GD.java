package net.futureclient.client;

import net.futureclient.client.events.Event;

public class GD extends n<xe>
{
    public final Ea k;
    
    public GD(final Ea k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final xe xe) {
        if (!Ea.M(this.k) && this.k.M > -100.0f) {
            final Ea k = this.k;
            k.M -= 5.0f;
            return;
        }
        pg.M().M().e(this);
    }
    
    public void M(final Event event) {
        this.M((xe)event);
    }
}
