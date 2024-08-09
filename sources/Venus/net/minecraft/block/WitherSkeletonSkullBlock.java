/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SkullBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.pattern.BlockMaterialMatcher;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class WitherSkeletonSkullBlock
extends SkullBlock {
    @Nullable
    private static BlockPattern witherPatternFull;
    @Nullable
    private static BlockPattern witherPatternBase;

    protected WitherSkeletonSkullBlock(AbstractBlock.Properties properties) {
        super(SkullBlock.Types.WITHER_SKELETON, properties);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        super.onBlockPlacedBy(world, blockPos, blockState, livingEntity, itemStack);
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof SkullTileEntity) {
            WitherSkeletonSkullBlock.checkWitherSpawn(world, blockPos, (SkullTileEntity)tileEntity);
        }
    }

    public static void checkWitherSpawn(World world, BlockPos blockPos, SkullTileEntity skullTileEntity) {
        if (!world.isRemote) {
            BlockPattern blockPattern;
            BlockPattern.PatternHelper patternHelper;
            boolean bl;
            BlockState blockState = skullTileEntity.getBlockState();
            boolean bl2 = bl = blockState.isIn(Blocks.WITHER_SKELETON_SKULL) || blockState.isIn(Blocks.WITHER_SKELETON_WALL_SKULL);
            if (bl && blockPos.getY() >= 0 && world.getDifficulty() != Difficulty.PEACEFUL && (patternHelper = (blockPattern = WitherSkeletonSkullBlock.getOrCreateWitherFull()).match(world, blockPos)) != null) {
                for (int i = 0; i < blockPattern.getPalmLength(); ++i) {
                    for (int j = 0; j < blockPattern.getThumbLength(); ++j) {
                        CachedBlockInfo cachedBlockInfo = patternHelper.translateOffset(i, j, 0);
                        world.setBlockState(cachedBlockInfo.getPos(), Blocks.AIR.getDefaultState(), 1);
                        world.playEvent(2001, cachedBlockInfo.getPos(), Block.getStateId(cachedBlockInfo.getBlockState()));
                    }
                }
                WitherEntity witherEntity = EntityType.WITHER.create(world);
                BlockPos blockPos2 = patternHelper.translateOffset(1, 2, 0).getPos();
                witherEntity.setLocationAndAngles((double)blockPos2.getX() + 0.5, (double)blockPos2.getY() + 0.55, (double)blockPos2.getZ() + 0.5, patternHelper.getForwards().getAxis() == Direction.Axis.X ? 0.0f : 90.0f, 0.0f);
                witherEntity.renderYawOffset = patternHelper.getForwards().getAxis() == Direction.Axis.X ? 0.0f : 90.0f;
                witherEntity.ignite();
                for (ServerPlayerEntity serverPlayerEntity : world.getEntitiesWithinAABB(ServerPlayerEntity.class, witherEntity.getBoundingBox().grow(50.0))) {
                    CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayerEntity, witherEntity);
                }
                world.addEntity(witherEntity);
                for (int i = 0; i < blockPattern.getPalmLength(); ++i) {
                    for (int j = 0; j < blockPattern.getThumbLength(); ++j) {
                        world.func_230547_a_(patternHelper.translateOffset(i, j, 0).getPos(), Blocks.AIR);
                    }
                }
            }
        }
    }

    public static boolean canSpawnMob(World world, BlockPos blockPos, ItemStack itemStack) {
        if (itemStack.getItem() == Items.WITHER_SKELETON_SKULL && blockPos.getY() >= 2 && world.getDifficulty() != Difficulty.PEACEFUL && !world.isRemote) {
            return WitherSkeletonSkullBlock.getOrCreateWitherBase().match(world, blockPos) != null;
        }
        return true;
    }

    private static BlockPattern getOrCreateWitherFull() {
        if (witherPatternFull == null) {
            witherPatternFull = BlockPatternBuilder.start().aisle("^^^", "###", "~#~").where('#', WitherSkeletonSkullBlock::lambda$getOrCreateWitherFull$0).where('^', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(Blocks.WITHER_SKELETON_SKULL).or(BlockStateMatcher.forBlock(Blocks.WITHER_SKELETON_WALL_SKULL)))).where('~', CachedBlockInfo.hasState(BlockMaterialMatcher.forMaterial(Material.AIR))).build();
        }
        return witherPatternFull;
    }

    private static BlockPattern getOrCreateWitherBase() {
        if (witherPatternBase == null) {
            witherPatternBase = BlockPatternBuilder.start().aisle("   ", "###", "~#~").where('#', WitherSkeletonSkullBlock::lambda$getOrCreateWitherBase$1).where('~', CachedBlockInfo.hasState(BlockMaterialMatcher.forMaterial(Material.AIR))).build();
        }
        return witherPatternBase;
    }

    private static boolean lambda$getOrCreateWitherBase$1(CachedBlockInfo cachedBlockInfo) {
        return cachedBlockInfo.getBlockState().isIn(BlockTags.WITHER_SUMMON_BASE_BLOCKS);
    }

    private static boolean lambda$getOrCreateWitherFull$0(CachedBlockInfo cachedBlockInfo) {
        return cachedBlockInfo.getBlockState().isIn(BlockTags.WITHER_SUMMON_BASE_BLOCKS);
    }
}

