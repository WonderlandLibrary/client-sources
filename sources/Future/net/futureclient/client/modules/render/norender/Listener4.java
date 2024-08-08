package net.futureclient.client.modules.render.norender;

import net.futureclient.client.events.Event;
import net.futureclient.client.modules.render.NoRender;
import net.futureclient.client.De;
import net.futureclient.client.n;

public class Listener4 extends n<De>
{
    public final NoRender k;
    
    public Listener4(final NoRender k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((De)event);
    }
    
    @Override
    public void M(final De de) {
        de.M((boolean)this.k.totemAnimation.M());
    }
}
