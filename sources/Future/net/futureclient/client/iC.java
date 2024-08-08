package net.futureclient.client;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import net.futureclient.loader.mixin.common.entity.living.player.wrapper.IEntityPlayerSP;
import net.futureclient.client.modules.render.Freecam;
import net.futureclient.client.modules.movement.Speed;
import net.futureclient.client.events.EventType2;
import net.futureclient.client.events.EventMotion;
import net.futureclient.client.events.Event;

public class iC extends n<KF>
{
    public final yb k;
    
    public iC(final yb k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventMotion)event);
    }
    
    public void M(final EventMotion eventMotion) {
        if (!eventMotion.M().equals(EventType2.POST)) {
            return;
        }
        this.k.e(String.format("Step §7[§F%s§7]", this.k.mode.M()));
        final Speed speed = (Speed)pg.M().M().M((Class)db.class);
        final Freecam freecam;
        if ((freecam = (Freecam)pg.M().M().M((Class)dd.class)) != null && freecam.M()) {
            return;
        }
        if (speed != null && speed.M() && !((db.fC)speed.mode.M()).equals((Object)db.fC.K) && !((db.fC)speed.mode.M()).equals((Object)db.fC.E) && !((db.fC)speed.mode.M()).equals((Object)db.fC.A)) {
            return;
        }
        if (this.k.M.M() && freecam != null && !freecam.M() && ((IEntityPlayerSP)yb.getMinecraft5().player).isPrevOnGround() && !yb.getMinecraft().player.onGround && yb.getMinecraft7().player.motionY <= 0.0) {
            final World world = yb.getMinecraft16().player.world;
            final EntityPlayerSP player = yb.getMinecraft1().player;
            final AxisAlignedBB entityBoundingBox = yb.getMinecraft3().player.getEntityBoundingBox();
            final double n = 1.867356296E-314;
            final double n2 = 0.0;
            if (!world.getCollisionBoxes((Entity)player, entityBoundingBox.offset(n2, n, n2)).isEmpty() && !IG.e() && !yb.getMinecraft18().player.isInWater() && yb.M(this.k).e(1000L)) {
                yb.getMinecraft11().player.motionY = 0.0;
            }
        }
    }
}
