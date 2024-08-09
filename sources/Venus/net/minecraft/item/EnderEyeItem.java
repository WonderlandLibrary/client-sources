/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.EndPortalFrameBlock;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.EyeOfEnderEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.server.ServerWorld;

public class EnderEyeItem
extends Item {
    public EnderEyeItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext itemUseContext) {
        BlockPos blockPos;
        World world = itemUseContext.getWorld();
        BlockState blockState = world.getBlockState(blockPos = itemUseContext.getPos());
        if (blockState.isIn(Blocks.END_PORTAL_FRAME) && !blockState.get(EndPortalFrameBlock.EYE).booleanValue()) {
            if (world.isRemote) {
                return ActionResultType.SUCCESS;
            }
            BlockState blockState2 = (BlockState)blockState.with(EndPortalFrameBlock.EYE, true);
            Block.nudgeEntitiesWithNewState(blockState, blockState2, world, blockPos);
            world.setBlockState(blockPos, blockState2, 1);
            world.updateComparatorOutputLevel(blockPos, Blocks.END_PORTAL_FRAME);
            itemUseContext.getItem().shrink(1);
            world.playEvent(1503, blockPos, 0);
            BlockPattern.PatternHelper patternHelper = EndPortalFrameBlock.getOrCreatePortalShape().match(world, blockPos);
            if (patternHelper != null) {
                BlockPos blockPos2 = patternHelper.getFrontTopLeft().add(-3, 0, -3);
                for (int i = 0; i < 3; ++i) {
                    for (int j = 0; j < 3; ++j) {
                        world.setBlockState(blockPos2.add(i, 0, j), Blocks.END_PORTAL.getDefaultState(), 1);
                    }
                }
                world.playBroadcastSound(1038, blockPos2.add(1, 0, 1), 0);
            }
            return ActionResultType.CONSUME;
        }
        return ActionResultType.PASS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerEntity, Hand hand) {
        BlockPos blockPos;
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        BlockRayTraceResult blockRayTraceResult = EnderEyeItem.rayTrace(world, playerEntity, RayTraceContext.FluidMode.NONE);
        if (((RayTraceResult)blockRayTraceResult).getType() == RayTraceResult.Type.BLOCK && world.getBlockState(blockRayTraceResult.getPos()).isIn(Blocks.END_PORTAL_FRAME)) {
            return ActionResult.resultPass(itemStack);
        }
        playerEntity.setActiveHand(hand);
        if (world instanceof ServerWorld && (blockPos = ((ServerWorld)world).getChunkProvider().getChunkGenerator().func_235956_a_((ServerWorld)world, Structure.field_236375_k_, playerEntity.getPosition(), 100, true)) != null) {
            EyeOfEnderEntity eyeOfEnderEntity = new EyeOfEnderEntity(world, playerEntity.getPosX(), playerEntity.getPosYHeight(0.5), playerEntity.getPosZ());
            eyeOfEnderEntity.func_213863_b(itemStack);
            eyeOfEnderEntity.moveTowards(blockPos);
            world.addEntity(eyeOfEnderEntity);
            if (playerEntity instanceof ServerPlayerEntity) {
                CriteriaTriggers.USED_ENDER_EYE.trigger((ServerPlayerEntity)playerEntity, blockPos);
            }
            world.playSound(null, playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ(), SoundEvents.ENTITY_ENDER_EYE_LAUNCH, SoundCategory.NEUTRAL, 0.5f, 0.4f / (random.nextFloat() * 0.4f + 0.8f));
            world.playEvent(null, 1003, playerEntity.getPosition(), 0);
            if (!playerEntity.abilities.isCreativeMode) {
                itemStack.shrink(1);
            }
            playerEntity.addStat(Stats.ITEM_USED.get(this));
            playerEntity.swing(hand, false);
            return ActionResult.resultSuccess(itemStack);
        }
        return ActionResult.resultConsume(itemStack);
    }
}

