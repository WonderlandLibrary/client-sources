package net.futureclient.client.modules.miscellaneous.announcer;

import java.util.Random;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.miscellaneous.Announcer;
import net.futureclient.client.tF;
import net.futureclient.client.n;

public class Listener2 extends n<tF>
{
    public final Announcer k;
    
    public Listener2(final Announcer k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((tF)event);
    }
    
    @Override
    public void M(final tF tf) {
        if (Announcer.B(this.k).M() && Announcer.b(this.k).e(2500L) && !tf.M().equals(Announcer.getMinecraft6().player.getName())) {
            Announcer.M(this.k, Announcer.e(this.k)[new Random().nextInt(Announcer.e(this.k).length)] + tf.M() + ".");
            Announcer.b(this.k).e();
        }
    }
}
