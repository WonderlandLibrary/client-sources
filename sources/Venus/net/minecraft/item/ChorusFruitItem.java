/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ChorusFruitItem
extends Item {
    public ChorusFruitItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack itemStack, World world, LivingEntity livingEntity) {
        ItemStack itemStack2 = super.onItemUseFinish(itemStack, world, livingEntity);
        if (!world.isRemote) {
            double d = livingEntity.getPosX();
            double d2 = livingEntity.getPosY();
            double d3 = livingEntity.getPosZ();
            for (int i = 0; i < 16; ++i) {
                double d4 = livingEntity.getPosX() + (livingEntity.getRNG().nextDouble() - 0.5) * 16.0;
                double d5 = MathHelper.clamp(livingEntity.getPosY() + (double)(livingEntity.getRNG().nextInt(16) - 8), 0.0, (double)(world.func_234938_ad_() - 1));
                double d6 = livingEntity.getPosZ() + (livingEntity.getRNG().nextDouble() - 0.5) * 16.0;
                if (livingEntity.isPassenger()) {
                    livingEntity.stopRiding();
                }
                if (!livingEntity.attemptTeleport(d4, d5, d6, false)) continue;
                SoundEvent soundEvent = livingEntity instanceof FoxEntity ? SoundEvents.ENTITY_FOX_TELEPORT : SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
                world.playSound(null, d, d2, d3, soundEvent, SoundCategory.PLAYERS, 1.0f, 1.0f);
                livingEntity.playSound(soundEvent, 1.0f, 1.0f);
                break;
            }
            if (livingEntity instanceof PlayerEntity) {
                ((PlayerEntity)livingEntity).getCooldownTracker().setCooldown(this, 20);
            }
        }
        return itemStack2;
    }
}

