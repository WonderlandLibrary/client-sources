/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ArrowItem
extends Item {
    public ArrowItem(Item.Properties properties) {
        super(properties);
    }

    public AbstractArrowEntity createArrow(World world, ItemStack itemStack, LivingEntity livingEntity) {
        ArrowEntity arrowEntity = new ArrowEntity(world, livingEntity);
        arrowEntity.setPotionEffect(itemStack);
        return arrowEntity;
    }
}

