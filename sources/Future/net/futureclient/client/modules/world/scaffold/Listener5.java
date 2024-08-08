package net.futureclient.client.modules.world.scaffold;

import net.futureclient.client.events.Event;
import net.futureclient.client.modules.world.Scaffold;
import net.futureclient.client.JD;
import net.futureclient.client.n;

public class Listener5 extends n<JD>
{
    public final Scaffold k;
    
    public Listener5(final Scaffold k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((JD)event);
    }
    
    @Override
    public void M(final JD jd) {
        jd.M((boolean)Scaffold.M(this.k).M());
    }
}
