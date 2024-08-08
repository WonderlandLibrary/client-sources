package net.futureclient.client.modules.render.viewclip;

import net.futureclient.client.events.Event;
import net.futureclient.client.modules.render.ViewClip;
import net.futureclient.client.Lg;
import net.futureclient.client.n;

public class Listener1 extends n<Lg>
{
    public final ViewClip k;
    
    public Listener1(final ViewClip k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final Lg lg) {
        lg.M(true);
        lg.M(this.k.distance.B().floatValue());
    }
    
    public void M(final Event event) {
        this.M((Lg)event);
    }
}
