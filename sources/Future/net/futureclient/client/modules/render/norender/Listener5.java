package net.futureclient.client.modules.render.norender;

import net.futureclient.client.events.Event;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.futureclient.client.oC;
import net.minecraft.init.MobEffects;
import net.futureclient.client.events.EventUpdate;
import net.futureclient.client.modules.render.NoRender;
import net.futureclient.client.lF;
import net.futureclient.client.n;

public class Listener5 extends n<lF>
{
    public final NoRender k;
    
    public Listener5(final NoRender k) {
        this.k = k;
        super();
    }
    
    public void M(final EventUpdate eventUpdate) {
        if (this.k.blindness.M()) {
            NoRender.getMinecraft4().player.removeActivePotionEffect(MobEffects.NAUSEA);
            if (NoRender.getMinecraft1().player.isPotionActive(MobEffects.BLINDNESS)) {
                NoRender.getMinecraft2().player.removePotionEffect(MobEffects.BLINDNESS);
            }
        }
        if (((oC.YA)NoRender.M(this.k).M()).equals((Object)oC.YA.a)) {
            final Iterator<Entity> iterator2;
            Iterator<Entity> iterator = iterator2 = NoRender.getMinecraft().world.loadedEntityList.iterator();
            while (iterator.hasNext()) {
                final Entity entity;
                if (!((entity = iterator2.next()) instanceof EntityItem)) {
                    iterator = iterator2;
                }
                else {
                    NoRender.getMinecraft3().world.removeEntity(entity);
                    iterator = iterator2;
                }
            }
        }
    }
    
    public void M(final Event event) {
        this.M((EventUpdate)event);
    }
}
