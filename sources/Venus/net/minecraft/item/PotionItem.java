/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class PotionItem
extends Item {
    public PotionItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack getDefaultInstance() {
        return PotionUtils.addPotionToItemStack(super.getDefaultInstance(), Potions.WATER);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack itemStack, World world, LivingEntity livingEntity) {
        PlayerEntity playerEntity;
        PlayerEntity playerEntity2 = playerEntity = livingEntity instanceof PlayerEntity ? (PlayerEntity)livingEntity : null;
        if (playerEntity instanceof ServerPlayerEntity) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity)playerEntity, itemStack);
        }
        if (!world.isRemote) {
            for (EffectInstance effectInstance : PotionUtils.getEffectsFromStack(itemStack)) {
                if (effectInstance.getPotion().isInstant()) {
                    effectInstance.getPotion().affectEntity(playerEntity, playerEntity, livingEntity, effectInstance.getAmplifier(), 1.0);
                    continue;
                }
                livingEntity.addPotionEffect(new EffectInstance(effectInstance));
            }
        }
        if (playerEntity != null) {
            playerEntity.addStat(Stats.ITEM_USED.get(this));
            if (!playerEntity.abilities.isCreativeMode) {
                itemStack.shrink(1);
            }
        }
        if (playerEntity == null || !playerEntity.abilities.isCreativeMode) {
            if (itemStack.isEmpty()) {
                return new ItemStack(Items.GLASS_BOTTLE);
            }
            if (playerEntity != null) {
                playerEntity.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
        }
        return itemStack;
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return 1;
    }

    @Override
    public UseAction getUseAction(ItemStack itemStack) {
        return UseAction.DRINK;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerEntity, Hand hand) {
        return DrinkHelper.startDrinking(world, playerEntity, hand);
    }

    @Override
    public String getTranslationKey(ItemStack itemStack) {
        return PotionUtils.getPotionFromItem(itemStack).getNamePrefixed(this.getTranslationKey() + ".effect.");
    }

    @Override
    public void addInformation(ItemStack itemStack, @Nullable World world, List<ITextComponent> list, ITooltipFlag iTooltipFlag) {
        PotionUtils.addPotionTooltip(itemStack, list, 1.0f);
    }

    @Override
    public boolean hasEffect(ItemStack itemStack) {
        return super.hasEffect(itemStack) || !PotionUtils.getEffectsFromStack(itemStack).isEmpty();
    }

    @Override
    public void fillItemGroup(ItemGroup itemGroup, NonNullList<ItemStack> nonNullList) {
        if (this.isInGroup(itemGroup)) {
            for (Potion potion : Registry.POTION) {
                if (potion == Potions.EMPTY) continue;
                nonNullList.add(PotionUtils.addPotionToItemStack(new ItemStack(this), potion));
            }
        }
    }
}

