/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractChestBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.EnderChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class EnderChestBlock
extends AbstractChestBlock<EnderChestTileEntity>
implements IWaterLoggable {
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(1.0, 0.0, 1.0, 15.0, 14.0, 15.0);
    private static final ITextComponent CONTAINER_NAME = new TranslationTextComponent("container.enderchest");

    protected EnderChestBlock(AbstractBlock.Properties properties) {
        super(properties, EnderChestBlock::lambda$new$0);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(FACING, Direction.NORTH)).with(WATERLOGGED, false));
    }

    @Override
    public TileEntityMerger.ICallbackWrapper<? extends ChestTileEntity> combine(BlockState blockState, World world, BlockPos blockPos, boolean bl) {
        return TileEntityMerger.ICallback::func_225537_b_;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return SHAPE;
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        FluidState fluidState = blockItemUseContext.getWorld().getFluidState(blockItemUseContext.getPos());
        return (BlockState)((BlockState)this.getDefaultState().with(FACING, blockItemUseContext.getPlacementHorizontalFacing().getOpposite())).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        EnderChestInventory enderChestInventory = playerEntity.getInventoryEnderChest();
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (enderChestInventory != null && tileEntity instanceof EnderChestTileEntity) {
            BlockPos blockPos2 = blockPos.up();
            if (world.getBlockState(blockPos2).isNormalCube(world, blockPos2)) {
                return ActionResultType.func_233537_a_(world.isRemote);
            }
            if (world.isRemote) {
                return ActionResultType.SUCCESS;
            }
            EnderChestTileEntity enderChestTileEntity = (EnderChestTileEntity)tileEntity;
            enderChestInventory.setChestTileEntity(enderChestTileEntity);
            playerEntity.openContainer(new SimpleNamedContainerProvider((arg_0, arg_1, arg_2) -> EnderChestBlock.lambda$onBlockActivated$1(enderChestInventory, arg_0, arg_1, arg_2), CONTAINER_NAME));
            playerEntity.addStat(Stats.OPEN_ENDERCHEST);
            PiglinTasks.func_234478_a_(playerEntity, true);
            return ActionResultType.CONSUME;
        }
        return ActionResultType.func_233537_a_(world.isRemote);
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new EnderChestTileEntity();
    }

    @Override
    public void animateTick(BlockState blockState, World world, BlockPos blockPos, Random random2) {
        for (int i = 0; i < 3; ++i) {
            int n = random2.nextInt(2) * 2 - 1;
            int n2 = random2.nextInt(2) * 2 - 1;
            double d = (double)blockPos.getX() + 0.5 + 0.25 * (double)n;
            double d2 = (float)blockPos.getY() + random2.nextFloat();
            double d3 = (double)blockPos.getZ() + 0.5 + 0.25 * (double)n2;
            double d4 = random2.nextFloat() * (float)n;
            double d5 = ((double)random2.nextFloat() - 0.5) * 0.125;
            double d6 = random2.nextFloat() * (float)n2;
            world.addParticle(ParticleTypes.PORTAL, d, d2, d3, d4, d5, d6);
        }
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
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState blockState) {
        return blockState.get(WATERLOGGED) != false ? Fluids.WATER.getStillFluidState(true) : super.getFluidState(blockState);
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        if (blockState.get(WATERLOGGED).booleanValue()) {
            iWorld.getPendingFluidTicks().scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickRate(iWorld));
        }
        return super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        return true;
    }

    private static Container lambda$onBlockActivated$1(EnderChestInventory enderChestInventory, int n, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return ChestContainer.createGeneric9X3(n, playerInventory, enderChestInventory);
    }

    private static TileEntityType lambda$new$0() {
        return TileEntityType.ENDER_CHEST;
    }
}

