package net.futureclient.client.modules.render.norender;

import net.futureclient.client.events.Event;
import net.futureclient.client.modules.render.NoRender;
import net.futureclient.client.rf;
import net.futureclient.client.n;

public class Listener6 extends n<rf>
{
    public final NoRender k;
    
    public Listener6(final NoRender k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final rf rf) {
        rf.M((boolean)this.k.spawners.M());
    }
    
    public void M(final Event event) {
        this.M((rf)event);
    }
}
