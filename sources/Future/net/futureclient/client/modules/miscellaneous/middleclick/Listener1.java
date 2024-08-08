package net.futureclient.client.modules.miscellaneous.middleclick;

import net.futureclient.client.rD;
import net.futureclient.client.pg;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.miscellaneous.MiddleClick;
import net.futureclient.client.IE;
import net.futureclient.client.n;

public class Listener1 extends n<IE>
{
    public final MiddleClick k;
    
    public Listener1(final MiddleClick k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((IE)event);
    }
    
    @Override
    public void M(final IE ie) {
        if (ie.M().equals(IE.RD.k) && MiddleClick.getMinecraft3().objectMouseOver != null && MiddleClick.getMinecraft().objectMouseOver.typeOfHit.equals((Object)RayTraceResult.Type.ENTITY) && MiddleClick.getMinecraft2().objectMouseOver.entityHit instanceof EntityPlayer) {
            final EntityPlayer entityPlayer = (EntityPlayer)MiddleClick.getMinecraft1().objectMouseOver.entityHit;
            if (pg.M().M().M(entityPlayer.getName())) {
                pg.M().M().M(pg.M().M().M(entityPlayer.getName()));
                return;
            }
            pg.M().M().e(new rD(entityPlayer.getName(), entityPlayer.getName()));
        }
    }
}
