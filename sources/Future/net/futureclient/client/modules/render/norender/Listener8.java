package net.futureclient.client.modules.render.norender;

import net.futureclient.client.events.Event;
import net.futureclient.client.modules.render.NoRender;
import net.futureclient.client.Qe;
import net.futureclient.client.n;

public class Listener8 extends n<Qe>
{
    public final NoRender k;
    
    public Listener8(final NoRender k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final Qe qe) {
        qe.M((boolean)this.k.barriers.M());
    }
    
    public void M(final Event event) {
        this.M((Qe)event);
    }
}
