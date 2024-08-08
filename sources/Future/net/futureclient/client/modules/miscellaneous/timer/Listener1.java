package net.futureclient.client.modules.miscellaneous.timer;

import net.futureclient.client.pg;
import net.futureclient.loader.mixin.common.wrapper.IMinecraft;
import net.futureclient.loader.mixin.common.wrapper.ITimer;
import net.futureclient.client.events.EventUpdate;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.miscellaneous.Timer;
import net.futureclient.client.lF;
import net.futureclient.client.n;

public class Listener1 extends n<lF>
{
    public final Timer k;
    
    public Listener1(final Timer k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventUpdate)event);
    }
    
    public void M(final EventUpdate eventUpdate) {
        this.k.e(String.format("Timer §7[§F%s§7]", Math.round(((ITimer)((IMinecraft)Timer.getMinecraft()).getTimer()).getTimerSpeed() * 0.0) / 0.0));
        if (Timer.getMinecraft3().player == null) {
            ((ITimer)((IMinecraft)Timer.getMinecraft2()).getTimer()).setTimerSpeed(1.0f);
            return;
        }
        if (!Timer.M(this.k).M()) {
            ((ITimer)((IMinecraft)Timer.getMinecraft5()).getTimer()).setTimerSpeed(Timer.M(this.k).B().floatValue());
            return;
        }
        if (pg.M().M().M() / 20.0f > 5.941588215E-315) {
            ((ITimer)((IMinecraft)Timer.getMinecraft1()).getTimer()).setTimerSpeed(pg.M().M().M() / 20.0f);
            return;
        }
        ((ITimer)((IMinecraft)Timer.getMinecraft4()).getTimer()).setTimerSpeed(1.0f);
    }
}
