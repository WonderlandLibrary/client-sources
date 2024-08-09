/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.WoodType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public abstract class AbstractSignBlock
extends ContainerBlock
implements IWaterLoggable {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
    private final WoodType woodType;

    protected AbstractSignBlock(AbstractBlock.Properties properties, WoodType woodType) {
        super(properties);
        this.woodType = woodType;
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        if (blockState.get(WATERLOGGED).booleanValue()) {
            iWorld.getPendingFluidTicks().scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickRate(iWorld));
        }
        return super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return SHAPE;
    }

    @Override
    public boolean canSpawnInBlock() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new SignTileEntity();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        boolean bl;
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        boolean bl2 = bl = itemStack.getItem() instanceof DyeItem && playerEntity.abilities.allowEdit;
        if (world.isRemote) {
            return bl ? ActionResultType.SUCCESS : ActionResultType.CONSUME;
        }
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof SignTileEntity) {
            boolean bl3;
            SignTileEntity signTileEntity = (SignTileEntity)tileEntity;
            if (bl && (bl3 = signTileEntity.setTextColor(((DyeItem)itemStack.getItem()).getDyeColor())) && !playerEntity.isCreative()) {
                itemStack.shrink(1);
            }
            return signTileEntity.executeCommand(playerEntity) ? ActionResultType.SUCCESS : ActionResultType.PASS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public FluidState getFluidState(BlockState blockState) {
        return blockState.get(WATERLOGGED) != false ? Fluids.WATER.getStillFluidState(true) : super.getFluidState(blockState);
    }

    public WoodType getWoodType() {
        return this.woodType;
    }
}

