package net.futureclient.client;

import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.futureclient.client.events.EventPacket;
import net.futureclient.client.events.Event;

public class nA extends n<we>
{
    public final Xb k;
    
    public nA(final Xb k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventPacket)event);
    }
    
    public void M(final EventPacket eventPacket) {
        if (eventPacket.M() instanceof SPacketPlayerPosLook) {
            Xb.M(this.k).clear();
            if (((Xb.bB)Xb.M(this.k).M()).equals((Object)Xb.bB.a)) {
                this.k.M(false);
            }
        }
    }
}
