/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.entity.IEquipable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;

public class SaddleItem
extends Item {
    public SaddleItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType itemInteractionForEntity(ItemStack itemStack, PlayerEntity playerEntity, LivingEntity livingEntity, Hand hand) {
        IEquipable iEquipable;
        if (livingEntity instanceof IEquipable && livingEntity.isAlive() && !(iEquipable = (IEquipable)((Object)livingEntity)).isHorseSaddled() && iEquipable.func_230264_L__()) {
            if (!playerEntity.world.isRemote) {
                iEquipable.func_230266_a_(SoundCategory.NEUTRAL);
                itemStack.shrink(1);
            }
            return ActionResultType.func_233537_a_(playerEntity.world.isRemote);
        }
        return ActionResultType.PASS;
    }
}

