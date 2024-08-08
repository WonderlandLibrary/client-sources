package net.futureclient.client.modules.movement.noslow;

import net.futureclient.client.events.Event;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.futureclient.client.events.EventPacket;
import net.futureclient.client.modules.movement.NoSlow;
import net.futureclient.client.we;
import net.futureclient.client.n;

public class Listener7 extends n<we>
{
    public final NoSlow k;
    
    public Listener7(final NoSlow k) {
        this.k = k;
        super();
    }
    
    public void M(final EventPacket eventPacket) {
        if (eventPacket.M() instanceof SPacketPlayerPosLook) {
            NoSlow.M(this.k).e();
        }
    }
    
    public void M(final Event event) {
        this.M((EventPacket)event);
    }
}
