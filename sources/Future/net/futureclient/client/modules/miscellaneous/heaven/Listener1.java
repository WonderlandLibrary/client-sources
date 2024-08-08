package net.futureclient.client.modules.miscellaneous.heaven;

import net.futureclient.client.Yb;
import net.futureclient.client.events.EventMotion;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.miscellaneous.Heaven;
import net.futureclient.client.KF;
import net.futureclient.client.n;

public class Listener1 extends n<KF>
{
    public final Heaven k;
    
    public Listener1(final Heaven k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventMotion)event);
    }
    
    public void M(final EventMotion eventMotion) {
        switch (Yb.k[eventMotion.M().ordinal()]) {
            case 1:
                if (!Heaven.getMinecraft1().player.isEntityAlive()) {
                    Heaven.getMinecraft2().player.motionY = Heaven.M(this.k).B().floatValue();
                    break;
                }
                break;
        }
    }
}
