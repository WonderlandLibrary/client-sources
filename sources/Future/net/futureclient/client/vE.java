package net.futureclient.client;

import net.minecraft.network.play.client.CPacketPlayer;
import net.futureclient.client.events.Event;

public class vE extends n<Ag>
{
    public final ne k;
    
    public vE(final ne k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((Ag)event);
    }
    
    @Override
    public void M(final Ag ag) {
        if (ag.M() instanceof CPacketPlayer) {
            final CPacketPlayer cPacketPlayer = (CPacketPlayer)ag.M();
            if (ne.M(this.k).contains(cPacketPlayer)) {
                ne.M(this.k).remove(cPacketPlayer);
                return;
            }
            ag.M(true);
        }
    }
}
