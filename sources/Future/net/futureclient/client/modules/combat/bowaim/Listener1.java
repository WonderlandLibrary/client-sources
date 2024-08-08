package net.futureclient.client.modules.combat.bowaim;

import net.futureclient.client.events.Event;
import net.minecraft.item.ItemBow;
import net.futureclient.client.he;
import net.futureclient.client.events.EventMotion;
import net.futureclient.client.modules.combat.BowAim;
import net.futureclient.client.KF;
import net.futureclient.client.n;

public class Listener1 extends n<KF>
{
    public final BowAim k;
    
    public Listener1(final BowAim k) {
        this.k = k;
        super();
    }
    
    public void M(final EventMotion eventMotion) {
        switch (he.k[eventMotion.M().ordinal()]) {
            case 1:
                this.k.l = BowAim.M(this.k, eventMotion, 360.0f);
                if (this.k.l != null && BowAim.getMinecraft1().player.getActiveItemStack().getItem() instanceof ItemBow) {
                    final float n = (BowAim.getMinecraft3().player.getActiveItemStack().getMaxItemUseDuration() - BowAim.getMinecraft2().player.getItemInUseCount()) / 20.0f;
                    float n2;
                    if ((n2 = (n * n + n * 2.0f) / 3.0f) >= 1.0f) {
                        n2 = 1.0f;
                    }
                    BowAim.M(this.k, eventMotion, n2 * 3.0f, 1.3262473694E-314);
                    break;
                }
                break;
        }
    }
    
    public void M(final Event event) {
        this.M((EventMotion)event);
    }
}
