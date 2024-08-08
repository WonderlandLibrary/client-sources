package net.futureclient.client.modules.combat.copsandcrims;

import net.futureclient.client.ZG;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.combat.CopsAndCrims;
import net.futureclient.client.xD;
import net.futureclient.client.n;

public class Listener3 extends n<xD>
{
    public final CopsAndCrims k;
    
    public Listener3(final CopsAndCrims k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((xD)event);
    }
    
    @Override
    public void M(final xD xd) {
        if (CopsAndCrims.M(this.k) != null && !CopsAndCrims.getMinecraft14().player.inventory.getCurrentItem().isEmpty()) {
            ZG.M(xd, CopsAndCrims.e(this.k), CopsAndCrims.M(this.k));
        }
    }
}
