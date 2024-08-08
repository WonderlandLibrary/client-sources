package net.futureclient.client;

import net.futureclient.client.events.Event;
import net.futureclient.loader.mixin.common.entity.living.wrapper.IEntityLivingBase;
import net.minecraft.item.ItemFood;

public class lD extends n<sf>
{
    public final te k;
    
    public lD(final te k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final sf sf) {
        this.k.e(String.format("Aura §7[§F%s§7]", this.k.s.M()));
        if (!((te.sF)te.M(this.k).M()).equals((Object)te.sF.a) && te.C().player.getHealth() <= 0.0f) {
            this.k.M(false);
        }
        if (!this.k.k.M() && ((IEntityLivingBase)te.e().player).getActiveItemStack().getItem() instanceof ItemFood) {
            return;
        }
        switch (We.a[sf.M().ordinal()]) {
            case 1:
                te.M(this.k, (Event)sf);
            case 2:
                te.M(this.k);
                break;
        }
    }
    
    public void M(final Event event) {
        this.M((sf)event);
    }
}
