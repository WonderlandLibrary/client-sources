/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DeadCoralWallFanBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.server.ServerWorld;

public class BoneMealItem
extends Item {
    public BoneMealItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext itemUseContext) {
        World world = itemUseContext.getWorld();
        BlockPos blockPos = itemUseContext.getPos();
        BlockPos blockPos2 = blockPos.offset(itemUseContext.getFace());
        if (BoneMealItem.applyBonemeal(itemUseContext.getItem(), world, blockPos)) {
            if (!world.isRemote) {
                world.playEvent(2005, blockPos, 0);
            }
            return ActionResultType.func_233537_a_(world.isRemote);
        }
        BlockState blockState = world.getBlockState(blockPos);
        boolean bl = blockState.isSolidSide(world, blockPos, itemUseContext.getFace());
        if (bl && BoneMealItem.growSeagrass(itemUseContext.getItem(), world, blockPos2, itemUseContext.getFace())) {
            if (!world.isRemote) {
                world.playEvent(2005, blockPos2, 0);
            }
            return ActionResultType.func_233537_a_(world.isRemote);
        }
        return ActionResultType.PASS;
    }

    public static boolean applyBonemeal(ItemStack itemStack, World world, BlockPos blockPos) {
        IGrowable iGrowable;
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() instanceof IGrowable && (iGrowable = (IGrowable)((Object)blockState.getBlock())).canGrow(world, blockPos, blockState, world.isRemote)) {
            if (world instanceof ServerWorld) {
                if (iGrowable.canUseBonemeal(world, world.rand, blockPos, blockState)) {
                    iGrowable.grow((ServerWorld)world, world.rand, blockPos, blockState);
                }
                itemStack.shrink(1);
            }
            return false;
        }
        return true;
    }

    public static boolean growSeagrass(ItemStack itemStack, World world, BlockPos blockPos, @Nullable Direction direction) {
        if (world.getBlockState(blockPos).isIn(Blocks.WATER) && world.getFluidState(blockPos).getLevel() == 8) {
            if (!(world instanceof ServerWorld)) {
                return false;
            }
            block0: for (int i = 0; i < 128; ++i) {
                BlockPos blockPos2 = blockPos;
                BlockState blockState = Blocks.SEAGRASS.getDefaultState();
                for (int j = 0; j < i / 16; ++j) {
                    if (world.getBlockState(blockPos2 = blockPos2.add(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1)).hasOpaqueCollisionShape(world, blockPos2)) continue block0;
                }
                Optional<RegistryKey<Biome>> optional = world.func_242406_i(blockPos2);
                if (Objects.equals(optional, Optional.of(Biomes.WARM_OCEAN)) || Objects.equals(optional, Optional.of(Biomes.DEEP_WARM_OCEAN))) {
                    if (i == 0 && direction != null && direction.getAxis().isHorizontal()) {
                        blockState = (BlockState)((Block)BlockTags.WALL_CORALS.getRandomElement(world.rand)).getDefaultState().with(DeadCoralWallFanBlock.FACING, direction);
                    } else if (random.nextInt(4) == 0) {
                        blockState = ((Block)BlockTags.UNDERWATER_BONEMEALS.getRandomElement(random)).getDefaultState();
                    }
                }
                if (blockState.getBlock().isIn(BlockTags.WALL_CORALS)) {
                    for (int j = 0; !blockState.isValidPosition(world, blockPos2) && j < 4; ++j) {
                        blockState = (BlockState)blockState.with(DeadCoralWallFanBlock.FACING, Direction.Plane.HORIZONTAL.random(random));
                    }
                }
                if (!blockState.isValidPosition(world, blockPos2)) continue;
                BlockState blockState2 = world.getBlockState(blockPos2);
                if (blockState2.isIn(Blocks.WATER) && world.getFluidState(blockPos2).getLevel() == 8) {
                    world.setBlockState(blockPos2, blockState, 0);
                    continue;
                }
                if (!blockState2.isIn(Blocks.SEAGRASS) || random.nextInt(10) != 0) continue;
                ((IGrowable)((Object)Blocks.SEAGRASS)).grow((ServerWorld)world, random, blockPos2, blockState2);
            }
            itemStack.shrink(1);
            return false;
        }
        return true;
    }

    public static void spawnBonemealParticles(IWorld iWorld, BlockPos blockPos, int n) {
        BlockState blockState;
        if (n == 0) {
            n = 15;
        }
        if (!(blockState = iWorld.getBlockState(blockPos)).isAir()) {
            double d;
            double d2 = 0.5;
            if (blockState.isIn(Blocks.WATER)) {
                n *= 3;
                d = 1.0;
                d2 = 3.0;
            } else if (blockState.isOpaqueCube(iWorld, blockPos)) {
                blockPos = blockPos.up();
                n *= 3;
                d2 = 3.0;
                d = 1.0;
            } else {
                d = blockState.getShape(iWorld, blockPos).getEnd(Direction.Axis.Y);
            }
            iWorld.addParticle(ParticleTypes.HAPPY_VILLAGER, (double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5, 0.0, 0.0, 0.0);
            for (int i = 0; i < n; ++i) {
                double d3;
                double d4;
                double d5 = random.nextGaussian() * 0.02;
                double d6 = random.nextGaussian() * 0.02;
                double d7 = random.nextGaussian() * 0.02;
                double d8 = 0.5 - d2;
                double d9 = (double)blockPos.getX() + d8 + random.nextDouble() * d2 * 2.0;
                if (iWorld.getBlockState(new BlockPos(d9, d4 = (double)blockPos.getY() + random.nextDouble() * d, d3 = (double)blockPos.getZ() + d8 + random.nextDouble() * d2 * 2.0).down()).isAir()) continue;
                iWorld.addParticle(ParticleTypes.HAPPY_VILLAGER, d9, d4, d3, d5, d6, d7);
            }
        }
    }
}

