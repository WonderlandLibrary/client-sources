/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class RedstoneOreBlock
extends Block {
    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

    public RedstoneOreBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)this.getDefaultState().with(LIT, false));
    }

    @Override
    public void onBlockClicked(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity) {
        RedstoneOreBlock.activate(blockState, world, blockPos);
        super.onBlockClicked(blockState, world, blockPos, playerEntity);
    }

    @Override
    public void onEntityWalk(World world, BlockPos blockPos, Entity entity2) {
        RedstoneOreBlock.activate(world.getBlockState(blockPos), world, blockPos);
        super.onEntityWalk(world, blockPos, entity2);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (world.isRemote) {
            RedstoneOreBlock.spawnParticles(world, blockPos);
        } else {
            RedstoneOreBlock.activate(blockState, world, blockPos);
        }
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        return itemStack.getItem() instanceof BlockItem && new BlockItemUseContext(playerEntity, hand, itemStack, blockRayTraceResult).canPlace() ? ActionResultType.PASS : ActionResultType.SUCCESS;
    }

    private static void activate(BlockState blockState, World world, BlockPos blockPos) {
        RedstoneOreBlock.spawnParticles(world, blockPos);
        if (!blockState.get(LIT).booleanValue()) {
            world.setBlockState(blockPos, (BlockState)blockState.with(LIT, true), 0);
        }
    }

    @Override
    public boolean ticksRandomly(BlockState blockState) {
        return blockState.get(LIT);
    }

    @Override
    public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        if (blockState.get(LIT).booleanValue()) {
            serverWorld.setBlockState(blockPos, (BlockState)blockState.with(LIT, false), 0);
        }
    }

    @Override
    public void spawnAdditionalDrops(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, ItemStack itemStack) {
        super.spawnAdditionalDrops(blockState, serverWorld, blockPos, itemStack);
        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, itemStack) == 0) {
            int n = 1 + serverWorld.rand.nextInt(5);
            this.dropXpOnBlockBreak(serverWorld, blockPos, n);
        }
    }

    @Override
    public void animateTick(BlockState blockState, World world, BlockPos blockPos, Random random2) {
        if (blockState.get(LIT).booleanValue()) {
            RedstoneOreBlock.spawnParticles(world, blockPos);
        }
    }

    private static void spawnParticles(World world, BlockPos blockPos) {
        double d = 0.5625;
        Random random2 = world.rand;
        for (Direction direction : Direction.values()) {
            BlockPos blockPos2 = blockPos.offset(direction);
            if (world.getBlockState(blockPos2).isOpaqueCube(world, blockPos2)) continue;
            Direction.Axis axis = direction.getAxis();
            double d2 = axis == Direction.Axis.X ? 0.5 + 0.5625 * (double)direction.getXOffset() : (double)random2.nextFloat();
            double d3 = axis == Direction.Axis.Y ? 0.5 + 0.5625 * (double)direction.getYOffset() : (double)random2.nextFloat();
            double d4 = axis == Direction.Axis.Z ? 0.5 + 0.5625 * (double)direction.getZOffset() : (double)random2.nextFloat();
            world.addParticle(RedstoneParticleData.REDSTONE_DUST, (double)blockPos.getX() + d2, (double)blockPos.getY() + d3, (double)blockPos.getZ() + d4, 0.0, 0.0, 0.0);
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }
}

