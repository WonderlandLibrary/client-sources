package net.futureclient.client;

import net.minecraft.network.play.client.CPacketPlayer;
import net.futureclient.client.events.Event;

public class hc extends n<Ag>
{
    public final bd k;
    
    public hc(final bd k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((Ag)event);
    }
    
    @Override
    public void M(final Ag ag) {
        if (!bd.M(this.k).M() || bd.M(this.k)) {
            return;
        }
        if (ag.M() instanceof CPacketPlayer) {
            bd.M(this.k).add(ag.M());
            ag.M(true);
        }
    }
}
