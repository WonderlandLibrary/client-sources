/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class FishingRodItem
extends Item
implements IVanishable {
    public FishingRodItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        if (playerEntity.fishingBobber != null) {
            if (!world.isRemote) {
                int n = playerEntity.fishingBobber.handleHookRetraction(itemStack);
                itemStack.damageItem(n, playerEntity, arg_0 -> FishingRodItem.lambda$onItemRightClick$0(hand, arg_0));
            }
            world.playSound(null, playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ(), SoundEvents.ENTITY_FISHING_BOBBER_RETRIEVE, SoundCategory.NEUTRAL, 1.0f, 0.4f / (random.nextFloat() * 0.4f + 0.8f));
        } else {
            world.playSound(null, playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ(), SoundEvents.ENTITY_FISHING_BOBBER_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (random.nextFloat() * 0.4f + 0.8f));
            if (!world.isRemote) {
                int n = EnchantmentHelper.getFishingSpeedBonus(itemStack);
                int n2 = EnchantmentHelper.getFishingLuckBonus(itemStack);
                world.addEntity(new FishingBobberEntity(playerEntity, world, n2, n));
            }
            playerEntity.addStat(Stats.ITEM_USED.get(this));
        }
        return ActionResult.func_233538_a_(itemStack, world.isRemote());
    }

    @Override
    public int getItemEnchantability() {
        return 0;
    }

    private static void lambda$onItemRightClick$0(Hand hand, PlayerEntity playerEntity) {
        playerEntity.sendBreakAnimation(hand);
    }
}

