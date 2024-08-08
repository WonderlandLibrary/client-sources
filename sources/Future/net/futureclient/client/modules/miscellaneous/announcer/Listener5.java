package net.futureclient.client.modules.miscellaneous.announcer;

import net.futureclient.client.events.Event;
import java.util.Random;
import java.util.Map;
import net.futureclient.client.events.EventUpdate;
import net.futureclient.client.modules.miscellaneous.Announcer;
import net.futureclient.client.lF;
import net.futureclient.client.n;

public class Listener5 extends n<lF>
{
    public final Announcer k;
    
    public Listener5(final Announcer k) {
        this.k = k;
        super();
    }
    
    public void M(final EventUpdate eventUpdate) {
        if (Announcer.M(this.k).isEmpty() && Announcer.e(this.k).isEmpty() && Announcer.b(this.k).isEmpty()) {
            Announcer.e(this.k).e();
        }
        if (Announcer.getMinecraft1().world == null && Announcer.getMinecraft8().player == null) {
            return;
        }
        if (!Announcer.e(this.k).isEmpty()) {
            if (Announcer.e(this.k).e(9000L)) {
                Announcer.M(this.k, new StringBuilder().insert(0, "I just mined ").append(Announcer.e(this.k).get(Announcer.e(this.k).entrySet().iterator().next().getKey())).append(" ").append(Announcer.e(this.k).entrySet().iterator().next().getKey()).append("!").toString());
                Announcer.e(this.k).remove(Announcer.e(this.k).entrySet().iterator().next().getKey());
                Announcer.e(this.k).e();
            }
        }
        else {
            Announcer.e(this.k).clear();
            Listener5 listener5 = null;
            Label_0468: {
                if (!Announcer.M(this.k).isEmpty()) {
                    if (Announcer.e(this.k).e(9000L)) {
                        Announcer.M(this.k, new StringBuilder().insert(0, "I just placed ").append(Announcer.M(this.k).get(Announcer.M(this.k).entrySet().iterator().next().getKey())).append(" ").append(Announcer.M(this.k).entrySet().iterator().next().getKey()).append("!").toString());
                        Announcer.M(this.k).remove(Announcer.M(this.k).entrySet().iterator().next().getKey());
                        Announcer.e(this.k).e();
                        listener5 = this;
                        break Label_0468;
                    }
                }
                else {
                    Announcer.M(this.k).clear();
                }
                listener5 = this;
            }
            Listener5 listener6 = null;
            Label_0668: {
                if (!Announcer.b(listener5.k).isEmpty()) {
                    if (Announcer.e(this.k).e(9000L)) {
                        Announcer.M(this.k, new StringBuilder().insert(0, "I just ate ").append(Announcer.b(this.k).get(Announcer.b(this.k).entrySet().iterator().next().getKey())).append(" ").append(Announcer.b(this.k).entrySet().iterator().next().getKey()).append("!").toString());
                        Announcer.b(this.k).remove(Announcer.b(this.k).entrySet().iterator().next().getKey());
                        Announcer.e(this.k).e();
                        listener6 = this;
                        break Label_0668;
                    }
                }
                else {
                    Announcer.b(this.k).clear();
                }
                listener6 = this;
            }
            if ((boolean)Announcer.e(listener6.k).M() && Announcer.M(this.k).e(60000L)) {
                final double n;
                if ((n = Math.round((Math.round(Announcer.getMinecraft9().world.getWorldInfo().getWorldTime() / 0.0 * 0.0) / 0.0 - Math.round((float)(Announcer.getMinecraft4().world.getWorldInfo().getWorldTime() / 24000L))) * 0.0) / 0.0) == 0.0) {
                    Announcer.M(this.k, Announcer.c(this.k)[new Random().nextInt(Announcer.c(this.k).length)]);
                    Announcer.M(this.k).e();
                    return;
                }
                if (n == 4.75327057E-315) {
                    Announcer.M(this.k, Announcer.h(this.k)[new Random().nextInt(Announcer.h(this.k).length)]);
                    Announcer.M(this.k).e();
                    return;
                }
                if (n == 0.0) {
                    Announcer.M(this.k, Announcer.C(this.k)[new Random().nextInt(Announcer.C(this.k).length)]);
                    Announcer.M(this.k).e();
                    return;
                }
                if (n == 2.885914274E-315) {
                    Announcer.M(this.k, Announcer.g(this.k)[new Random().nextInt(Announcer.g(this.k).length)]);
                    Announcer.M(this.k).e();
                    return;
                }
                if (n == 4.07423192E-315) {
                    Announcer.M(this.k, Announcer.b(this.k)[new Random().nextInt(Announcer.b(this.k).length)]);
                    Announcer.M(this.k).e();
                    return;
                }
                if (n == 8.48798316E-315) {
                    Announcer.M(this.k, Announcer.B(this.k)[new Random().nextInt(Announcer.B(this.k).length)]);
                    Announcer.M(this.k).e();
                    return;
                }
                if (n == 0.0) {
                    Announcer.M(this.k, Announcer.i(this.k)[new Random().nextInt(Announcer.i(this.k).length)]);
                    Announcer.M(this.k).e();
                    return;
                }
                if (n == 1.0694858786E-314) {
                    Announcer.M(this.k, Announcer.M(this.k)[new Random().nextInt(Announcer.M(this.k).length)]);
                    Announcer.M(this.k).e();
                }
            }
        }
    }
    
    public void M(final Event event) {
        this.M((EventUpdate)event);
    }
}
