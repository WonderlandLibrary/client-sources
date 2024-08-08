package net.futureclient.client.modules.world.avoid;

import net.futureclient.client.events.Event;
import net.futureclient.client.IG;
import net.minecraft.block.material.Material;
import net.futureclient.client.modules.world.Avoid;
import net.futureclient.client.wE;
import net.futureclient.client.n;

public class Listener1 extends n<wE>
{
    public final Avoid k;
    
    public Listener1(final Avoid k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final wE we) {
        final Material material = Avoid.getMinecraft().world.getBlockState(we.M()).getMaterial();
        if ((Avoid.b(this.k).M() && material.equals(Material.FIRE)) || (Avoid.e(this.k).M() && material.equals(Material.CACTUS)) || (Avoid.M(this.k).M() && !Avoid.getMinecraft1().world.isBlockLoaded(we.M(), false))) {
            we.M(IG.M(we.M().getX(), we.M().getY(), we.M().getZ(), we.M().getX() + 1.0, we.M().getY() + 1.0, we.M().getZ() + 1.0));
        }
    }
    
    public void M(final Event event) {
        this.M((wE)event);
    }
}
