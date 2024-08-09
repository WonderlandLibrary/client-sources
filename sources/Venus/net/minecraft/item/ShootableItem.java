/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import java.util.function.Predicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Hand;

public abstract class ShootableItem
extends Item {
    public static final Predicate<ItemStack> ARROWS = ShootableItem::lambda$static$0;
    public static final Predicate<ItemStack> ARROWS_OR_FIREWORKS = ARROWS.or(ShootableItem::lambda$static$1);

    public ShootableItem(Item.Properties properties) {
        super(properties);
    }

    public Predicate<ItemStack> getAmmoPredicate() {
        return this.getInventoryAmmoPredicate();
    }

    public abstract Predicate<ItemStack> getInventoryAmmoPredicate();

    public static ItemStack getHeldAmmo(LivingEntity livingEntity, Predicate<ItemStack> predicate) {
        if (predicate.test(livingEntity.getHeldItem(Hand.OFF_HAND))) {
            return livingEntity.getHeldItem(Hand.OFF_HAND);
        }
        return predicate.test(livingEntity.getHeldItem(Hand.MAIN_HAND)) ? livingEntity.getHeldItem(Hand.MAIN_HAND) : ItemStack.EMPTY;
    }

    @Override
    public int getItemEnchantability() {
        return 0;
    }

    public abstract int func_230305_d_();

    private static boolean lambda$static$1(ItemStack itemStack) {
        return itemStack.getItem() == Items.FIREWORK_ROCKET;
    }

    private static boolean lambda$static$0(ItemStack itemStack) {
        return itemStack.getItem().isIn(ItemTags.ARROWS);
    }
}

