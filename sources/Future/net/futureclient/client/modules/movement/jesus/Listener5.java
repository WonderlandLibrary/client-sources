package net.futureclient.client.modules.movement.jesus;

import net.futureclient.client.ZG;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.movement.Jesus;
import net.futureclient.client.DD;
import net.futureclient.client.n;

public class Listener5 extends n<DD>
{
    public final Jesus k;
    
    public Listener5(final Jesus k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((DD)event);
    }
    
    @Override
    public void M(final DD dd) {
        if ((ZG.M().isInWater() || ZG.M().isInLava()) && (ZG.M().motionY == 1.273197475E-314 || ZG.M().motionY == 0.0)) {
            dd.M(true);
        }
    }
}
