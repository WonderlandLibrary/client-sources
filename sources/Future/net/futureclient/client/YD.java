package net.futureclient.client;

import net.futureclient.client.events.Event;
import java.util.Iterator;
import net.futureclient.loader.mixin.common.wrapper.IMinecraft;
import net.futureclient.loader.mixin.common.wrapper.ITimer;
import net.minecraft.entity.Entity;
import net.futureclient.client.events.EventUpdate;

public class YD extends n<lF>
{
    public final gD k;
    
    public YD(final gD k) {
        this.k = k;
        super();
    }
    
    public void M(final EventUpdate eventUpdate) {
        if (gD.M(this.k).size() > 20) {
            gD.M(this.k).poll();
        }
        gD.M(this.k).add(ZG.M((Entity)gD.B().player) * ((ITimer)((IMinecraft)gD.K()).getTimer()).getTimerSpeed());
        double n = 0.0;
        Iterator<Double> iterator2;
        final Iterator<Double> iterator = iterator2 = gD.M(this.k).iterator();
        while (iterator2.hasNext()) {
            n += iterator.next();
            iterator2 = iterator;
        }
        gD.M(this.k, n / gD.M(this.k).size());
    }
    
    public void M(final Event event) {
        this.M((EventUpdate)event);
    }
}
