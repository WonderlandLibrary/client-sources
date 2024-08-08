package net.futureclient.client.modules.miscellaneous.xcarry;

import net.futureclient.client.events.Event;
import net.minecraft.network.Packet;
import net.futureclient.loader.mixin.common.network.packet.serverbound.wrapper.ICPacketCloseWindow;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.futureclient.client.modules.miscellaneous.XCarry;
import net.futureclient.client.Ag;
import net.futureclient.client.n;

public class Listener1 extends n<Ag>
{
    public final XCarry k;
    
    public Listener1(final XCarry k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final Ag ag) {
        final Packet<?> m;
        if ((m = ag.M()) instanceof CPacketCloseWindow) {
            final CPacketCloseWindow cPacketCloseWindow = (CPacketCloseWindow)m;
            ag.M(XCarry.M(this.k).M() || ((ICPacketCloseWindow)cPacketCloseWindow).getWindowId() == 0);
        }
    }
    
    public void M(final Event event) {
        this.M((Ag)event);
    }
}
