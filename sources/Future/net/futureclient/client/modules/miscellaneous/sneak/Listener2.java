package net.futureclient.client.modules.miscellaneous.sneak;

import net.futureclient.client.events.Event;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.futureclient.client.modules.miscellaneous.Sneak;
import net.futureclient.client.Ag;
import net.futureclient.client.n;

public class Listener2 extends n<Ag>
{
    public final Sneak k;
    
    public Listener2(final Sneak k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final Ag ag) {
        if (!Sneak.M(this.k).M() && ag.M() instanceof CPacketPlayerTryUseItemOnBlock) {
            Sneak.getMinecraft14().player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Sneak.getMinecraft8().player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
    }
    
    public void M(final Event event) {
        this.M((Ag)event);
    }
}
