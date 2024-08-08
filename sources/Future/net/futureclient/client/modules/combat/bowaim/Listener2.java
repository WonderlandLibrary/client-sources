package net.futureclient.client.modules.combat.bowaim;

import net.futureclient.client.events.Event;
import net.futureclient.client.ZG;
import net.minecraft.item.ItemBow;
import net.futureclient.client.modules.combat.BowAim;
import net.futureclient.client.xD;
import net.futureclient.client.n;

public class Listener2 extends n<xD>
{
    public final BowAim k;
    
    public Listener2(final BowAim k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final xD xd) {
        if (this.k.l != null && BowAim.getMinecraft().player.getActiveItemStack().getItem() instanceof ItemBow && this.k.A != 0.0f && this.k.K != 0.0f) {
            ZG.M(xd, this.k.K, this.k.A);
        }
    }
    
    public void M(final Event event) {
        this.M((xD)event);
    }
}
