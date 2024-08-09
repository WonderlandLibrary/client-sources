/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractChestBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.DoubleSidedInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.ChestType;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class ChestBlock
extends AbstractChestBlock<ChestTileEntity>
implements IWaterLoggable {
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final EnumProperty<ChestType> TYPE = BlockStateProperties.CHEST_TYPE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape SHAPE_NORTH = Block.makeCuboidShape(1.0, 0.0, 0.0, 15.0, 14.0, 15.0);
    protected static final VoxelShape SHAPE_SOUTH = Block.makeCuboidShape(1.0, 0.0, 1.0, 15.0, 14.0, 16.0);
    protected static final VoxelShape SHAPE_WEST = Block.makeCuboidShape(0.0, 0.0, 1.0, 15.0, 14.0, 15.0);
    protected static final VoxelShape SHAPE_EAST = Block.makeCuboidShape(1.0, 0.0, 1.0, 16.0, 14.0, 15.0);
    protected static final VoxelShape SHAPE_SINGLE = Block.makeCuboidShape(1.0, 0.0, 1.0, 15.0, 14.0, 15.0);
    private static final TileEntityMerger.ICallback<ChestTileEntity, Optional<IInventory>> INVENTORY_MERGER = new TileEntityMerger.ICallback<ChestTileEntity, Optional<IInventory>>(){

        @Override
        public Optional<IInventory> func_225539_a_(ChestTileEntity chestTileEntity, ChestTileEntity chestTileEntity2) {
            return Optional.of(new DoubleSidedInventory(chestTileEntity, chestTileEntity2));
        }

        @Override
        public Optional<IInventory> func_225538_a_(ChestTileEntity chestTileEntity) {
            return Optional.of(chestTileEntity);
        }

        @Override
        public Optional<IInventory> func_225537_b_() {
            return Optional.empty();
        }

        @Override
        public Object func_225537_b_() {
            return this.func_225537_b_();
        }

        @Override
        public Object func_225538_a_(Object object) {
            return this.func_225538_a_((ChestTileEntity)object);
        }

        @Override
        public Object func_225539_a_(Object object, Object object2) {
            return this.func_225539_a_((ChestTileEntity)object, (ChestTileEntity)object2);
        }
    };
    private static final TileEntityMerger.ICallback<ChestTileEntity, Optional<INamedContainerProvider>> CONTAINER_MERGER = new TileEntityMerger.ICallback<ChestTileEntity, Optional<INamedContainerProvider>>(){

        @Override
        public Optional<INamedContainerProvider> func_225539_a_(ChestTileEntity chestTileEntity, ChestTileEntity chestTileEntity2) {
            DoubleSidedInventory doubleSidedInventory = new DoubleSidedInventory(chestTileEntity, chestTileEntity2);
            return Optional.of(new INamedContainerProvider(){
                final ChestTileEntity val$p_225539_1_;
                final ChestTileEntity val$p_225539_2_;
                final IInventory val$iinventory;
                final 2 this$0;
                {
                    this.this$0 = var1_1;
                    this.val$p_225539_1_ = chestTileEntity;
                    this.val$p_225539_2_ = chestTileEntity2;
                    this.val$iinventory = iInventory;
                }

                @Override
                @Nullable
                public Container createMenu(int n, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                    if (this.val$p_225539_1_.canOpen(playerEntity) && this.val$p_225539_2_.canOpen(playerEntity)) {
                        this.val$p_225539_1_.fillWithLoot(playerInventory.player);
                        this.val$p_225539_2_.fillWithLoot(playerInventory.player);
                        return ChestContainer.createGeneric9X6(n, playerInventory, this.val$iinventory);
                    }
                    return null;
                }

                @Override
                public ITextComponent getDisplayName() {
                    if (this.val$p_225539_1_.hasCustomName()) {
                        return this.val$p_225539_1_.getDisplayName();
                    }
                    return this.val$p_225539_2_.hasCustomName() ? this.val$p_225539_2_.getDisplayName() : new TranslationTextComponent("container.chestDouble");
                }
            });
        }

        @Override
        public Optional<INamedContainerProvider> func_225538_a_(ChestTileEntity chestTileEntity) {
            return Optional.of(chestTileEntity);
        }

        @Override
        public Optional<INamedContainerProvider> func_225537_b_() {
            return Optional.empty();
        }

        @Override
        public Object func_225537_b_() {
            return this.func_225537_b_();
        }

        @Override
        public Object func_225538_a_(Object object) {
            return this.func_225538_a_((ChestTileEntity)object);
        }

        @Override
        public Object func_225539_a_(Object object, Object object2) {
            return this.func_225539_a_((ChestTileEntity)object, (ChestTileEntity)object2);
        }
    };

    protected ChestBlock(AbstractBlock.Properties properties, Supplier<TileEntityType<? extends ChestTileEntity>> supplier) {
        super(properties, supplier);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(FACING, Direction.NORTH)).with(TYPE, ChestType.SINGLE)).with(WATERLOGGED, false));
    }

    public static TileEntityMerger.Type getChestMergerType(BlockState blockState) {
        ChestType chestType = blockState.get(TYPE);
        if (chestType == ChestType.SINGLE) {
            return TileEntityMerger.Type.SINGLE;
        }
        return chestType == ChestType.RIGHT ? TileEntityMerger.Type.FIRST : TileEntityMerger.Type.SECOND;
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        if (blockState.get(WATERLOGGED).booleanValue()) {
            iWorld.getPendingFluidTicks().scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickRate(iWorld));
        }
        if (blockState2.isIn(this) && direction.getAxis().isHorizontal()) {
            ChestType chestType = blockState2.get(TYPE);
            if (blockState.get(TYPE) == ChestType.SINGLE && chestType != ChestType.SINGLE && blockState.get(FACING) == blockState2.get(FACING) && ChestBlock.getDirectionToAttached(blockState2) == direction.getOpposite()) {
                return (BlockState)blockState.with(TYPE, chestType.opposite());
            }
        } else if (ChestBlock.getDirectionToAttached(blockState) == direction) {
            return (BlockState)blockState.with(TYPE, ChestType.SINGLE);
        }
        return super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        if (blockState.get(TYPE) == ChestType.SINGLE) {
            return SHAPE_SINGLE;
        }
        switch (4.$SwitchMap$net$minecraft$util$Direction[ChestBlock.getDirectionToAttached(blockState).ordinal()]) {
            default: {
                return SHAPE_NORTH;
            }
            case 2: {
                return SHAPE_SOUTH;
            }
            case 3: {
                return SHAPE_WEST;
            }
            case 4: 
        }
        return SHAPE_EAST;
    }

    public static Direction getDirectionToAttached(BlockState blockState) {
        Direction direction = blockState.get(FACING);
        return blockState.get(TYPE) == ChestType.LEFT ? direction.rotateY() : direction.rotateYCCW();
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        Direction direction;
        ChestType chestType = ChestType.SINGLE;
        Direction direction2 = blockItemUseContext.getPlacementHorizontalFacing().getOpposite();
        FluidState fluidState = blockItemUseContext.getWorld().getFluidState(blockItemUseContext.getPos());
        boolean bl = blockItemUseContext.hasSecondaryUseForPlayer();
        Direction direction3 = blockItemUseContext.getFace();
        if (direction3.getAxis().isHorizontal() && bl && (direction = this.getDirectionToAttach(blockItemUseContext, direction3.getOpposite())) != null && direction.getAxis() != direction3.getAxis()) {
            direction2 = direction;
            ChestType chestType2 = chestType = direction.rotateYCCW() == direction3.getOpposite() ? ChestType.RIGHT : ChestType.LEFT;
        }
        if (chestType == ChestType.SINGLE && !bl) {
            if (direction2 == this.getDirectionToAttach(blockItemUseContext, direction2.rotateY())) {
                chestType = ChestType.LEFT;
            } else if (direction2 == this.getDirectionToAttach(blockItemUseContext, direction2.rotateYCCW())) {
                chestType = ChestType.RIGHT;
            }
        }
        return (BlockState)((BlockState)((BlockState)this.getDefaultState().with(FACING, direction2)).with(TYPE, chestType)).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState blockState) {
        return blockState.get(WATERLOGGED) != false ? Fluids.WATER.getStillFluidState(true) : super.getFluidState(blockState);
    }

    @Nullable
    private Direction getDirectionToAttach(BlockItemUseContext blockItemUseContext, Direction direction) {
        BlockState blockState = blockItemUseContext.getWorld().getBlockState(blockItemUseContext.getPos().offset(direction));
        return blockState.isIn(this) && blockState.get(TYPE) == ChestType.SINGLE ? blockState.get(FACING) : null;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack) {
        TileEntity tileEntity;
        if (itemStack.hasDisplayName() && (tileEntity = world.getTileEntity(blockPos)) instanceof ChestTileEntity) {
            ((ChestTileEntity)tileEntity).setCustomName(itemStack.getDisplayName());
        }
    }

    @Override
    public void onReplaced(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState.isIn(blockState2.getBlock())) {
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof IInventory) {
                InventoryHelper.dropInventoryItems(world, blockPos, (IInventory)((Object)tileEntity));
                world.updateComparatorOutputLevel(blockPos, this);
            }
            super.onReplaced(blockState, world, blockPos, blockState2, bl);
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (world.isRemote) {
            return ActionResultType.SUCCESS;
        }
        INamedContainerProvider iNamedContainerProvider = this.getContainer(blockState, world, blockPos);
        if (iNamedContainerProvider != null) {
            playerEntity.openContainer(iNamedContainerProvider);
            playerEntity.addStat(this.getOpenStat());
            PiglinTasks.func_234478_a_(playerEntity, true);
        }
        return ActionResultType.CONSUME;
    }

    protected Stat<ResourceLocation> getOpenStat() {
        return Stats.CUSTOM.get(Stats.OPEN_CHEST);
    }

    @Nullable
    public static IInventory getChestInventory(ChestBlock chestBlock, BlockState blockState, World world, BlockPos blockPos, boolean bl) {
        return chestBlock.combine(blockState, world, blockPos, bl).apply(INVENTORY_MERGER).orElse(null);
    }

    @Override
    public TileEntityMerger.ICallbackWrapper<? extends ChestTileEntity> combine(BlockState blockState, World world, BlockPos blockPos, boolean bl) {
        BiPredicate<IWorld, BlockPos> biPredicate = bl ? ChestBlock::lambda$combine$0 : ChestBlock::isBlocked;
        return TileEntityMerger.func_226924_a_((TileEntityType)this.tileEntityType.get(), ChestBlock::getChestMergerType, ChestBlock::getDirectionToAttached, FACING, blockState, world, blockPos, biPredicate);
    }

    @Override
    @Nullable
    public INamedContainerProvider getContainer(BlockState blockState, World world, BlockPos blockPos) {
        return this.combine(blockState, world, blockPos, true).apply(CONTAINER_MERGER).orElse(null);
    }

    public static TileEntityMerger.ICallback<ChestTileEntity, Float2FloatFunction> getLidRotationCallback(IChestLid iChestLid) {
        return new TileEntityMerger.ICallback<ChestTileEntity, Float2FloatFunction>(iChestLid){
            final IChestLid val$lid;
            {
                this.val$lid = iChestLid;
            }

            @Override
            public Float2FloatFunction func_225539_a_(ChestTileEntity chestTileEntity, ChestTileEntity chestTileEntity2) {
                return arg_0 -> 3.lambda$func_225539_a_$0(chestTileEntity, chestTileEntity2, arg_0);
            }

            @Override
            public Float2FloatFunction func_225538_a_(ChestTileEntity chestTileEntity) {
                return chestTileEntity::getLidAngle;
            }

            @Override
            public Float2FloatFunction func_225537_b_() {
                return this.val$lid::getLidAngle;
            }

            @Override
            public Object func_225537_b_() {
                return this.func_225537_b_();
            }

            @Override
            public Object func_225538_a_(Object object) {
                return this.func_225538_a_((ChestTileEntity)object);
            }

            @Override
            public Object func_225539_a_(Object object, Object object2) {
                return this.func_225539_a_((ChestTileEntity)object, (ChestTileEntity)object2);
            }

            private static float lambda$func_225539_a_$0(ChestTileEntity chestTileEntity, ChestTileEntity chestTileEntity2, float f) {
                return Math.max(chestTileEntity.getLidAngle(f), chestTileEntity2.getLidAngle(f));
            }
        };
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new ChestTileEntity();
    }

    public static boolean isBlocked(IWorld iWorld, BlockPos blockPos) {
        return ChestBlock.isBelowSolidBlock(iWorld, blockPos) || ChestBlock.isCatSittingOn(iWorld, blockPos);
    }

    private static boolean isBelowSolidBlock(IBlockReader iBlockReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.up();
        return iBlockReader.getBlockState(blockPos2).isNormalCube(iBlockReader, blockPos2);
    }

    private static boolean isCatSittingOn(IWorld iWorld, BlockPos blockPos) {
        List<CatEntity> list = iWorld.getEntitiesWithinAABB(CatEntity.class, new AxisAlignedBB(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ(), blockPos.getX() + 1, blockPos.getY() + 2, blockPos.getZ() + 1));
        if (!list.isEmpty()) {
            for (CatEntity catEntity : list) {
                if (!catEntity.isSleeping()) continue;
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState blockState) {
        return false;
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World world, BlockPos blockPos) {
        return Container.calcRedstoneFromInventory(ChestBlock.getChestInventory(this, blockState, world, blockPos, false));
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
        builder.add(FACING, TYPE, WATERLOGGED);
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        return true;
    }

    private static boolean lambda$combine$0(IWorld iWorld, BlockPos blockPos) {
        return true;
    }
}

