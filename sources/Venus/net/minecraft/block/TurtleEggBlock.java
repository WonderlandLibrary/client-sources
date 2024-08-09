/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class TurtleEggBlock
extends Block {
    private static final VoxelShape ONE_EGG_SHAPE = Block.makeCuboidShape(3.0, 0.0, 3.0, 12.0, 7.0, 12.0);
    private static final VoxelShape MULTI_EGG_SHAPE = Block.makeCuboidShape(1.0, 0.0, 1.0, 15.0, 7.0, 15.0);
    public static final IntegerProperty HATCH = BlockStateProperties.HATCH_0_2;
    public static final IntegerProperty EGGS = BlockStateProperties.EGGS_1_4;

    public TurtleEggBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(HATCH, 0)).with(EGGS, 1));
    }

    @Override
    public void onEntityWalk(World world, BlockPos blockPos, Entity entity2) {
        this.tryTrample(world, blockPos, entity2, 100);
        super.onEntityWalk(world, blockPos, entity2);
    }

    @Override
    public void onFallenUpon(World world, BlockPos blockPos, Entity entity2, float f) {
        if (!(entity2 instanceof ZombieEntity)) {
            this.tryTrample(world, blockPos, entity2, 3);
        }
        super.onFallenUpon(world, blockPos, entity2, f);
    }

    private void tryTrample(World world, BlockPos blockPos, Entity entity2, int n) {
        BlockState blockState;
        if (this.canTrample(world, entity2) && !world.isRemote && world.rand.nextInt(n) == 0 && (blockState = world.getBlockState(blockPos)).isIn(Blocks.TURTLE_EGG)) {
            this.removeOneEgg(world, blockPos, blockState);
        }
    }

    private void removeOneEgg(World world, BlockPos blockPos, BlockState blockState) {
        world.playSound(null, blockPos, SoundEvents.ENTITY_TURTLE_EGG_BREAK, SoundCategory.BLOCKS, 0.7f, 0.9f + world.rand.nextFloat() * 0.2f);
        int n = blockState.get(EGGS);
        if (n <= 1) {
            world.destroyBlock(blockPos, true);
        } else {
            world.setBlockState(blockPos, (BlockState)blockState.with(EGGS, n - 1), 1);
            world.playEvent(2001, blockPos, Block.getStateId(blockState));
        }
    }

    @Override
    public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        if (this.canGrow(serverWorld) && TurtleEggBlock.hasProperHabitat(serverWorld, blockPos)) {
            int n = blockState.get(HATCH);
            if (n < 2) {
                serverWorld.playSound(null, blockPos, SoundEvents.ENTITY_TURTLE_EGG_CRACK, SoundCategory.BLOCKS, 0.7f, 0.9f + random2.nextFloat() * 0.2f);
                serverWorld.setBlockState(blockPos, (BlockState)blockState.with(HATCH, n + 1), 1);
            } else {
                serverWorld.playSound(null, blockPos, SoundEvents.ENTITY_TURTLE_EGG_HATCH, SoundCategory.BLOCKS, 0.7f, 0.9f + random2.nextFloat() * 0.2f);
                serverWorld.removeBlock(blockPos, true);
                for (int i = 0; i < blockState.get(EGGS); ++i) {
                    serverWorld.playEvent(2001, blockPos, Block.getStateId(blockState));
                    TurtleEntity turtleEntity = EntityType.TURTLE.create(serverWorld);
                    turtleEntity.setGrowingAge(-24000);
                    turtleEntity.setHome(blockPos);
                    turtleEntity.setLocationAndAngles((double)blockPos.getX() + 0.3 + (double)i * 0.2, blockPos.getY(), (double)blockPos.getZ() + 0.3, 0.0f, 0.0f);
                    serverWorld.addEntity(turtleEntity);
                }
            }
        }
    }

    public static boolean hasProperHabitat(IBlockReader iBlockReader, BlockPos blockPos) {
        return TurtleEggBlock.isProperHabitat(iBlockReader, blockPos.down());
    }

    public static boolean isProperHabitat(IBlockReader iBlockReader, BlockPos blockPos) {
        return iBlockReader.getBlockState(blockPos).isIn(BlockTags.SAND);
    }

    @Override
    public void onBlockAdded(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (TurtleEggBlock.hasProperHabitat(world, blockPos) && !world.isRemote) {
            world.playEvent(2005, blockPos, 0);
        }
    }

    private boolean canGrow(World world) {
        float f = world.func_242415_f(1.0f);
        if ((double)f < 0.69 && (double)f > 0.65) {
            return false;
        }
        return world.rand.nextInt(500) == 0;
    }

    @Override
    public void harvestBlock(World world, PlayerEntity playerEntity, BlockPos blockPos, BlockState blockState, @Nullable TileEntity tileEntity, ItemStack itemStack) {
        super.harvestBlock(world, playerEntity, blockPos, blockState, tileEntity, itemStack);
        this.removeOneEgg(world, blockPos, blockState);
    }

    @Override
    public boolean isReplaceable(BlockState blockState, BlockItemUseContext blockItemUseContext) {
        return blockItemUseContext.getItem().getItem() == this.asItem() && blockState.get(EGGS) < 4 ? true : super.isReplaceable(blockState, blockItemUseContext);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        BlockState blockState = blockItemUseContext.getWorld().getBlockState(blockItemUseContext.getPos());
        return blockState.isIn(this) ? (BlockState)blockState.with(EGGS, Math.min(4, blockState.get(EGGS) + 1)) : super.getStateForPlacement(blockItemUseContext);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return blockState.get(EGGS) > 1 ? MULTI_EGG_SHAPE : ONE_EGG_SHAPE;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HATCH, EGGS);
    }

    private boolean canTrample(World world, Entity entity2) {
        if (!(entity2 instanceof TurtleEntity) && !(entity2 instanceof BatEntity)) {
            if (!(entity2 instanceof LivingEntity)) {
                return true;
            }
            return entity2 instanceof PlayerEntity || world.getGameRules().getBoolean(GameRules.MOB_GRIEFING);
        }
        return true;
    }
}

