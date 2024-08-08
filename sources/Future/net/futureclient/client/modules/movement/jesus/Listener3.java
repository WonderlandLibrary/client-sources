package net.futureclient.client.modules.movement.jesus;

import net.futureclient.client.events.Event;
import net.futureclient.client.IG;
import net.futureclient.client.ZG;
import net.futureclient.client.dd;
import net.futureclient.client.pg;
import net.futureclient.client.modules.render.Freecam;
import net.futureclient.client.Cb;
import net.futureclient.client.events.EventUpdate;
import net.futureclient.client.modules.movement.Jesus;
import net.futureclient.client.lF;
import net.futureclient.client.n;

public class Listener3 extends n<lF>
{
    public final Jesus k;
    
    public Listener3(final Jesus k) {
        this.k = k;
        super();
    }
    
    public void M(final EventUpdate eventUpdate) {
        if (!Jesus.M(this.k).e(800L)) {
            return;
        }
        if (Jesus.M(this.k).M() == Cb.gA.d) {
            final Freecam freecam;
            if (((freecam = (Freecam)pg.M().M().M((Class)dd.class)) != null && freecam.M()) || ZG.M().fallDistance > 3.0f) {
                return;
            }
            if ((Jesus.getMinecraft30().player.isInLava() || Jesus.getMinecraft28().player.isInWater()) && !Jesus.getMinecraft13().player.isSneaking()) {
                ZG.M().motionY = 1.273197475E-314;
                return;
            }
            if (IG.e() && !Jesus.getMinecraft32().player.isSneaking()) {
                ZG.M().motionY = 1.273197475E-314;
            }
        }
    }
    
    public void M(final Event event) {
        this.M((EventUpdate)event);
    }
}
