package net.futureclient.client.modules.movement.elytrafly;

import net.futureclient.client.events.Event;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.futureclient.client.Fb;
import net.futureclient.client.dd;
import net.futureclient.client.pg;
import net.futureclient.client.modules.render.Freecam;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.futureclient.client.lb;
import net.futureclient.client.events.EventMotion;
import net.futureclient.client.modules.movement.ElytraFly;
import net.futureclient.client.KF;
import net.futureclient.client.n;

public class Listener2 extends n<KF>
{
    public final ElytraFly k;
    
    public Listener2(final ElytraFly k) {
        this.k = k;
        super();
    }
    
    public void M(final EventMotion eventMotion) {
        switch (lb.k[eventMotion.M().ordinal()]) {
            case 1: {
                this.k.e(String.format("ElytraFly §7[§F%s§7]", this.k.mode.M()));
                if (ElytraFly.getMinecraft10().player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() != Items.ELYTRA) {
                    return;
                }
                final Freecam freecam;
                if ((freecam = (Freecam)pg.M().M().M((Class)dd.class)) != null && freecam.M()) {
                    return;
                }
                if (this.k.mode.M() == Fb.RB.D && (!ElytraFly.getMinecraft20().player.onGround || !this.k.K.M())) {
                    if (ElytraFly.getMinecraft8().world == null) {
                        this.k.M(false);
                    }
                    if (!ElytraFly.getMinecraft5().gameSettings.keyBindSneak.isKeyDown()) {
                        ElytraFly.getMinecraft13().player.motionY = 0.0;
                    }
                    ElytraFly.getMinecraft35().player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)ElytraFly.getMinecraft23().player, CPacketEntityAction.Action.START_FALL_FLYING));
                    break;
                }
                break;
            }
        }
    }
    
    public void M(final Event event) {
        this.M((EventMotion)event);
    }
}
