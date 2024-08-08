package net.futureclient.client.modules.movement.flight;

import net.futureclient.loader.mixin.common.network.packet.serverbound.wrapper.ICPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import net.futureclient.client.ZC;
import net.futureclient.client.AA;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.movement.Flight;
import net.futureclient.client.Ag;
import net.futureclient.client.n;

public class Listener4 extends n<Ag>
{
    public final Flight k;
    
    public Listener4(final Flight k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((Ag)event);
    }
    
    @Override
    public void M(final Ag ag) {
        switch (AA.k[((ZC.SB)Flight.M(this.k).M()).ordinal()]) {
            case 4:
                if (Flight.getMinecraft26().player.fallDistance > 3.8f && ag.M() instanceof CPacketPlayer) {
                    ((ICPacketPlayer)ag.M()).setOnGround(true);
                    Flight.getMinecraft24().player.fallDistance = 0.0f;
                    return;
                }
                break;
            case 5: {
                if (!(ag.M() instanceof CPacketPlayer)) {
                    break;
                }
                final CPacketPlayer cPacketPlayer = (CPacketPlayer)ag.M();
                if (Flight.M(this.k).M()) {
                    ((ICPacketPlayer)cPacketPlayer).setOnGround(false);
                    break;
                }
                break;
            }
        }
    }
}
