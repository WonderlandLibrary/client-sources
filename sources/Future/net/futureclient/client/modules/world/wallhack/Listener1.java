package net.futureclient.client.modules.world.wallhack;

import net.futureclient.client.events.Event;
import net.futureclient.client.oh;
import net.futureclient.client.mh;
import net.futureclient.client.events.EventUpdate;
import net.futureclient.client.modules.world.Wallhack;
import net.futureclient.client.lF;
import net.futureclient.client.n;

public class Listener1 extends n<lF>
{
    public final Wallhack k;
    
    public Listener1(final Wallhack k) {
        this.k = k;
        super();
    }
    
    public void M(final EventUpdate eventUpdate) {
        this.k.e(String.format(mh.M("\t!2,6!=+~\u00e7i\u001b\u00f9\u0006{3\u00f9w\u0003"), Wallhack.M(this.k).M()));
        Wallhack.getMinecraft().gameSettings.gammaSetting = 11.0f;
        if (Wallhack.M(this.k) != this.k.opacity.B().intValue()) {
            Wallhack.M(this.k, this.k.opacity.B().intValue());
            oh.M();
        }
    }
    
    public void M(final Event event) {
        this.M((EventUpdate)event);
    }
}
