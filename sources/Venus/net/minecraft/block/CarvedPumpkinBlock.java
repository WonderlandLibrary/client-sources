/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.pattern.BlockMaterialMatcher;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.enchantment.IArmorVanishable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class CarvedPumpkinBlock
extends HorizontalBlock
implements IArmorVanishable {
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    @Nullable
    private BlockPattern snowmanBasePattern;
    @Nullable
    private BlockPattern snowmanPattern;
    @Nullable
    private BlockPattern golemBasePattern;
    @Nullable
    private BlockPattern golemPattern;
    private static final Predicate<BlockState> IS_PUMPKIN = CarvedPumpkinBlock::lambda$static$0;

    protected CarvedPumpkinBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(FACING, Direction.NORTH));
    }

    @Override
    public void onBlockAdded(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState2.isIn(blockState.getBlock())) {
            this.trySpawnGolem(world, blockPos);
        }
    }

    public boolean canDispenserPlace(IWorldReader iWorldReader, BlockPos blockPos) {
        return this.getSnowmanBasePattern().match(iWorldReader, blockPos) != null || this.getGolemBasePattern().match(iWorldReader, blockPos) != null;
    }

    /*
     * WARNING - void declaration
     */
    private void trySpawnGolem(World world, BlockPos blockPos) {
        block9: {
            BlockPattern.PatternHelper patternHelper;
            block8: {
                Object object;
                patternHelper = this.getSnowmanPattern().match(world, blockPos);
                if (patternHelper == null) break block8;
                for (int i = 0; i < this.getSnowmanPattern().getThumbLength(); ++i) {
                    object = patternHelper.translateOffset(0, i, 0);
                    world.setBlockState(((CachedBlockInfo)object).getPos(), Blocks.AIR.getDefaultState(), 1);
                    world.playEvent(2001, ((CachedBlockInfo)object).getPos(), Block.getStateId(((CachedBlockInfo)object).getBlockState()));
                }
                SnowGolemEntity snowGolemEntity = EntityType.SNOW_GOLEM.create(world);
                object = patternHelper.translateOffset(0, 2, 0).getPos();
                snowGolemEntity.setLocationAndAngles((double)((Vector3i)object).getX() + 0.5, (double)((Vector3i)object).getY() + 0.05, (double)((Vector3i)object).getZ() + 0.5, 0.0f, 0.0f);
                world.addEntity(snowGolemEntity);
                for (ServerPlayerEntity object2 : world.getEntitiesWithinAABB(ServerPlayerEntity.class, snowGolemEntity.getBoundingBox().grow(5.0))) {
                    CriteriaTriggers.SUMMONED_ENTITY.trigger(object2, snowGolemEntity);
                }
                for (int i = 0; i < this.getSnowmanPattern().getThumbLength(); ++i) {
                    CachedBlockInfo j = patternHelper.translateOffset(0, i, 0);
                    world.func_230547_a_(j.getPos(), Blocks.AIR);
                }
                break block9;
            }
            patternHelper = this.getGolemPattern().match(world, blockPos);
            if (patternHelper == null) break block9;
            for (int i = 0; i < this.getGolemPattern().getPalmLength(); ++i) {
                for (int j = 0; j < this.getGolemPattern().getThumbLength(); ++j) {
                    CachedBlockInfo cachedBlockInfo = patternHelper.translateOffset(i, j, 0);
                    world.setBlockState(cachedBlockInfo.getPos(), Blocks.AIR.getDefaultState(), 1);
                    world.playEvent(2001, cachedBlockInfo.getPos(), Block.getStateId(cachedBlockInfo.getBlockState()));
                }
            }
            BlockPos blockPos2 = patternHelper.translateOffset(1, 2, 0).getPos();
            IronGolemEntity ironGolemEntity = EntityType.IRON_GOLEM.create(world);
            ironGolemEntity.setPlayerCreated(false);
            ironGolemEntity.setLocationAndAngles((double)blockPos2.getX() + 0.5, (double)blockPos2.getY() + 0.05, (double)blockPos2.getZ() + 0.5, 0.0f, 0.0f);
            world.addEntity(ironGolemEntity);
            for (ServerPlayerEntity serverPlayerEntity : world.getEntitiesWithinAABB(ServerPlayerEntity.class, ironGolemEntity.getBoundingBox().grow(5.0))) {
                CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayerEntity, ironGolemEntity);
            }
            for (int i = 0; i < this.getGolemPattern().getPalmLength(); ++i) {
                void var7_20;
                boolean bl = false;
                while (var7_20 < this.getGolemPattern().getThumbLength()) {
                    CachedBlockInfo cachedBlockInfo = patternHelper.translateOffset(i, (int)var7_20, 0);
                    world.func_230547_a_(cachedBlockInfo.getPos(), Blocks.AIR);
                    ++var7_20;
                }
            }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        return (BlockState)this.getDefaultState().with(FACING, blockItemUseContext.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    private BlockPattern getSnowmanBasePattern() {
        if (this.snowmanBasePattern == null) {
            this.snowmanBasePattern = BlockPatternBuilder.start().aisle(" ", "#", "#").where('#', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(Blocks.SNOW_BLOCK))).build();
        }
        return this.snowmanBasePattern;
    }

    private BlockPattern getSnowmanPattern() {
        if (this.snowmanPattern == null) {
            this.snowmanPattern = BlockPatternBuilder.start().aisle("^", "#", "#").where('^', CachedBlockInfo.hasState(IS_PUMPKIN)).where('#', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(Blocks.SNOW_BLOCK))).build();
        }
        return this.snowmanPattern;
    }

    private BlockPattern getGolemBasePattern() {
        if (this.golemBasePattern == null) {
            this.golemBasePattern = BlockPatternBuilder.start().aisle("~ ~", "###", "~#~").where('#', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(Blocks.IRON_BLOCK))).where('~', CachedBlockInfo.hasState(BlockMaterialMatcher.forMaterial(Material.AIR))).build();
        }
        return this.golemBasePattern;
    }

    private BlockPattern getGolemPattern() {
        if (this.golemPattern == null) {
            this.golemPattern = BlockPatternBuilder.start().aisle("~^~", "###", "~#~").where('^', CachedBlockInfo.hasState(IS_PUMPKIN)).where('#', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(Blocks.IRON_BLOCK))).where('~', CachedBlockInfo.hasState(BlockMaterialMatcher.forMaterial(Material.AIR))).build();
        }
        return this.golemPattern;
    }

    private static boolean lambda$static$0(BlockState blockState) {
        return blockState != null && (blockState.isIn(Blocks.CARVED_PUMPKIN) || blockState.isIn(Blocks.JACK_O_LANTERN));
    }
}

