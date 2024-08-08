package net.futureclient.client.modules.world.scaffold;

import net.futureclient.client.events.Event;
import net.futureclient.client.ZG;
import net.futureclient.client.modules.world.Scaffold;
import net.futureclient.client.xD;
import net.futureclient.client.n;

public class Listener4 extends n<xD>
{
    public final Scaffold k;
    
    public Listener4(final Scaffold k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final xD xd) {
        if (this.k.j != null || !Scaffold.M(this.k).e(Scaffold.M(this.k).B().floatValue() * 5.0f)) {
            ZG.M(xd, Scaffold.M(this.k), Scaffold.e(this.k));
        }
    }
    
    public void M(final Event event) {
        this.M((xD)event);
    }
}
