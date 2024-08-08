package net.futureclient.client.modules.render.norender;

import net.futureclient.client.events.Event;
import net.futureclient.client.modules.render.NoRender;
import net.futureclient.client.Nf;
import net.futureclient.client.n;

public class Listener2 extends n<Nf>
{
    public final NoRender k;
    
    public Listener2(final NoRender k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final Nf nf) {
        nf.M((boolean)this.k.fire.M());
    }
    
    public void M(final Event event) {
        this.M((Nf)event);
    }
}
