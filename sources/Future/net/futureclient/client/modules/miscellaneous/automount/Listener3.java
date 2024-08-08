package net.futureclient.client.modules.miscellaneous.automount;

import net.futureclient.client.ZG;
import net.futureclient.client.pg;
import net.futureclient.client.te;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.miscellaneous.AutoMount;
import net.futureclient.client.xD;
import net.futureclient.client.n;

public class Listener3 extends n<xD>
{
    public final AutoMount k;
    
    public Listener3(final AutoMount k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((xD)event);
    }
    
    @Override
    public void M(final xD xd) {
        final te te = (te)pg.M().M().M((Class)te.class);
        if (AutoMount.M(this.k) != null && (boolean)te.W.M() && AutoMount.getMinecraft().player.getRidingEntity() == null) {
            ZG.M(xd, AutoMount.e(this.k), AutoMount.M(this.k));
        }
    }
}
