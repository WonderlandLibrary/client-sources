package net.futureclient.client;

import net.minecraft.network.play.server.SPacketSoundEffect;
import net.futureclient.client.events.EventPacket;
import net.futureclient.client.events.Event;

public class Cd extends n<we>
{
    public final Fc k;
    
    public Cd(final Fc k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventPacket)event);
    }
    
    public void M(final EventPacket eventPacket) {
        if (eventPacket.M() instanceof SPacketSoundEffect && Fc.M(this.k).contains(((SPacketSoundEffect)eventPacket.M()).getSound())) {
            eventPacket.M(true);
        }
    }
}
