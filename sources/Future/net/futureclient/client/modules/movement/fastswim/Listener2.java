package net.futureclient.client.modules.movement.fastswim;

import net.futureclient.client.events.Event;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.futureclient.client.events.EventPacket;
import net.futureclient.client.modules.movement.FastSwim;
import net.futureclient.client.we;
import net.futureclient.client.n;

public class Listener2 extends n<we>
{
    public final FastSwim k;
    
    public Listener2(final FastSwim k) {
        this.k = k;
        super();
    }
    
    public void M(final EventPacket eventPacket) {
        if (eventPacket.M() instanceof SPacketPlayerPosLook) {
            FastSwim.M(this.k, false);
        }
    }
    
    public void M(final Event event) {
        this.M((EventPacket)event);
    }
}
