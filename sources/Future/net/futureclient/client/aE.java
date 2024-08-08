package net.futureclient.client;

import net.futureclient.client.events.Event;
import net.futureclient.loader.mixin.common.entity.living.wrapper.IEntityLivingBase;
import net.minecraft.item.ItemFood;
import net.futureclient.client.events.EventMotion;

public class aE extends n<KF>
{
    public final te k;
    
    public aE(final te k) {
        this.k = k;
        super();
    }
    
    public void M(final EventMotion eventMotion) {
        this.k.e(String.format("Aura §7[§F%s§7]", this.k.s.M()));
        if (!((te.sF)te.M(this.k).M()).equals((Object)te.sF.a) && te.b().player.getHealth() <= 0.0f) {
            this.k.M(false);
        }
        if (!this.k.k.M() && ((IEntityLivingBase)te.h().player).getActiveItemStack().getItem() instanceof ItemFood) {
            return;
        }
        switch (We.k[eventMotion.M().ordinal()]) {
            case 1:
                te.M(this.k, (Event)eventMotion);
            case 2:
                te.M(this.k);
                break;
        }
    }
    
    public void M(final Event event) {
        this.M((EventMotion)event);
    }
}
