package net.futureclient.client.modules.render.extratab;

import net.futureclient.client.events.Event;
import net.futureclient.client.modules.render.ExtraTab;
import net.futureclient.client.wF;
import net.futureclient.client.n;

public class Listener1 extends n<wF>
{
    public final ExtraTab k;
    
    public Listener1(final ExtraTab k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((wF)event);
    }
    
    @Override
    public void M(final wF wf) {
        wf.e(true);
    }
}
