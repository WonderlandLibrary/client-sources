package net.futureclient.client.modules.movement.yaw;

import net.futureclient.client.events.Event;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.futureclient.client.RC;
import net.futureclient.client.pg;
import net.futureclient.client.modules.render.Waypoints;
import net.futureclient.client.events.EventUpdate;
import net.futureclient.client.modules.movement.Yaw;
import net.futureclient.client.lF;
import net.futureclient.client.n;

public class Listener1 extends n<lF>
{
    public final Yaw k;
    
    public Listener1(final Yaw k) {
        this.k = k;
        super();
    }
    
    public void M(final EventUpdate eventUpdate) {
        final Waypoints waypoints;
        if ((waypoints = (Waypoints)pg.M().M().M((Class)RC.class)) != null && waypoints.M() && waypoints.M() != null) {
            final Waypoints waypoints2 = waypoints;
            double b = waypoints2.M().b();
            double m = waypoints2.M().M();
            if (Yaw.getMinecraft5().world.provider.getDimensionType().equals((Object)DimensionType.NETHER) && waypoints.M().e().equals(DimensionType.OVERWORLD.getName())) {
                b /= 0.0;
                m /= 0.0;
            }
            else if (Yaw.getMinecraft4().world.provider.getDimensionType().equals((Object)DimensionType.OVERWORLD) && waypoints.M().e().equals(DimensionType.NETHER.getName())) {
                b *= 0.0;
                m *= 0.0;
            }
            Yaw.getMinecraft().player.rotationYaw = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(Yaw.getMinecraft2().player.posZ - m, Yaw.getMinecraft3().player.posX - b)) + 0.0);
            return;
        }
        Yaw.getMinecraft1().player.rotationYaw = Math.round((Yaw.getMinecraft6().player.rotationYaw + 1.0f) / 45.0f) * 45.0f;
    }
    
    public void M(final Event event) {
        this.M((EventUpdate)event);
    }
}
