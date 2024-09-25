/*
 * Decompiled with CFR 0.150.
 */
package skizzle.events.listeners;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import skizzle.events.Event;

public class EventAttack
extends Event<EventAttack> {
    public Entity attackedEntity;
    public Minecraft mc = Minecraft.getMinecraft();
    public ItemStack item;

    public EventAttack(Entity Nigga) {
        EventAttack Nigga2;
        Nigga2.attackedEntity = Nigga;
        Nigga2.item = Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem();
    }

    public static {
        throw throwable;
    }

    public ItemStack getItem() {
        EventAttack Nigga;
        for (int Nigga2 = 0; Nigga2 < 9; ++Nigga2) {
            ItemStack Nigga3 = Nigga.mc.thePlayer.inventory.getCurrentItem();
            ItemStack Nigga4 = Nigga.mc.thePlayer.inventory.getStackInSlot(Nigga2);
            if (Nigga4 == null || Nigga3 == null || !(Nigga4.getItem() instanceof ItemSword)) continue;
            ItemSword Nigga5 = (ItemSword)Nigga3.getItem();
            ItemSword Nigga6 = (ItemSword)Nigga4.getItem();
            if (!(Nigga6.damageAmount > Nigga5.damageAmount)) continue;
            Nigga.mc.thePlayer.inventory.currentItem = Nigga2;
            return Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(Nigga2);
        }
        return Nigga.mc.thePlayer.getHeldItem();
    }

    public void setItem(ItemStack Nigga) {
        Nigga.item = Nigga;
    }
}

