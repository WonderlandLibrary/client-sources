package net.futureclient.client.modules.miscellaneous.autorespawn;

import net.futureclient.client.events.Event;
import net.futureclient.client.events.EventUpdate;
import net.futureclient.client.modules.miscellaneous.AutoRespawn;
import net.futureclient.client.lF;
import net.futureclient.client.n;

public class Listener1 extends n<lF>
{
    public final AutoRespawn k;
    
    public Listener1(final AutoRespawn k) {
        this.k = k;
        super();
    }
    
    public void M(final EventUpdate eventUpdate) {
        if (AutoRespawn.getMinecraft().player.getHealth() <= 0.0f) {
            AutoRespawn.getMinecraft1().player.respawnPlayer();
        }
    }
    
    public void M(final Event event) {
        this.M((EventUpdate)event);
    }
}
