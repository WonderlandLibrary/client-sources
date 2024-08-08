package net.futureclient.client.modules.movement.velocity;

import net.futureclient.client.events.Event;
import net.futureclient.client.ad;
import net.futureclient.client.events.EventMotion;
import net.futureclient.client.modules.movement.Velocity;
import net.futureclient.client.KF;
import net.futureclient.client.n;

public class Listener3 extends n<KF>
{
    public final Velocity k;
    
    public Listener3(final Velocity k) {
        this.k = k;
        super();
    }
    
    public void M(final EventMotion eventMotion) {
        Listener3 listener3;
        if (((ad.Dc)Velocity.M(this.k).M()).equals((Object)ad.Dc.D)) {
            final Velocity k = this.k;
            final StringBuilder insert = new StringBuilder().insert(0, "Velocity §7[§FH");
            listener3 = this;
            k.e(insert.append(this.k.horizontal.B().doubleValue()).append("%§7|§FV").append(this.k.vertical.B().doubleValue()).append("%§7]").toString());
        }
        else {
            this.k.e(String.format("Velocity §7[§F%s§7]", Velocity.M(this.k).M()));
            listener3 = this;
        }
        if (((ad.Dc)Velocity.M(listener3.k).M()).equals((Object)ad.Dc.a) && Velocity.getMinecraft4().player.hurtTime != 0) {
            Velocity.getMinecraft3().player.onGround = true;
        }
    }
    
    public void M(final Event event) {
        this.M((EventMotion)event);
    }
}
