package net.futureclient.client.modules.movement.speed;

import net.futureclient.client.events.Event;
import net.futureclient.client.ZG;
import net.futureclient.loader.mixin.common.wrapper.IMinecraft;
import net.futureclient.loader.mixin.common.wrapper.ITimer;
import net.futureclient.client.Bc;
import net.futureclient.client.pg;
import net.futureclient.client.modules.miscellaneous.Timer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.futureclient.client.events.EventPacket;
import net.futureclient.client.modules.movement.Speed;
import net.futureclient.client.we;
import net.futureclient.client.n;

public class Listener5 extends n<we>
{
    public final Speed k;
    
    public Listener5(final Speed k) {
        this.k = k;
        super();
    }
    
    public void M(final EventPacket eventPacket) {
        if (eventPacket.M() instanceof SPacketPlayerPosLook) {
            final Timer timer;
            if ((timer = (Timer)pg.M().M().M((Class)Bc.class)) != null && !timer.M()) {
                ((ITimer)((IMinecraft)Speed.getMinecraft83()).getTimer()).setTimerSpeed(1.0f);
            }
            Speed.M(this.k).e();
            Speed.M(this.k, ZG.e());
            Speed.b(this.k, 0);
            Speed.e(this.k, 0.0);
            Speed.B(this.k, 4);
            Speed.M(this.k, 1);
            Speed.e(this.k, 1);
            Speed.C(this.k, 2);
            Speed.i(this.k, 4);
        }
    }
    
    public void M(final Event event) {
        this.M((EventPacket)event);
    }
}
