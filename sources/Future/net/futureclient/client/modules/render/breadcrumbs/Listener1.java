package net.futureclient.client.modules.render.breadcrumbs;

import net.futureclient.client.events.Event;
import net.futureclient.client.Uh;
import net.minecraft.util.math.Vec3d;
import java.util.List;
import net.futureclient.client.mG;
import java.util.ArrayList;
import net.futureclient.client.oh;
import net.futureclient.client.dd;
import net.futureclient.client.pg;
import net.futureclient.client.modules.render.Freecam;
import net.futureclient.client.events.EventUpdate;
import net.futureclient.client.modules.render.BreadCrumbs;
import net.futureclient.client.lF;
import net.futureclient.client.n;

public class Listener1 extends n<lF>
{
    public final BreadCrumbs k;
    
    public Listener1(final BreadCrumbs k) {
        this.k = k;
        super();
    }
    
    public void M(final EventUpdate eventUpdate) {
        final Freecam freecam;
        if ((freecam = (Freecam)pg.M().M().M((Class)dd.class)) != null && freecam.M()) {
            return;
        }
        if (BreadCrumbs.M(this.k).e(BreadCrumbs.M(this.k).B().floatValue() * 1000.0f)) {
            final Vec3d positionVector;
            if ((positionVector = BreadCrumbs.getMinecraft2().player.getPositionVector()).equals((Object)oh.k)) {
                return;
            }
            if (BreadCrumbs.M(this.k) == null) {
                BreadCrumbs.M(this.k, new mG(0, (String)null, BreadCrumbs.getMinecraft6().world.provider.getDimensionType(), positionVector, (List)new ArrayList()));
            }
            final Vec3d vec3d = BreadCrumbs.M(this.k).M().isEmpty() ? positionVector : BreadCrumbs.M(this.k).M().get(BreadCrumbs.M(this.k).M().size() - 1);
            if (!BreadCrumbs.M(this.k).M().isEmpty() && (Uh.M(positionVector, vec3d) > 0.0 || BreadCrumbs.M(this.k).M() != BreadCrumbs.getMinecraft1().world.provider.getDimensionType())) {
                BreadCrumbs.M(this.k).add(BreadCrumbs.M(this.k));
                BreadCrumbs.M(this.k, new mG(BreadCrumbs.M(this.k).size() + 1, (String)null, BreadCrumbs.getMinecraft8().world.provider.getDimensionType(), positionVector, (List)new ArrayList()));
            }
            if (BreadCrumbs.M(this.k).M().isEmpty() || !positionVector.equals((Object)vec3d)) {
                BreadCrumbs.M(this.k).M().add(positionVector);
            }
            BreadCrumbs.M(this.k).e();
        }
    }
    
    public void M(final Event event) {
        this.M((EventUpdate)event);
    }
}
