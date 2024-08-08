package net.futureclient.client.modules.combat.autosoup;

import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.item.ItemStack;
import net.futureclient.client.of;
import net.futureclient.client.pg;
import net.futureclient.client.modules.combat.AutoTotem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAir;
import net.futureclient.client.events.EventMotion;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.combat.AutoSoup;
import net.futureclient.client.KF;
import net.futureclient.client.n;

public class Listener1 extends n<KF>
{
    public final AutoSoup k;
    
    public Listener1(final AutoSoup k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventMotion)event);
    }
    
    public void M(final EventMotion eventMotion) {
        this.k.e(String.format("AutoSoup §7[§F%s§7]", this.k.e()));
        int i = 44;
        int n = 44;
        while (i >= 9) {
            final ItemStack stack;
            if (!((stack = AutoSoup.getMinecraft().player.inventoryContainer.getSlot(n).getStack()).getItem() instanceof ItemAir) && stack.getItem() == Items.BOWL && AutoSoup.M(this.k).M() && AutoSoup.M(this.k).e(100L)) {
                final PlayerControllerMP playerController = AutoSoup.getMinecraft2().playerController;
                final int n2 = n;
                final int n3 = 0;
                playerController.windowClick(n3, n2, n3, ClickType.THROW, (EntityPlayer)AutoSoup.getMinecraft3().player);
                AutoSoup.M(this.k).e();
            }
            i = --n;
        }
        if (AutoSoup.getMinecraft1().player.getHealth() < AutoSoup.M(this.k).B().floatValue() && !((AutoTotem)pg.M().M().M((Class)of.class)).k) {
            AutoSoup.M(this.k);
        }
    }
}
