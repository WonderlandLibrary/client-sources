package net.futureclient.client.modules.render.norender;

import net.futureclient.client.events.Event;
import net.futureclient.client.modules.render.NoRender;
import net.futureclient.client.Rf;
import net.futureclient.client.n;

public class Listener3 extends n<Rf>
{
    public final NoRender k;
    
    public Listener3(final NoRender k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final Rf rf) {
        rf.M((boolean)this.k.hurtCamera.M());
    }
    
    public void M(final Event event) {
        this.M((Rf)event);
    }
}
