package net.futureclient.client.modules.combat.copsandcrims;

import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.futureclient.client.af;
import net.futureclient.client.Zf;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.combat.CopsAndCrims;
import net.futureclient.client.Ag;
import net.futureclient.client.n;

public class Listener1 extends n<Ag>
{
    public final CopsAndCrims k;
    
    public Listener1(final CopsAndCrims k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((Ag)event);
    }
    
    @Override
    public void M(final Ag ag) {
        if (CopsAndCrims.b(this.k).M()) {
            switch (Zf.k[((af.Pe)CopsAndCrims.M(this.k).M()).ordinal()]) {
                case 1:
                    if (ag.M() instanceof CPacketPlayerTryUseItem) {
                        CopsAndCrims.b(this.k);
                        return;
                    }
                    break;
                case 2:
                    if (ag.M() instanceof CPacketAnimation) {
                        CopsAndCrims.b(this.k);
                        break;
                    }
                    break;
            }
        }
    }
}
