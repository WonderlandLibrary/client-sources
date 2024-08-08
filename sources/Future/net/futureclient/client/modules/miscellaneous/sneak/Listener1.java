package net.futureclient.client.modules.miscellaneous.sneak;

import net.futureclient.client.events.Event;
import net.futureclient.client.vF;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.futureclient.client.events.EventMotion;
import net.futureclient.client.modules.miscellaneous.Sneak;
import net.futureclient.client.KF;
import net.futureclient.client.n;

public class Listener1 extends n<KF>
{
    public final Sneak k;
    
    public Listener1(final Sneak k) {
        this.k = k;
        super();
    }
    
    public void M(final EventMotion eventMotion) {
        if (Sneak.M(this.k).M()) {
            if (Sneak.M(this.k) && !Sneak.getMinecraft().gameSettings.keyBindSneak.isKeyDown()) {
                Sneak.getMinecraft3().player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Sneak.getMinecraft15().player, CPacketEntityAction.Action.STOP_SNEAKING));
                Sneak.M(this.k, false);
            }
            return;
        }
        if (Sneak.getMinecraft18().player.motionX == 0.0 || Sneak.getMinecraft11().player.motionZ == 0.0) {
            Sneak.getMinecraft1().player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Sneak.getMinecraft13().player, CPacketEntityAction.Action.START_SNEAKING));
            Sneak.M(this.k, true);
            return;
        }
        switch (vF.k[eventMotion.M().ordinal()]) {
            case 1:
                Sneak.getMinecraft7().player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Sneak.getMinecraft10().player, CPacketEntityAction.Action.START_SNEAKING));
                Sneak.getMinecraft12().player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Sneak.getMinecraft2().player, CPacketEntityAction.Action.START_SNEAKING));
                Sneak.getMinecraft17().player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Sneak.getMinecraft9().player, CPacketEntityAction.Action.STOP_SNEAKING));
            case 2:
                Sneak.getMinecraft16().player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Sneak.getMinecraft4().player, CPacketEntityAction.Action.START_SNEAKING));
                Sneak.getMinecraft6().player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Sneak.getMinecraft5().player, CPacketEntityAction.Action.START_SNEAKING));
                Sneak.M(this.k, true);
                break;
        }
    }
    
    public void M(final Event event) {
        this.M((EventMotion)event);
    }
}
