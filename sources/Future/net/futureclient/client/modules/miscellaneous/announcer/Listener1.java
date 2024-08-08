package net.futureclient.client.modules.miscellaneous.announcer;

import java.util.Random;
import net.futureclient.client.pg;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.miscellaneous.Announcer;
import net.futureclient.client.uF;
import net.futureclient.client.n;

public class Listener1 extends n<uF>
{
    public final Announcer k;
    
    public Listener1(final Announcer k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((uF)event);
    }
    
    @Override
    public void M(final uF uf) {
        if (Announcer.b(this.k).M() && Announcer.b(this.k).e(2500L)) {
            Listener1 listener1;
            if (pg.M().M().M(uf.M()) && !uf.M().equals(Announcer.getMinecraft12().player.getName())) {
                listener1 = this;
                Announcer.M(this.k, new StringBuilder().insert(0, "My friend ").append(uf.M()).append(" just joined the server!").toString());
            }
            else {
                if (!uf.M().equals(Announcer.getMinecraft11().player.getName()) && Announcer.getMinecraft7().getCurrentServerData() != null) {
                    Announcer.M(this.k, Announcer.K(this.k)[new Random().nextInt(Announcer.K(this.k).length)].replaceFirst("SERVERIP1D5A9E", Announcer.getMinecraft10().getCurrentServerData().serverIP) + uf.M() + ".");
                }
                listener1 = this;
            }
            Announcer.b(listener1.k).e();
        }
    }
}
