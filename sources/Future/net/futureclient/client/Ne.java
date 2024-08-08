package net.futureclient.client;

import net.futureclient.client.events.Event;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.futureclient.client.events.EventType2;
import net.futureclient.client.events.EventMotion;

public class Ne extends n<KF>
{
    public final Xe k;
    
    public Ne(final Xe k) {
        this.k = k;
        super();
    }
    
    public void M(final EventMotion eventMotion) {
        if (eventMotion.M() != EventType2.PRE) {
            return;
        }
        final ItemStack m;
        if ((m = ZG.M((Class)ItemBow.class)) != null && m.getMaxItemUseDuration() - Xe.getMinecraft1().player.getItemInUseCount() - (20.0f - pg.M().M().M()) >= Xe.M(this.k).B().floatValue()) {
            Xe.getMinecraft().playerController.onStoppedUsingItem((EntityPlayer)Xe.getMinecraft2().player);
        }
    }
    
    public void M(final Event event) {
        this.M((EventMotion)event);
    }
}
