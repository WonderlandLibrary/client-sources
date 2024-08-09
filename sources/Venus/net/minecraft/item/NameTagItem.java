/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;

public class NameTagItem
extends Item {
    public NameTagItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType itemInteractionForEntity(ItemStack itemStack, PlayerEntity playerEntity, LivingEntity livingEntity, Hand hand) {
        if (itemStack.hasDisplayName() && !(livingEntity instanceof PlayerEntity)) {
            if (!playerEntity.world.isRemote && livingEntity.isAlive()) {
                livingEntity.setCustomName(itemStack.getDisplayName());
                if (livingEntity instanceof MobEntity) {
                    ((MobEntity)livingEntity).enablePersistence();
                }
                itemStack.shrink(1);
            }
            return ActionResultType.func_233537_a_(playerEntity.world.isRemote);
        }
        return ActionResultType.PASS;
    }
}

