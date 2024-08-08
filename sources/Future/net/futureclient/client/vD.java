package net.futureclient.client;

import net.futureclient.loader.mixin.common.entity.living.wrapper.IEntityLivingBase;
import net.minecraft.item.ItemFood;
import net.futureclient.client.events.Event;

public class vD extends n<xD>
{
    public final te k;
    
    public vD(final te k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((xD)event);
    }
    
    @Override
    public void M(final xD xd) {
        if (!this.k.k.M() && ((IEntityLivingBase)te.B().player).getActiveItemStack().getItem() instanceof ItemFood) {
            return;
        }
        if (te.M(this.k) != null && this.k.W.M()) {
            ZG.M(xd, te.e(this.k), te.M(this.k));
        }
    }
}
