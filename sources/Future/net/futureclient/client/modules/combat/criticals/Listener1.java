package net.futureclient.client.modules.combat.criticals;

import net.futureclient.client.events.EventMotion;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.combat.Criticals;
import net.futureclient.client.KF;
import net.futureclient.client.n;

public class Listener1 extends n<KF>
{
    public final Criticals k;
    
    public Listener1(final Criticals k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventMotion)event);
    }
    
    public void M(final EventMotion eventMotion) {
        this.k.e(String.format("Criticals §7[§F%s§7]", this.k.mode.M()));
    }
}
