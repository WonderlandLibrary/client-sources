package net.futureclient.client;

import net.futureclient.client.events.Event;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemAir;
import net.minecraft.network.play.server.SPacketEntityEquipment;
import net.futureclient.client.events.EventPacket;

public class Vf extends n<we>
{
    public final te k;
    
    public Vf(final te k) {
        this.k = k;
        super();
    }
    
    public void M(final EventPacket eventPacket) {
        final SPacketEntityEquipment sPacketEntityEquipment;
        if (eventPacket.M() instanceof SPacketEntityEquipment && (sPacketEntityEquipment = (SPacketEntityEquipment)eventPacket.M()).getEquipmentSlot().getIndex() == 1 && sPacketEntityEquipment.getItemStack().getItem() instanceof ItemAir && !(te.i().player.getHeldItemOffhand().getItem() instanceof ItemAir) && te.M().player.getHeldItemOffhand().getItem() instanceof ItemShield && this.k.M()) {
            eventPacket.M(true);
        }
    }
    
    public void M(final Event event) {
        this.M((EventPacket)event);
    }
}
