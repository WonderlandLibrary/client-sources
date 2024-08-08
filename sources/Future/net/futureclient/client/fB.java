package net.futureclient.client;

import net.futureclient.client.events.Event;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketPlayer;

public class fB extends n<Ag>
{
    public final Xb k;
    
    public fB(final Xb k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final Ag ag) {
        switch (zA.k[((Xb.bB)Xb.M(this.k).M()).ordinal()]) {
            case 1:
                if (ag.M() instanceof CPacketPlayer || ag.M() instanceof CPacketPlayerTryUseItemOnBlock || ag.M() instanceof CPacketPlayerDigging || ag.M() instanceof CPacketUseEntity) {
                    Xb.M(this.k).add(ag.M());
                    ag.M(true);
                    return;
                }
                break;
            case 2:
                if (!Xb.M(this.k) && ag.M() instanceof CPacketPlayer && !Xb.M(this.k).contains(ag.M())) {
                    Xb.M(this.k).add(ag.M());
                    ag.M(true);
                    break;
                }
                break;
        }
    }
    
    public void M(final Event event) {
        this.M((Ag)event);
    }
}
