package net.futureclient.client.modules.miscellaneous.heaven;

import net.futureclient.client.events.Event;
import net.minecraft.util.math.AxisAlignedBB;
import net.futureclient.client.modules.miscellaneous.Heaven;
import net.futureclient.client.wE;
import net.futureclient.client.n;

public class Listener2 extends n<wE>
{
    public final Heaven k;
    
    public Listener2(final Heaven k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final wE we) {
        if (!Heaven.getMinecraft().player.isEntityAlive() && we.M() != null) {
            we.M((AxisAlignedBB)null);
        }
    }
    
    public void M(final Event event) {
        this.M((wE)event);
    }
}
