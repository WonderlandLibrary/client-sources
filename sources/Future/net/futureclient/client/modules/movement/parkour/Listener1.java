package net.futureclient.client.modules.movement.parkour;

import net.futureclient.client.events.Event;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.futureclient.client.Pc;
import net.futureclient.client.events.EventMotion;
import net.futureclient.client.modules.movement.Parkour;
import net.futureclient.client.KF;
import net.futureclient.client.n;

public class Listener1 extends n<KF>
{
    public final Parkour k;
    
    public Listener1(final Parkour k) {
        this.k = k;
        super();
    }
    
    public void M(final EventMotion eventMotion) {
        switch (Pc.k[eventMotion.M().ordinal()]) {
            case 1: {
                if (!Parkour.getMinecraft3().player.onGround || Parkour.getMinecraft().player.isSneaking()) {
                    break;
                }
                final WorldClient world = Parkour.getMinecraft4().world;
                final EntityPlayerSP player = Parkour.getMinecraft5().player;
                final AxisAlignedBB entityBoundingBox = Parkour.getMinecraft2().player.getEntityBoundingBox();
                final double n = 0.0;
                final double n2 = 0.0;
                if (world.getCollisionBoxes((Entity)player, entityBoundingBox.offset(n2, n, n2).shrink(1.748524532E-314)).isEmpty()) {
                    Parkour.getMinecraft1().player.jump();
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
