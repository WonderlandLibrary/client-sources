/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.PistonHeadBlock;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.PistonType;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.AabbHelper;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;

public class PistonTileEntity
extends TileEntity
implements ITickableTileEntity {
    private BlockState pistonState;
    private Direction pistonFacing;
    private boolean extending;
    private boolean shouldHeadBeRendered;
    private static final ThreadLocal<Direction> MOVING_ENTITY = ThreadLocal.withInitial(PistonTileEntity::lambda$static$0);
    private float progress;
    private float lastProgress;
    private long lastTicked;
    private int field_242697_l;

    public PistonTileEntity() {
        super(TileEntityType.PISTON);
    }

    public PistonTileEntity(BlockState blockState, Direction direction, boolean bl, boolean bl2) {
        this();
        this.pistonState = blockState;
        this.pistonFacing = direction;
        this.extending = bl;
        this.shouldHeadBeRendered = bl2;
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    public boolean isExtending() {
        return this.extending;
    }

    public Direction getFacing() {
        return this.pistonFacing;
    }

    public boolean shouldPistonHeadBeRendered() {
        return this.shouldHeadBeRendered;
    }

    public float getProgress(float f) {
        if (f > 1.0f) {
            f = 1.0f;
        }
        return MathHelper.lerp(f, this.lastProgress, this.progress);
    }

    public float getOffsetX(float f) {
        return (float)this.pistonFacing.getXOffset() * this.getExtendedProgress(this.getProgress(f));
    }

    public float getOffsetY(float f) {
        return (float)this.pistonFacing.getYOffset() * this.getExtendedProgress(this.getProgress(f));
    }

    public float getOffsetZ(float f) {
        return (float)this.pistonFacing.getZOffset() * this.getExtendedProgress(this.getProgress(f));
    }

    private float getExtendedProgress(float f) {
        return this.extending ? f - 1.0f : 1.0f - f;
    }

    private BlockState getCollisionRelatedBlockState() {
        return !this.isExtending() && this.shouldPistonHeadBeRendered() && this.pistonState.getBlock() instanceof PistonBlock ? (BlockState)((BlockState)((BlockState)Blocks.PISTON_HEAD.getDefaultState().with(PistonHeadBlock.SHORT, this.progress > 0.25f)).with(PistonHeadBlock.TYPE, this.pistonState.isIn(Blocks.STICKY_PISTON) ? PistonType.STICKY : PistonType.DEFAULT)).with(PistonHeadBlock.FACING, this.pistonState.get(PistonBlock.FACING)) : this.pistonState;
    }

    private void moveCollidedEntities(float f) {
        AxisAlignedBB axisAlignedBB;
        List<Entity> list;
        Direction direction = this.getMotionDirection();
        double d = f - this.progress;
        VoxelShape voxelShape = this.getCollisionRelatedBlockState().getCollisionShape(this.world, this.getPos());
        if (!voxelShape.isEmpty() && !(list = this.world.getEntitiesWithinAABBExcludingEntity(null, AabbHelper.func_227019_a_(axisAlignedBB = this.moveByPositionAndProgress(voxelShape.getBoundingBox()), direction, d).union(axisAlignedBB))).isEmpty()) {
            List<AxisAlignedBB> list2 = voxelShape.toBoundingBoxList();
            boolean bl = this.pistonState.isIn(Blocks.SLIME_BLOCK);
            Iterator<Entity> iterator2 = list.iterator();
            while (true) {
                AxisAlignedBB axisAlignedBB2;
                AxisAlignedBB axisAlignedBB3;
                AxisAlignedBB axisAlignedBB4;
                if (!iterator2.hasNext()) {
                    return;
                }
                Entity entity2 = iterator2.next();
                if (entity2.getPushReaction() == PushReaction.IGNORE) continue;
                if (bl) {
                    if (entity2 instanceof ServerPlayerEntity) continue;
                    Vector3d vector3d = entity2.getMotion();
                    double d2 = vector3d.x;
                    double d3 = vector3d.y;
                    double d4 = vector3d.z;
                    switch (direction.getAxis()) {
                        case X: {
                            d2 = direction.getXOffset();
                            break;
                        }
                        case Y: {
                            d3 = direction.getYOffset();
                            break;
                        }
                        case Z: {
                            d4 = direction.getZOffset();
                        }
                    }
                    entity2.setMotion(d2, d3, d4);
                }
                double d5 = 0.0;
                Iterator<AxisAlignedBB> iterator3 = list2.iterator();
                while (!(!iterator3.hasNext() || (axisAlignedBB4 = AabbHelper.func_227019_a_(this.moveByPositionAndProgress(axisAlignedBB3 = iterator3.next()), direction, d)).intersects(axisAlignedBB2 = entity2.getBoundingBox()) && (d5 = Math.max(d5, PistonTileEntity.getMovement(axisAlignedBB4, direction, axisAlignedBB2))) >= d)) {
                }
                if (d5 <= 0.0) continue;
                d5 = Math.min(d5, d) + 0.01;
                PistonTileEntity.func_227022_a_(direction, entity2, d5, direction);
                if (this.extending || !this.shouldHeadBeRendered) continue;
                this.fixEntityWithinPistonBase(entity2, direction, d);
            }
        }
    }

    private static void func_227022_a_(Direction direction, Entity entity2, double d, Direction direction2) {
        MOVING_ENTITY.set(direction);
        entity2.move(MoverType.PISTON, new Vector3d(d * (double)direction2.getXOffset(), d * (double)direction2.getYOffset(), d * (double)direction2.getZOffset()));
        MOVING_ENTITY.set(null);
    }

    private void func_227024_g_(float f) {
        Direction direction;
        if (this.func_227025_y_() && (direction = this.getMotionDirection()).getAxis().isHorizontal()) {
            double d = this.pistonState.getCollisionShape(this.world, this.pos).getEnd(Direction.Axis.Y);
            AxisAlignedBB axisAlignedBB = this.moveByPositionAndProgress(new AxisAlignedBB(0.0, d, 0.0, 1.0, 1.5000000999999998, 1.0));
            double d2 = f - this.progress;
            for (Entity entity2 : this.world.getEntitiesInAABBexcluding(null, axisAlignedBB, arg_0 -> PistonTileEntity.lambda$func_227024_g_$1(axisAlignedBB, arg_0))) {
                PistonTileEntity.func_227022_a_(direction, entity2, d2, direction);
            }
        }
    }

    private static boolean func_227021_a_(AxisAlignedBB axisAlignedBB, Entity entity2) {
        return entity2.getPushReaction() == PushReaction.NORMAL && entity2.isOnGround() && entity2.getPosX() >= axisAlignedBB.minX && entity2.getPosX() <= axisAlignedBB.maxX && entity2.getPosZ() >= axisAlignedBB.minZ && entity2.getPosZ() <= axisAlignedBB.maxZ;
    }

    private boolean func_227025_y_() {
        return this.pistonState.isIn(Blocks.HONEY_BLOCK);
    }

    public Direction getMotionDirection() {
        return this.extending ? this.pistonFacing : this.pistonFacing.getOpposite();
    }

    private static double getMovement(AxisAlignedBB axisAlignedBB, Direction direction, AxisAlignedBB axisAlignedBB2) {
        switch (direction) {
            case EAST: {
                return axisAlignedBB.maxX - axisAlignedBB2.minX;
            }
            case WEST: {
                return axisAlignedBB2.maxX - axisAlignedBB.minX;
            }
            default: {
                return axisAlignedBB.maxY - axisAlignedBB2.minY;
            }
            case DOWN: {
                return axisAlignedBB2.maxY - axisAlignedBB.minY;
            }
            case SOUTH: {
                return axisAlignedBB.maxZ - axisAlignedBB2.minZ;
            }
            case NORTH: 
        }
        return axisAlignedBB2.maxZ - axisAlignedBB.minZ;
    }

    private AxisAlignedBB moveByPositionAndProgress(AxisAlignedBB axisAlignedBB) {
        double d = this.getExtendedProgress(this.progress);
        return axisAlignedBB.offset((double)this.pos.getX() + d * (double)this.pistonFacing.getXOffset(), (double)this.pos.getY() + d * (double)this.pistonFacing.getYOffset(), (double)this.pos.getZ() + d * (double)this.pistonFacing.getZOffset());
    }

    private void fixEntityWithinPistonBase(Entity entity2, Direction direction, double d) {
        double d2;
        Direction direction2;
        double d3;
        AxisAlignedBB axisAlignedBB;
        AxisAlignedBB axisAlignedBB2 = entity2.getBoundingBox();
        if (axisAlignedBB2.intersects(axisAlignedBB = VoxelShapes.fullCube().getBoundingBox().offset(this.pos)) && Math.abs((d3 = PistonTileEntity.getMovement(axisAlignedBB, direction2 = direction.getOpposite(), axisAlignedBB2) + 0.01) - (d2 = PistonTileEntity.getMovement(axisAlignedBB, direction2, axisAlignedBB2.intersect(axisAlignedBB)) + 0.01)) < 0.01) {
            d3 = Math.min(d3, d) + 0.01;
            PistonTileEntity.func_227022_a_(direction, entity2, d3, direction2);
        }
    }

    public BlockState getPistonState() {
        return this.pistonState;
    }

    public void clearPistonTileEntity() {
        if (this.world != null && (this.lastProgress < 1.0f || this.world.isRemote)) {
            this.lastProgress = this.progress = 1.0f;
            this.world.removeTileEntity(this.pos);
            this.remove();
            if (this.world.getBlockState(this.pos).isIn(Blocks.MOVING_PISTON)) {
                BlockState blockState = this.shouldHeadBeRendered ? Blocks.AIR.getDefaultState() : Block.getValidBlockForPosition(this.pistonState, this.world, this.pos);
                this.world.setBlockState(this.pos, blockState, 0);
                this.world.neighborChanged(this.pos, blockState.getBlock(), this.pos);
            }
        }
    }

    @Override
    public void tick() {
        this.lastTicked = this.world.getGameTime();
        this.lastProgress = this.progress;
        if (this.lastProgress >= 1.0f) {
            if (this.world.isRemote && this.field_242697_l < 5) {
                ++this.field_242697_l;
            } else {
                this.world.removeTileEntity(this.pos);
                this.remove();
                if (this.pistonState != null && this.world.getBlockState(this.pos).isIn(Blocks.MOVING_PISTON)) {
                    BlockState blockState = Block.getValidBlockForPosition(this.pistonState, this.world, this.pos);
                    if (blockState.isAir()) {
                        this.world.setBlockState(this.pos, this.pistonState, 1);
                        Block.replaceBlock(this.pistonState, blockState, this.world, this.pos, 3);
                    } else {
                        if (blockState.hasProperty(BlockStateProperties.WATERLOGGED) && blockState.get(BlockStateProperties.WATERLOGGED).booleanValue()) {
                            blockState = (BlockState)blockState.with(BlockStateProperties.WATERLOGGED, false);
                        }
                        this.world.setBlockState(this.pos, blockState, 0);
                        this.world.neighborChanged(this.pos, blockState.getBlock(), this.pos);
                    }
                }
            }
        } else {
            float f = this.progress + 0.5f;
            this.moveCollidedEntities(f);
            this.func_227024_g_(f);
            this.progress = f;
            if (this.progress >= 1.0f) {
                this.progress = 1.0f;
            }
        }
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compoundNBT) {
        super.read(blockState, compoundNBT);
        this.pistonState = NBTUtil.readBlockState(compoundNBT.getCompound("blockState"));
        this.pistonFacing = Direction.byIndex(compoundNBT.getInt("facing"));
        this.lastProgress = this.progress = compoundNBT.getFloat("progress");
        this.extending = compoundNBT.getBoolean("extending");
        this.shouldHeadBeRendered = compoundNBT.getBoolean("source");
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        super.write(compoundNBT);
        compoundNBT.put("blockState", NBTUtil.writeBlockState(this.pistonState));
        compoundNBT.putInt("facing", this.pistonFacing.getIndex());
        compoundNBT.putFloat("progress", this.lastProgress);
        compoundNBT.putBoolean("extending", this.extending);
        compoundNBT.putBoolean("source", this.shouldHeadBeRendered);
        return compoundNBT;
    }

    public VoxelShape getCollisionShape(IBlockReader iBlockReader, BlockPos blockPos) {
        VoxelShape voxelShape = !this.extending && this.shouldHeadBeRendered ? ((BlockState)this.pistonState.with(PistonBlock.EXTENDED, true)).getCollisionShape(iBlockReader, blockPos) : VoxelShapes.empty();
        Direction direction = MOVING_ENTITY.get();
        if ((double)this.progress < 1.0 && direction == this.getMotionDirection()) {
            return voxelShape;
        }
        BlockState blockState = this.shouldPistonHeadBeRendered() ? (BlockState)((BlockState)Blocks.PISTON_HEAD.getDefaultState().with(PistonHeadBlock.FACING, this.pistonFacing)).with(PistonHeadBlock.SHORT, this.extending != 1.0f - this.progress < 0.25f) : this.pistonState;
        float f = this.getExtendedProgress(this.progress);
        double d = (float)this.pistonFacing.getXOffset() * f;
        double d2 = (float)this.pistonFacing.getYOffset() * f;
        double d3 = (float)this.pistonFacing.getZOffset() * f;
        return VoxelShapes.or(voxelShape, blockState.getCollisionShape(iBlockReader, blockPos).withOffset(d, d2, d3));
    }

    public long getLastTicked() {
        return this.lastTicked;
    }

    @Override
    public double getMaxRenderDistanceSquared() {
        return 68.0;
    }

    private static boolean lambda$func_227024_g_$1(AxisAlignedBB axisAlignedBB, Entity entity2) {
        return PistonTileEntity.func_227021_a_(axisAlignedBB, entity2);
    }

    private static Direction lambda$static$0() {
        return null;
    }
}

