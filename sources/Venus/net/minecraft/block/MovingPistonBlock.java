/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.PistonHeadBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.PistonType;
import net.minecraft.tileentity.PistonTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class MovingPistonBlock
extends ContainerBlock {
    public static final DirectionProperty FACING = PistonHeadBlock.FACING;
    public static final EnumProperty<PistonType> TYPE = PistonHeadBlock.TYPE;

    public MovingPistonBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(FACING, Direction.NORTH)).with(TYPE, PistonType.DEFAULT));
    }

    @Override
    @Nullable
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return null;
    }

    public static TileEntity createTilePiston(BlockState blockState, Direction direction, boolean bl, boolean bl2) {
        return new PistonTileEntity(blockState, direction, bl, bl2);
    }

    @Override
    public void onReplaced(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        TileEntity tileEntity;
        if (!blockState.isIn(blockState2.getBlock()) && (tileEntity = world.getTileEntity(blockPos)) instanceof PistonTileEntity) {
            ((PistonTileEntity)tileEntity).clearPistonTileEntity();
        }
    }

    @Override
    public void onPlayerDestroy(IWorld iWorld, BlockPos blockPos, BlockState blockState) {
        BlockPos blockPos2 = blockPos.offset(blockState.get(FACING).getOpposite());
        BlockState blockState2 = iWorld.getBlockState(blockPos2);
        if (blockState2.getBlock() instanceof PistonBlock && blockState2.get(PistonBlock.EXTENDED).booleanValue()) {
            iWorld.removeBlock(blockPos2, false);
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (!world.isRemote && world.getTileEntity(blockPos) == null) {
            world.removeBlock(blockPos, true);
            return ActionResultType.CONSUME;
        }
        return ActionResultType.PASS;
    }

    @Override
    public List<ItemStack> getDrops(BlockState blockState, LootContext.Builder builder) {
        PistonTileEntity pistonTileEntity = this.getTileEntity(builder.getWorld(), new BlockPos(builder.assertPresent(LootParameters.field_237457_g_)));
        return pistonTileEntity == null ? Collections.emptyList() : pistonTileEntity.getPistonState().getDrops(builder);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        PistonTileEntity pistonTileEntity = this.getTileEntity(iBlockReader, blockPos);
        return pistonTileEntity != null ? pistonTileEntity.getCollisionShape(iBlockReader, blockPos) : VoxelShapes.empty();
    }

    @Nullable
    private PistonTileEntity getTileEntity(IBlockReader iBlockReader, BlockPos blockPos) {
        TileEntity tileEntity = iBlockReader.getTileEntity(blockPos);
        return tileEntity instanceof PistonTileEntity ? (PistonTileEntity)tileEntity : null;
    }

    @Override
    public ItemStack getItem(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState) {
        return ItemStack.EMPTY;
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return (BlockState)blockState.with(FACING, rotation.rotate(blockState.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.toRotation(blockState.get(FACING)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, TYPE);
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        return true;
    }
}

