package net.futureclient.client.modules.world.scaffold;

import net.futureclient.client.events.Event;
import net.futureclient.loader.mixin.common.network.packet.serverbound.wrapper.ICPacketPlayer;
import net.futureclient.client.ZH;
import net.minecraft.network.play.client.CPacketPlayer;
import net.futureclient.client.modules.world.Scaffold;
import net.futureclient.client.Ag;
import net.futureclient.client.n;

public class Listener3 extends n<Ag>
{
    public final Scaffold k;
    
    public Listener3(final Scaffold k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final Ag ag) {
        if (ag.M() instanceof CPacketPlayer && this.k.j != null) {
            final CPacketPlayer cPacketPlayer = (CPacketPlayer)ag.M();
            final float[] m = ZH.M(this.k.j.k, this.k.j.D);
            if (((ICPacketPlayer)cPacketPlayer).isRotating()) {
                ((ICPacketPlayer)cPacketPlayer).setYaw(m[0]);
                ((ICPacketPlayer)cPacketPlayer).setPitch(ZH.M(m[1]));
            }
        }
    }
    
    public void M(final Event event) {
        this.M((Ag)event);
    }
}
