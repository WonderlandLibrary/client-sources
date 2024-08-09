/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BucketItem
extends Item {
    private final Fluid containedBlock;

    public BucketItem(Fluid fluid, Item.Properties properties) {
        super(properties);
        this.containedBlock = fluid;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        BlockRayTraceResult blockRayTraceResult = BucketItem.rayTrace(world, playerEntity, this.containedBlock == Fluids.EMPTY ? RayTraceContext.FluidMode.SOURCE_ONLY : RayTraceContext.FluidMode.NONE);
        if (((RayTraceResult)blockRayTraceResult).getType() == RayTraceResult.Type.MISS) {
            return ActionResult.resultPass(itemStack);
        }
        if (((RayTraceResult)blockRayTraceResult).getType() != RayTraceResult.Type.BLOCK) {
            return ActionResult.resultPass(itemStack);
        }
        BlockRayTraceResult blockRayTraceResult2 = blockRayTraceResult;
        BlockPos blockPos = blockRayTraceResult2.getPos();
        Direction direction = blockRayTraceResult2.getFace();
        BlockPos blockPos2 = blockPos.offset(direction);
        if (world.isBlockModifiable(playerEntity, blockPos) && playerEntity.canPlayerEdit(blockPos2, direction, itemStack)) {
            BlockPos blockPos3;
            if (this.containedBlock == Fluids.EMPTY) {
                Fluid fluid;
                BlockState blockState = world.getBlockState(blockPos);
                if (blockState.getBlock() instanceof IBucketPickupHandler && (fluid = ((IBucketPickupHandler)((Object)blockState.getBlock())).pickupFluid(world, blockPos, blockState)) != Fluids.EMPTY) {
                    playerEntity.addStat(Stats.ITEM_USED.get(this));
                    playerEntity.playSound(fluid.isIn(FluidTags.LAVA) ? SoundEvents.ITEM_BUCKET_FILL_LAVA : SoundEvents.ITEM_BUCKET_FILL, 1.0f, 1.0f);
                    ItemStack itemStack2 = DrinkHelper.fill(itemStack, playerEntity, new ItemStack(fluid.getFilledBucket()));
                    if (!world.isRemote) {
                        CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayerEntity)playerEntity, new ItemStack(fluid.getFilledBucket()));
                    }
                    return ActionResult.func_233538_a_(itemStack2, world.isRemote());
                }
                return ActionResult.resultFail(itemStack);
            }
            BlockState blockState = world.getBlockState(blockPos);
            BlockPos blockPos4 = blockPos3 = blockState.getBlock() instanceof ILiquidContainer && this.containedBlock == Fluids.WATER ? blockPos : blockPos2;
            if (this.tryPlaceContainedLiquid(playerEntity, world, blockPos3, blockRayTraceResult2)) {
                this.onLiquidPlaced(world, itemStack, blockPos3);
                if (playerEntity instanceof ServerPlayerEntity) {
                    CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)playerEntity, blockPos3, itemStack);
                }
                playerEntity.addStat(Stats.ITEM_USED.get(this));
                return ActionResult.func_233538_a_(this.emptyBucket(itemStack, playerEntity), world.isRemote());
            }
            return ActionResult.resultFail(itemStack);
        }
        return ActionResult.resultFail(itemStack);
    }

    protected ItemStack emptyBucket(ItemStack itemStack, PlayerEntity playerEntity) {
        return !playerEntity.abilities.isCreativeMode ? new ItemStack(Items.BUCKET) : itemStack;
    }

    public void onLiquidPlaced(World world, ItemStack itemStack, BlockPos blockPos) {
    }

    public boolean tryPlaceContainedLiquid(@Nullable PlayerEntity playerEntity, World world, BlockPos blockPos, @Nullable BlockRayTraceResult blockRayTraceResult) {
        boolean bl;
        if (!(this.containedBlock instanceof FlowingFluid)) {
            return true;
        }
        BlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        Material material = blockState.getMaterial();
        boolean bl2 = blockState.isReplaceable(this.containedBlock);
        boolean bl3 = bl = blockState.isAir() || bl2 || block instanceof ILiquidContainer && ((ILiquidContainer)((Object)block)).canContainFluid(world, blockPos, blockState, this.containedBlock);
        if (!bl) {
            return blockRayTraceResult != null && this.tryPlaceContainedLiquid(playerEntity, world, blockRayTraceResult.getPos().offset(blockRayTraceResult.getFace()), null);
        }
        if (world.getDimensionType().isUltrawarm() && this.containedBlock.isIn(FluidTags.WATER)) {
            int n = blockPos.getX();
            int n2 = blockPos.getY();
            int n3 = blockPos.getZ();
            world.playSound(playerEntity, blockPos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5f, 2.6f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8f);
            for (int i = 0; i < 8; ++i) {
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double)n + Math.random(), (double)n2 + Math.random(), (double)n3 + Math.random(), 0.0, 0.0, 0.0);
            }
            return false;
        }
        if (block instanceof ILiquidContainer && this.containedBlock == Fluids.WATER) {
            ((ILiquidContainer)((Object)block)).receiveFluid(world, blockPos, blockState, ((FlowingFluid)this.containedBlock).getStillFluidState(true));
            this.playEmptySound(playerEntity, world, blockPos);
            return false;
        }
        if (!world.isRemote && bl2 && !material.isLiquid()) {
            world.destroyBlock(blockPos, false);
        }
        if (!world.setBlockState(blockPos, this.containedBlock.getDefaultState().getBlockState(), 0) && !blockState.getFluidState().isSource()) {
            return true;
        }
        this.playEmptySound(playerEntity, world, blockPos);
        return false;
    }

    protected void playEmptySound(@Nullable PlayerEntity playerEntity, IWorld iWorld, BlockPos blockPos) {
        SoundEvent soundEvent = this.containedBlock.isIn(FluidTags.LAVA) ? SoundEvents.ITEM_BUCKET_EMPTY_LAVA : SoundEvents.ITEM_BUCKET_EMPTY;
        iWorld.playSound(playerEntity, blockPos, soundEvent, SoundCategory.BLOCKS, 1.0f, 1.0f);
    }
}

