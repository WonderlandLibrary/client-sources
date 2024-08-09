/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import mpp.venusfr.functions.api.FunctionRegistry;
import mpp.venusfr.functions.impl.player.ItemCooldown;
import mpp.venusfr.venusfr;
import net.minecraft.entity.item.EnderPearlEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class EnderPearlItem
extends Item {
    public EnderPearlItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        world.playSound(null, playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ(), SoundEvents.ENTITY_ENDER_PEARL_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (random.nextFloat() * 0.4f + 0.8f));
        playerEntity.getCooldownTracker().setCooldown(this, 20);
        FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
        ItemCooldown itemCooldown = functionRegistry.getItemCooldown();
        ItemCooldown.ItemEnum itemEnum = ItemCooldown.ItemEnum.getItemEnum(this);
        if (itemCooldown.isState() && itemEnum != null && itemCooldown.isCurrentItem(itemEnum)) {
            itemCooldown.lastUseItemTime.put(itemEnum.getItem(), System.currentTimeMillis());
        } else {
            playerEntity.getCooldownTracker().setCooldown(this, 20);
        }
        if (!world.isRemote) {
            EnderPearlEntity enderPearlEntity = new EnderPearlEntity(world, playerEntity);
            enderPearlEntity.setItem(itemStack);
            enderPearlEntity.func_234612_a_(playerEntity, playerEntity.rotationPitch, playerEntity.rotationYaw, 0.0f, 1.5f, 1.0f);
            world.addEntity(enderPearlEntity);
        }
        playerEntity.addStat(Stats.ITEM_USED.get(this));
        if (!playerEntity.abilities.isCreativeMode) {
            itemStack.shrink(1);
        }
        return ActionResult.func_233538_a_(itemStack, world.isRemote());
    }
}

