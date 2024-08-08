package net.futureclient.client.modules.movement.jesus;

import net.futureclient.client.events.Event;
import net.futureclient.loader.mixin.common.network.packet.serverbound.wrapper.ICPacketPlayer;
import net.futureclient.client.IG;
import net.minecraft.network.play.client.CPacketPlayer;
import net.futureclient.client.Cb;
import net.futureclient.client.modules.movement.Jesus;
import net.futureclient.client.Ag;
import net.futureclient.client.n;

public class Listener4 extends n<Ag>
{
    public final Jesus k;
    
    public Listener4(final Jesus k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final Ag ag) {
        if (!Jesus.M(this.k).e(800L)) {
            return;
        }
        if (Jesus.M(this.k).M() == Cb.gA.d && ag.M() instanceof CPacketPlayer && !IG.e() && IG.M(true) && !IG.M()) {
            final CPacketPlayer cPacketPlayer;
            final ICPacketPlayer icPacketPlayer = (ICPacketPlayer)(cPacketPlayer = (CPacketPlayer)ag.M());
            double n;
            CPacketPlayer cPacketPlayer2;
            if (Jesus.getMinecraft12().player.ticksExisted % 2 == 0) {
                n = 5.941588215E-315;
                cPacketPlayer2 = cPacketPlayer;
            }
            else {
                n = 0.0;
                cPacketPlayer2 = cPacketPlayer;
            }
            icPacketPlayer.setY(n + cPacketPlayer2.getY(0.0));
        }
    }
    
    public void M(final Event event) {
        this.M((Ag)event);
    }
}
