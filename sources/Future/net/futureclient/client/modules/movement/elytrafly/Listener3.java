package net.futureclient.client.modules.movement.elytrafly;

import net.futureclient.client.dd;
import net.futureclient.client.pg;
import net.futureclient.client.modules.render.Freecam;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.futureclient.client.Fb;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.futureclient.client.events.EventPacket;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.movement.ElytraFly;
import net.futureclient.client.we;
import net.futureclient.client.n;

public class Listener3 extends n<we>
{
    public final ElytraFly k;
    
    public Listener3(final ElytraFly k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventPacket)event);
    }
    
    public void M(final EventPacket eventPacket) {
        if (eventPacket.M() instanceof SPacketPlayerPosLook && this.k.mode.M().equals((Object)Fb.RB.D)) {
            if (ElytraFly.getMinecraft9().player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() != Items.ELYTRA) {
                return;
            }
            final Freecam freecam;
            if ((freecam = (Freecam)pg.M().M().M((Class)dd.class)) != null && freecam.M()) {
                return;
            }
            ElytraFly.M(this.k, true);
        }
    }
}
