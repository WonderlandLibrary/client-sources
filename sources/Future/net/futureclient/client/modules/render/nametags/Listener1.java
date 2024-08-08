package net.futureclient.client.modules.render.nametags;

import net.minecraft.util.math.Vec3d;
import java.util.Iterator;
import net.futureclient.client.xG;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.futureclient.client.ZG;
import net.futureclient.client.events.EventType;
import net.futureclient.client.events.EventRender;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.render.Nametags;
import net.futureclient.client.KD;
import net.futureclient.client.n;

public class Listener1 extends n<KD>
{
    public final Nametags k;
    
    public Listener1(final Nametags k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventRender)event);
    }
    
    public void M(final EventRender eventRender) {
        if (!eventRender.getType().equals(EventType.PRE)) {
            return;
        }
        final Iterator<EntityPlayer> iterator = ZG.M().iterator();
    Label_0024:
        while (true) {
            Iterator<EntityPlayer> iterator2 = iterator;
            while (iterator2.hasNext()) {
                final EntityPlayer entityPlayer;
                if (!ZG.b((Entity)(entityPlayer = iterator.next())) || entityPlayer instanceof EntityPlayerSP) {
                    continue Label_0024;
                }
                if (entityPlayer.isInvisible() && !Nametags.M(this.k).M()) {
                    iterator2 = iterator;
                }
                else {
                    final Vec3d m = xG.M((Entity)entityPlayer);
                    iterator2 = iterator;
                    Nametags.M(this.k, entityPlayer, m.x, m.y, m.z);
                }
            }
            break;
        }
    }
}
