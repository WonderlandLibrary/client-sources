/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import java.util.List;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class GlassBottleItem
extends Item {
    public GlassBottleItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerEntity, Hand hand) {
        List<AreaEffectCloudEntity> list = world.getEntitiesWithinAABB(AreaEffectCloudEntity.class, playerEntity.getBoundingBox().grow(2.0), GlassBottleItem::lambda$onItemRightClick$0);
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        if (!list.isEmpty()) {
            AreaEffectCloudEntity areaEffectCloudEntity = list.get(0);
            areaEffectCloudEntity.setRadius(areaEffectCloudEntity.getRadius() - 0.5f);
            world.playSound(null, playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ(), SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, SoundCategory.NEUTRAL, 1.0f, 1.0f);
            return ActionResult.func_233538_a_(this.turnBottleIntoItem(itemStack, playerEntity, new ItemStack(Items.DRAGON_BREATH)), world.isRemote());
        }
        BlockRayTraceResult blockRayTraceResult = GlassBottleItem.rayTrace(world, playerEntity, RayTraceContext.FluidMode.SOURCE_ONLY);
        if (((RayTraceResult)blockRayTraceResult).getType() == RayTraceResult.Type.MISS) {
            return ActionResult.resultPass(itemStack);
        }
        if (((RayTraceResult)blockRayTraceResult).getType() == RayTraceResult.Type.BLOCK) {
            BlockPos blockPos = blockRayTraceResult.getPos();
            if (!world.isBlockModifiable(playerEntity, blockPos)) {
                return ActionResult.resultPass(itemStack);
            }
            if (world.getFluidState(blockPos).isTagged(FluidTags.WATER)) {
                world.playSound(playerEntity, playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0f, 1.0f);
                return ActionResult.func_233538_a_(this.turnBottleIntoItem(itemStack, playerEntity, PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER)), world.isRemote());
            }
        }
        return ActionResult.resultPass(itemStack);
    }

    protected ItemStack turnBottleIntoItem(ItemStack itemStack, PlayerEntity playerEntity, ItemStack itemStack2) {
        playerEntity.addStat(Stats.ITEM_USED.get(this));
        return DrinkHelper.fill(itemStack, playerEntity, itemStack2);
    }

    private static boolean lambda$onItemRightClick$0(AreaEffectCloudEntity areaEffectCloudEntity) {
        return areaEffectCloudEntity != null && areaEffectCloudEntity.isAlive() && areaEffectCloudEntity.getOwner() instanceof EnderDragonEntity;
    }
}

