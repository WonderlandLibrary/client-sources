package net.futureclient.client;

import net.futureclient.client.events.Event;
import java.util.Iterator;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.futureclient.client.events.EventPacket;

public class GI extends n<we>
{
    public final rG k;
    
    public GI(final rG k) {
        this.k = k;
        super();
    }
    
    public void M(final EventPacket eventPacket) {
        if (eventPacket.M() instanceof SPacketTimeUpdate) {
            if (rG.M(this.k) != 0L) {
                if (rG.M(this.k).size() > 20) {
                    rG.M(this.k).poll();
                }
                rG.M(this.k).add(20.0f * (1000.0f / (System.currentTimeMillis() - rG.M(this.k))));
                float n = 0.0f;
                Iterator<Float> iterator2;
                final Iterator<Float> iterator = iterator2 = rG.M(this.k).iterator();
                while (iterator2.hasNext()) {
                    n += Math.max(0.0f, Math.min(20.0f, iterator.next()));
                    iterator2 = iterator;
                }
                rG.M(this.k, n / rG.M(this.k).size());
            }
            rG.M(this.k, System.currentTimeMillis());
        }
    }
    
    public void M(final Event event) {
        this.M((EventPacket)event);
    }
}
