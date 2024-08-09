/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BedPart;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.BedTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.TransportationHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ICollisionReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.apache.commons.lang3.ArrayUtils;

public class BedBlock
extends HorizontalBlock
implements ITileEntityProvider {
    public static final EnumProperty<BedPart> PART = BlockStateProperties.BED_PART;
    public static final BooleanProperty OCCUPIED = BlockStateProperties.OCCUPIED;
    protected static final VoxelShape BED_BASE_SHAPE = Block.makeCuboidShape(0.0, 3.0, 0.0, 16.0, 9.0, 16.0);
    protected static final VoxelShape CORNER_NW = Block.makeCuboidShape(0.0, 0.0, 0.0, 3.0, 3.0, 3.0);
    protected static final VoxelShape CORNER_SW = Block.makeCuboidShape(0.0, 0.0, 13.0, 3.0, 3.0, 16.0);
    protected static final VoxelShape CORNER_NE = Block.makeCuboidShape(13.0, 0.0, 0.0, 16.0, 3.0, 3.0);
    protected static final VoxelShape CORNER_SE = Block.makeCuboidShape(13.0, 0.0, 13.0, 16.0, 3.0, 16.0);
    protected static final VoxelShape NORTH_FACING_SHAPE = VoxelShapes.or(BED_BASE_SHAPE, CORNER_NW, CORNER_NE);
    protected static final VoxelShape SOUTH_FACING_SHAPE = VoxelShapes.or(BED_BASE_SHAPE, CORNER_SW, CORNER_SE);
    protected static final VoxelShape WEST_FACING_SHAPE = VoxelShapes.or(BED_BASE_SHAPE, CORNER_NW, CORNER_SW);
    protected static final VoxelShape EAST_FACING_SHAPE = VoxelShapes.or(BED_BASE_SHAPE, CORNER_NE, CORNER_SE);
    private final DyeColor color;

    public BedBlock(DyeColor dyeColor, AbstractBlock.Properties properties) {
        super(properties);
        this.color = dyeColor;
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(PART, BedPart.FOOT)).with(OCCUPIED, false));
    }

    @Nullable
    public static Direction getBedDirection(IBlockReader iBlockReader, BlockPos blockPos) {
        BlockState blockState = iBlockReader.getBlockState(blockPos);
        return blockState.getBlock() instanceof BedBlock ? blockState.get(HORIZONTAL_FACING) : null;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (world.isRemote) {
            return ActionResultType.CONSUME;
        }
        if (blockState.get(PART) != BedPart.HEAD && !(blockState = world.getBlockState(blockPos = blockPos.offset(blockState.get(HORIZONTAL_FACING)))).isIn(this)) {
            return ActionResultType.CONSUME;
        }
        if (!BedBlock.doesBedWork(world)) {
            world.removeBlock(blockPos, true);
            BlockPos blockPos2 = blockPos.offset(blockState.get(HORIZONTAL_FACING).getOpposite());
            if (world.getBlockState(blockPos2).isIn(this)) {
                world.removeBlock(blockPos2, true);
            }
            world.createExplosion(null, DamageSource.func_233546_a_(), null, (double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5, 5.0f, true, Explosion.Mode.DESTROY);
            return ActionResultType.SUCCESS;
        }
        if (blockState.get(OCCUPIED).booleanValue()) {
            if (!this.tryWakeUpVillager(world, blockPos)) {
                playerEntity.sendStatusMessage(new TranslationTextComponent("block.minecraft.bed.occupied"), false);
            }
            return ActionResultType.SUCCESS;
        }
        playerEntity.trySleep(blockPos).ifLeft(arg_0 -> BedBlock.lambda$onBlockActivated$0(playerEntity, arg_0));
        return ActionResultType.SUCCESS;
    }

    public static boolean doesBedWork(World world) {
        return world.getDimensionType().doesBedWork();
    }

    private boolean tryWakeUpVillager(World world, BlockPos blockPos) {
        List<VillagerEntity> list = world.getEntitiesWithinAABB(VillagerEntity.class, new AxisAlignedBB(blockPos), LivingEntity::isSleeping);
        if (list.isEmpty()) {
            return true;
        }
        list.get(0).wakeUp();
        return false;
    }

    @Override
    public void onFallenUpon(World world, BlockPos blockPos, Entity entity2, float f) {
        super.onFallenUpon(world, blockPos, entity2, f * 0.5f);
    }

    @Override
    public void onLanded(IBlockReader iBlockReader, Entity entity2) {
        if (entity2.isSuppressingBounce()) {
            super.onLanded(iBlockReader, entity2);
        } else {
            this.bounceEntity(entity2);
        }
    }

    private void bounceEntity(Entity entity2) {
        Vector3d vector3d = entity2.getMotion();
        if (vector3d.y < 0.0) {
            double d = entity2 instanceof LivingEntity ? 1.0 : 0.8;
            entity2.setMotion(vector3d.x, -vector3d.y * (double)0.66f * d, vector3d.z);
        }
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        if (direction == BedBlock.getDirectionToOther(blockState.get(PART), blockState.get(HORIZONTAL_FACING))) {
            return blockState2.isIn(this) && blockState2.get(PART) != blockState.get(PART) ? (BlockState)blockState.with(OCCUPIED, blockState2.get(OCCUPIED)) : Blocks.AIR.getDefaultState();
        }
        return super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    private static Direction getDirectionToOther(BedPart bedPart, Direction direction) {
        return bedPart == BedPart.FOOT ? direction : direction.getOpposite();
    }

    @Override
    public void onBlockHarvested(World world, BlockPos blockPos, BlockState blockState, PlayerEntity playerEntity) {
        BlockPos blockPos2;
        BlockState blockState2;
        BedPart bedPart;
        if (!world.isRemote && playerEntity.isCreative() && (bedPart = blockState.get(PART)) == BedPart.FOOT && (blockState2 = world.getBlockState(blockPos2 = blockPos.offset(BedBlock.getDirectionToOther(bedPart, blockState.get(HORIZONTAL_FACING))))).getBlock() == this && blockState2.get(PART) == BedPart.HEAD) {
            world.setBlockState(blockPos2, Blocks.AIR.getDefaultState(), 0);
            world.playEvent(playerEntity, 2001, blockPos2, Block.getStateId(blockState2));
        }
        super.onBlockHarvested(world, blockPos, blockState, playerEntity);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        Direction direction = blockItemUseContext.getPlacementHorizontalFacing();
        BlockPos blockPos = blockItemUseContext.getPos();
        BlockPos blockPos2 = blockPos.offset(direction);
        return blockItemUseContext.getWorld().getBlockState(blockPos2).isReplaceable(blockItemUseContext) ? (BlockState)this.getDefaultState().with(HORIZONTAL_FACING, direction) : null;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        Direction direction = BedBlock.getFootDirection(blockState).getOpposite();
        switch (1.$SwitchMap$net$minecraft$util$Direction[direction.ordinal()]) {
            case 1: {
                return NORTH_FACING_SHAPE;
            }
            case 2: {
                return SOUTH_FACING_SHAPE;
            }
            case 3: {
                return WEST_FACING_SHAPE;
            }
        }
        return EAST_FACING_SHAPE;
    }

    public static Direction getFootDirection(BlockState blockState) {
        Direction direction = blockState.get(HORIZONTAL_FACING);
        return blockState.get(PART) == BedPart.HEAD ? direction.getOpposite() : direction;
    }

    public static TileEntityMerger.Type getMergeType(BlockState blockState) {
        BedPart bedPart = blockState.get(PART);
        return bedPart == BedPart.HEAD ? TileEntityMerger.Type.FIRST : TileEntityMerger.Type.SECOND;
    }

    private static boolean isBedBelow(IBlockReader iBlockReader, BlockPos blockPos) {
        return iBlockReader.getBlockState(blockPos.down()).getBlock() instanceof BedBlock;
    }

    public static Optional<Vector3d> func_242652_a(EntityType<?> entityType, ICollisionReader iCollisionReader, BlockPos blockPos, float f) {
        Direction direction;
        Direction direction2 = iCollisionReader.getBlockState(blockPos).get(HORIZONTAL_FACING);
        Direction direction3 = direction2.rotateY();
        Direction direction4 = direction = direction3.hasOrientation(f) ? direction3.getOpposite() : direction3;
        if (BedBlock.isBedBelow(iCollisionReader, blockPos)) {
            return BedBlock.func_242653_a(entityType, iCollisionReader, blockPos, direction2, direction);
        }
        int[][] nArray = BedBlock.func_242656_a(direction2, direction);
        Optional<Vector3d> optional = BedBlock.func_242654_a(entityType, iCollisionReader, blockPos, nArray, true);
        return optional.isPresent() ? optional : BedBlock.func_242654_a(entityType, iCollisionReader, blockPos, nArray, false);
    }

    private static Optional<Vector3d> func_242653_a(EntityType<?> entityType, ICollisionReader iCollisionReader, BlockPos blockPos, Direction direction, Direction direction2) {
        int[][] nArray = BedBlock.func_242658_b(direction, direction2);
        Optional<Vector3d> optional = BedBlock.func_242654_a(entityType, iCollisionReader, blockPos, nArray, true);
        if (optional.isPresent()) {
            return optional;
        }
        BlockPos blockPos2 = blockPos.down();
        Optional<Vector3d> optional2 = BedBlock.func_242654_a(entityType, iCollisionReader, blockPos2, nArray, true);
        if (optional2.isPresent()) {
            return optional2;
        }
        int[][] nArray2 = BedBlock.func_242655_a(direction);
        Optional<Vector3d> optional3 = BedBlock.func_242654_a(entityType, iCollisionReader, blockPos, nArray2, true);
        if (optional3.isPresent()) {
            return optional3;
        }
        Optional<Vector3d> optional4 = BedBlock.func_242654_a(entityType, iCollisionReader, blockPos, nArray, false);
        if (optional4.isPresent()) {
            return optional4;
        }
        Optional<Vector3d> optional5 = BedBlock.func_242654_a(entityType, iCollisionReader, blockPos2, nArray, false);
        return optional5.isPresent() ? optional5 : BedBlock.func_242654_a(entityType, iCollisionReader, blockPos, nArray2, false);
    }

    private static Optional<Vector3d> func_242654_a(EntityType<?> entityType, ICollisionReader iCollisionReader, BlockPos blockPos, int[][] nArray, boolean bl) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int[] nArray2 : nArray) {
            mutable.setPos(blockPos.getX() + nArray2[0], blockPos.getY(), blockPos.getZ() + nArray2[1]);
            Vector3d vector3d = TransportationHelper.func_242379_a(entityType, iCollisionReader, mutable, bl);
            if (vector3d == null) continue;
            return Optional.of(vector3d);
        }
        return Optional.empty();
    }

    @Override
    public PushReaction getPushReaction(BlockState blockState) {
        return PushReaction.DESTROY;
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, PART, OCCUPIED);
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new BedTileEntity(this.color);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        super.onBlockPlacedBy(world, blockPos, blockState, livingEntity, itemStack);
        if (!world.isRemote) {
            BlockPos blockPos2 = blockPos.offset(blockState.get(HORIZONTAL_FACING));
            world.setBlockState(blockPos2, (BlockState)blockState.with(PART, BedPart.HEAD), 0);
            world.func_230547_a_(blockPos, Blocks.AIR);
            blockState.updateNeighbours(world, blockPos, 3);
        }
    }

    public DyeColor getColor() {
        return this.color;
    }

    @Override
    public long getPositionRandom(BlockState blockState, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.offset(blockState.get(HORIZONTAL_FACING), blockState.get(PART) == BedPart.HEAD ? 0 : 1);
        return MathHelper.getCoordinateRandom(blockPos2.getX(), blockPos.getY(), blockPos2.getZ());
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        return true;
    }

    private static int[][] func_242656_a(Direction direction, Direction direction2) {
        return (int[][])ArrayUtils.addAll(BedBlock.func_242658_b(direction, direction2), BedBlock.func_242655_a(direction));
    }

    private static int[][] func_242658_b(Direction direction, Direction direction2) {
        return new int[][]{{direction2.getXOffset(), direction2.getZOffset()}, {direction2.getXOffset() - direction.getXOffset(), direction2.getZOffset() - direction.getZOffset()}, {direction2.getXOffset() - direction.getXOffset() * 2, direction2.getZOffset() - direction.getZOffset() * 2}, {-direction.getXOffset() * 2, -direction.getZOffset() * 2}, {-direction2.getXOffset() - direction.getXOffset() * 2, -direction2.getZOffset() - direction.getZOffset() * 2}, {-direction2.getXOffset() - direction.getXOffset(), -direction2.getZOffset() - direction.getZOffset()}, {-direction2.getXOffset(), -direction2.getZOffset()}, {-direction2.getXOffset() + direction.getXOffset(), -direction2.getZOffset() + direction.getZOffset()}, {direction.getXOffset(), direction.getZOffset()}, {direction2.getXOffset() + direction.getXOffset(), direction2.getZOffset() + direction.getZOffset()}};
    }

    private static int[][] func_242655_a(Direction direction) {
        return new int[][]{{0, 0}, {-direction.getXOffset(), -direction.getZOffset()}};
    }

    private static void lambda$onBlockActivated$0(PlayerEntity playerEntity, PlayerEntity.SleepResult sleepResult) {
        if (sleepResult != null) {
            playerEntity.sendStatusMessage(sleepResult.getMessage(), false);
        }
    }
}

