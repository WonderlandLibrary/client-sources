/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.pathfinding;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.MobEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.pathfinding.FlaggedPathPoint;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.pathfinding.PathType;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.Region;

public class WalkNodeProcessor
extends NodeProcessor {
    protected float avoidsWater;
    private final Long2ObjectMap<PathNodeType> field_237226_k_ = new Long2ObjectOpenHashMap<PathNodeType>();
    private final Object2BooleanMap<AxisAlignedBB> field_237227_l_ = new Object2BooleanOpenHashMap<AxisAlignedBB>();

    @Override
    public void func_225578_a_(Region region, MobEntity mobEntity) {
        super.func_225578_a_(region, mobEntity);
        this.avoidsWater = mobEntity.getPathPriority(PathNodeType.WATER);
    }

    @Override
    public void postProcess() {
        this.entity.setPathPriority(PathNodeType.WATER, this.avoidsWater);
        this.field_237226_k_.clear();
        this.field_237227_l_.clear();
        super.postProcess();
    }

    @Override
    public PathPoint getStart() {
        Object object;
        BlockPos blockPos;
        int n;
        BlockPos.Mutable mutable;
        block11: {
            mutable = new BlockPos.Mutable();
            n = MathHelper.floor(this.entity.getPosY());
            BlockState blockState = this.blockaccess.getBlockState(mutable.setPos(this.entity.getPosX(), (double)n, this.entity.getPosZ()));
            if (!this.entity.func_230285_a_(blockState.getFluidState().getFluid())) {
                if (this.getCanSwim() && this.entity.isInWater()) {
                    while (true) {
                        if (blockState.getBlock() != Blocks.WATER && blockState.getFluidState() != Fluids.WATER.getStillFluidState(true)) {
                            --n;
                            break block11;
                        }
                        blockState = this.blockaccess.getBlockState(mutable.setPos(this.entity.getPosX(), (double)(++n), this.entity.getPosZ()));
                    }
                }
                if (this.entity.isOnGround()) {
                    n = MathHelper.floor(this.entity.getPosY() + 0.5);
                } else {
                    blockPos = this.entity.getPosition();
                    while ((this.blockaccess.getBlockState(blockPos).isAir() || this.blockaccess.getBlockState(blockPos).allowsMovement(this.blockaccess, blockPos, PathType.LAND)) && blockPos.getY() > 0) {
                        blockPos = blockPos.down();
                    }
                    n = blockPos.up().getY();
                }
            } else {
                while (this.entity.func_230285_a_(blockState.getFluidState().getFluid())) {
                    blockState = this.blockaccess.getBlockState(mutable.setPos(this.entity.getPosX(), (double)(++n), this.entity.getPosZ()));
                }
                --n;
            }
        }
        blockPos = this.entity.getPosition();
        PathNodeType pathNodeType = this.func_237230_a_(this.entity, blockPos.getX(), n, blockPos.getZ());
        if (this.entity.getPathPriority(pathNodeType) < 0.0f) {
            object = this.entity.getBoundingBox();
            if (this.func_237239_b_(mutable.setPos(((AxisAlignedBB)object).minX, (double)n, ((AxisAlignedBB)object).minZ)) || this.func_237239_b_(mutable.setPos(((AxisAlignedBB)object).minX, (double)n, ((AxisAlignedBB)object).maxZ)) || this.func_237239_b_(mutable.setPos(((AxisAlignedBB)object).maxX, (double)n, ((AxisAlignedBB)object).minZ)) || this.func_237239_b_(mutable.setPos(((AxisAlignedBB)object).maxX, (double)n, ((AxisAlignedBB)object).maxZ))) {
                PathPoint pathPoint = this.func_237223_a_(mutable);
                pathPoint.nodeType = this.getPathNodeType(this.entity, pathPoint.func_224759_a());
                pathPoint.costMalus = this.entity.getPathPriority(pathPoint.nodeType);
                return pathPoint;
            }
        }
        object = this.openPoint(blockPos.getX(), n, blockPos.getZ());
        ((PathPoint)object).nodeType = this.getPathNodeType(this.entity, ((PathPoint)object).func_224759_a());
        ((PathPoint)object).costMalus = this.entity.getPathPriority(((PathPoint)object).nodeType);
        return object;
    }

    private boolean func_237239_b_(BlockPos blockPos) {
        PathNodeType pathNodeType = this.getPathNodeType(this.entity, blockPos);
        return this.entity.getPathPriority(pathNodeType) >= 0.0f;
    }

    @Override
    public FlaggedPathPoint func_224768_a(double d, double d2, double d3) {
        return new FlaggedPathPoint(this.openPoint(MathHelper.floor(d), MathHelper.floor(d2), MathHelper.floor(d3)));
    }

    @Override
    public int func_222859_a(PathPoint[] pathPointArray, PathPoint pathPoint) {
        PathPoint pathPoint2;
        PathPoint pathPoint3;
        PathPoint pathPoint4;
        PathPoint pathPoint5;
        PathPoint pathPoint6;
        PathPoint pathPoint7;
        PathPoint pathPoint8;
        double d;
        PathPoint pathPoint9;
        int n = 0;
        int n2 = 0;
        PathNodeType pathNodeType = this.func_237230_a_(this.entity, pathPoint.x, pathPoint.y + 1, pathPoint.z);
        PathNodeType pathNodeType2 = this.func_237230_a_(this.entity, pathPoint.x, pathPoint.y, pathPoint.z);
        if (this.entity.getPathPriority(pathNodeType) >= 0.0f && pathNodeType2 != PathNodeType.STICKY_HONEY) {
            n2 = MathHelper.floor(Math.max(1.0f, this.entity.stepHeight));
        }
        if (this.func_237235_a_(pathPoint9 = this.getSafePoint(pathPoint.x, pathPoint.y, pathPoint.z + 1, n2, d = WalkNodeProcessor.getGroundY(this.blockaccess, new BlockPos(pathPoint.x, pathPoint.y, pathPoint.z)), Direction.SOUTH, pathNodeType2), pathPoint)) {
            pathPointArray[n++] = pathPoint9;
        }
        if (this.func_237235_a_(pathPoint8 = this.getSafePoint(pathPoint.x - 1, pathPoint.y, pathPoint.z, n2, d, Direction.WEST, pathNodeType2), pathPoint)) {
            pathPointArray[n++] = pathPoint8;
        }
        if (this.func_237235_a_(pathPoint7 = this.getSafePoint(pathPoint.x + 1, pathPoint.y, pathPoint.z, n2, d, Direction.EAST, pathNodeType2), pathPoint)) {
            pathPointArray[n++] = pathPoint7;
        }
        if (this.func_237235_a_(pathPoint6 = this.getSafePoint(pathPoint.x, pathPoint.y, pathPoint.z - 1, n2, d, Direction.NORTH, pathNodeType2), pathPoint)) {
            pathPointArray[n++] = pathPoint6;
        }
        if (this.func_222860_a(pathPoint, pathPoint8, pathPoint6, pathPoint5 = this.getSafePoint(pathPoint.x - 1, pathPoint.y, pathPoint.z - 1, n2, d, Direction.NORTH, pathNodeType2))) {
            pathPointArray[n++] = pathPoint5;
        }
        if (this.func_222860_a(pathPoint, pathPoint7, pathPoint6, pathPoint4 = this.getSafePoint(pathPoint.x + 1, pathPoint.y, pathPoint.z - 1, n2, d, Direction.NORTH, pathNodeType2))) {
            pathPointArray[n++] = pathPoint4;
        }
        if (this.func_222860_a(pathPoint, pathPoint8, pathPoint9, pathPoint3 = this.getSafePoint(pathPoint.x - 1, pathPoint.y, pathPoint.z + 1, n2, d, Direction.SOUTH, pathNodeType2))) {
            pathPointArray[n++] = pathPoint3;
        }
        if (this.func_222860_a(pathPoint, pathPoint7, pathPoint9, pathPoint2 = this.getSafePoint(pathPoint.x + 1, pathPoint.y, pathPoint.z + 1, n2, d, Direction.SOUTH, pathNodeType2))) {
            pathPointArray[n++] = pathPoint2;
        }
        return n;
    }

    private boolean func_237235_a_(PathPoint pathPoint, PathPoint pathPoint2) {
        return pathPoint != null && !pathPoint.visited && (pathPoint.costMalus >= 0.0f || pathPoint2.costMalus < 0.0f);
    }

    private boolean func_222860_a(PathPoint pathPoint, @Nullable PathPoint pathPoint2, @Nullable PathPoint pathPoint3, @Nullable PathPoint pathPoint4) {
        if (pathPoint4 != null && pathPoint3 != null && pathPoint2 != null) {
            if (pathPoint4.visited) {
                return true;
            }
            if (pathPoint3.y <= pathPoint.y && pathPoint2.y <= pathPoint.y) {
                if (pathPoint2.nodeType != PathNodeType.WALKABLE_DOOR && pathPoint3.nodeType != PathNodeType.WALKABLE_DOOR && pathPoint4.nodeType != PathNodeType.WALKABLE_DOOR) {
                    boolean bl = pathPoint3.nodeType == PathNodeType.FENCE && pathPoint2.nodeType == PathNodeType.FENCE && (double)this.entity.getWidth() < 0.5;
                    return pathPoint4.costMalus >= 0.0f && (pathPoint3.y < pathPoint.y || pathPoint3.costMalus >= 0.0f || bl) && (pathPoint2.y < pathPoint.y || pathPoint2.costMalus >= 0.0f || bl);
                }
                return true;
            }
            return true;
        }
        return true;
    }

    private boolean func_237234_a_(PathPoint pathPoint) {
        Vector3d vector3d = new Vector3d((double)pathPoint.x - this.entity.getPosX(), (double)pathPoint.y - this.entity.getPosY(), (double)pathPoint.z - this.entity.getPosZ());
        AxisAlignedBB axisAlignedBB = this.entity.getBoundingBox();
        int n = MathHelper.ceil(vector3d.length() / axisAlignedBB.getAverageEdgeLength());
        vector3d = vector3d.scale(1.0f / (float)n);
        for (int i = 1; i <= n; ++i) {
            if (!this.func_237236_a_(axisAlignedBB = axisAlignedBB.offset(vector3d))) continue;
            return true;
        }
        return false;
    }

    public static double getGroundY(IBlockReader iBlockReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.down();
        VoxelShape voxelShape = iBlockReader.getBlockState(blockPos2).getCollisionShape(iBlockReader, blockPos2);
        return (double)blockPos2.getY() + (voxelShape.isEmpty() ? 0.0 : voxelShape.getEnd(Direction.Axis.Y));
    }

    @Nullable
    private PathPoint getSafePoint(int n, int n2, int n3, int n4, double d, Direction direction, PathNodeType pathNodeType) {
        double d2;
        double d3;
        AxisAlignedBB axisAlignedBB;
        PathPoint pathPoint = null;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        double d4 = WalkNodeProcessor.getGroundY(this.blockaccess, mutable.setPos(n, n2, n3));
        if (d4 - d > 1.125) {
            return null;
        }
        PathNodeType pathNodeType2 = this.func_237230_a_(this.entity, n, n2, n3);
        float f = this.entity.getPathPriority(pathNodeType2);
        double d5 = (double)this.entity.getWidth() / 2.0;
        if (f >= 0.0f) {
            pathPoint = this.openPoint(n, n2, n3);
            pathPoint.nodeType = pathNodeType2;
            pathPoint.costMalus = Math.max(pathPoint.costMalus, f);
        }
        if (pathNodeType == PathNodeType.FENCE && pathPoint != null && pathPoint.costMalus >= 0.0f && !this.func_237234_a_(pathPoint)) {
            pathPoint = null;
        }
        if (pathNodeType2 == PathNodeType.WALKABLE) {
            return pathPoint;
        }
        if ((pathPoint == null || pathPoint.costMalus < 0.0f) && n4 > 0 && pathNodeType2 != PathNodeType.FENCE && pathNodeType2 != PathNodeType.UNPASSABLE_RAIL && pathNodeType2 != PathNodeType.TRAPDOOR && (pathPoint = this.getSafePoint(n, n2 + 1, n3, n4 - 1, d, direction, pathNodeType)) != null && (pathPoint.nodeType == PathNodeType.OPEN || pathPoint.nodeType == PathNodeType.WALKABLE) && this.entity.getWidth() < 1.0f && this.func_237236_a_(axisAlignedBB = new AxisAlignedBB((d3 = (double)(n - direction.getXOffset()) + 0.5) - d5, WalkNodeProcessor.getGroundY(this.blockaccess, mutable.setPos(d3, (double)(n2 + 1), d2 = (double)(n3 - direction.getZOffset()) + 0.5)) + 0.001, d2 - d5, d3 + d5, (double)this.entity.getHeight() + WalkNodeProcessor.getGroundY(this.blockaccess, mutable.setPos((double)pathPoint.x, (double)pathPoint.y, (double)pathPoint.z)) - 0.002, d2 + d5))) {
            pathPoint = null;
        }
        if (pathNodeType2 == PathNodeType.WATER && !this.getCanSwim()) {
            if (this.func_237230_a_(this.entity, n, n2 - 1, n3) != PathNodeType.WATER) {
                return pathPoint;
            }
            while (n2 > 0) {
                if ((pathNodeType2 = this.func_237230_a_(this.entity, n, --n2, n3)) != PathNodeType.WATER) {
                    return pathPoint;
                }
                pathPoint = this.openPoint(n, n2, n3);
                pathPoint.nodeType = pathNodeType2;
                pathPoint.costMalus = Math.max(pathPoint.costMalus, this.entity.getPathPriority(pathNodeType2));
            }
        }
        if (pathNodeType2 == PathNodeType.OPEN) {
            int n5 = 0;
            int n6 = n2;
            while (pathNodeType2 == PathNodeType.OPEN) {
                if (--n2 < 0) {
                    PathPoint pathPoint2 = this.openPoint(n, n6, n3);
                    pathPoint2.nodeType = PathNodeType.BLOCKED;
                    pathPoint2.costMalus = -1.0f;
                    return pathPoint2;
                }
                if (n5++ >= this.entity.getMaxFallHeight()) {
                    PathPoint pathPoint3 = this.openPoint(n, n2, n3);
                    pathPoint3.nodeType = PathNodeType.BLOCKED;
                    pathPoint3.costMalus = -1.0f;
                    return pathPoint3;
                }
                pathNodeType2 = this.func_237230_a_(this.entity, n, n2, n3);
                f = this.entity.getPathPriority(pathNodeType2);
                if (pathNodeType2 != PathNodeType.OPEN && f >= 0.0f) {
                    pathPoint = this.openPoint(n, n2, n3);
                    pathPoint.nodeType = pathNodeType2;
                    pathPoint.costMalus = Math.max(pathPoint.costMalus, f);
                    break;
                }
                if (!(f < 0.0f)) continue;
                PathPoint pathPoint4 = this.openPoint(n, n2, n3);
                pathPoint4.nodeType = PathNodeType.BLOCKED;
                pathPoint4.costMalus = -1.0f;
                return pathPoint4;
            }
        }
        if (pathNodeType2 == PathNodeType.FENCE) {
            pathPoint = this.openPoint(n, n2, n3);
            pathPoint.visited = true;
            pathPoint.nodeType = pathNodeType2;
            pathPoint.costMalus = pathNodeType2.getPriority();
        }
        return pathPoint;
    }

    private boolean func_237236_a_(AxisAlignedBB axisAlignedBB) {
        return this.field_237227_l_.computeIfAbsent(axisAlignedBB, arg_0 -> this.lambda$func_237236_a_$0(axisAlignedBB, arg_0));
    }

    @Override
    public PathNodeType getPathNodeType(IBlockReader iBlockReader, int n, int n2, int n3, MobEntity mobEntity, int n4, int n5, int n6, boolean bl, boolean bl2) {
        EnumSet<PathNodeType> enumSet = EnumSet.noneOf(PathNodeType.class);
        PathNodeType pathNodeType = PathNodeType.BLOCKED;
        BlockPos blockPos = mobEntity.getPosition();
        pathNodeType = this.getPathNodeType(iBlockReader, n, n2, n3, n4, n5, n6, bl, bl2, enumSet, pathNodeType, blockPos);
        if (enumSet.contains((Object)PathNodeType.FENCE)) {
            return PathNodeType.FENCE;
        }
        if (enumSet.contains((Object)PathNodeType.UNPASSABLE_RAIL)) {
            return PathNodeType.UNPASSABLE_RAIL;
        }
        PathNodeType pathNodeType2 = PathNodeType.BLOCKED;
        for (PathNodeType pathNodeType3 : enumSet) {
            if (mobEntity.getPathPriority(pathNodeType3) < 0.0f) {
                return pathNodeType3;
            }
            if (!(mobEntity.getPathPriority(pathNodeType3) >= mobEntity.getPathPriority(pathNodeType2))) continue;
            pathNodeType2 = pathNodeType3;
        }
        return pathNodeType == PathNodeType.OPEN && mobEntity.getPathPriority(pathNodeType2) == 0.0f && n4 <= 1 ? PathNodeType.OPEN : pathNodeType2;
    }

    public PathNodeType getPathNodeType(IBlockReader iBlockReader, int n, int n2, int n3, int n4, int n5, int n6, boolean bl, boolean bl2, EnumSet<PathNodeType> enumSet, PathNodeType pathNodeType, BlockPos blockPos) {
        for (int i = 0; i < n4; ++i) {
            for (int j = 0; j < n5; ++j) {
                for (int k = 0; k < n6; ++k) {
                    int n7 = i + n;
                    int n8 = j + n2;
                    int n9 = k + n3;
                    PathNodeType pathNodeType2 = this.getPathNodeType(iBlockReader, n7, n8, n9);
                    pathNodeType2 = this.func_215744_a(iBlockReader, bl, bl2, blockPos, pathNodeType2);
                    if (i == 0 && j == 0 && k == 0) {
                        pathNodeType = pathNodeType2;
                    }
                    enumSet.add(pathNodeType2);
                }
            }
        }
        return pathNodeType;
    }

    protected PathNodeType func_215744_a(IBlockReader iBlockReader, boolean bl, boolean bl2, BlockPos blockPos, PathNodeType pathNodeType) {
        if (pathNodeType == PathNodeType.DOOR_WOOD_CLOSED && bl && bl2) {
            pathNodeType = PathNodeType.WALKABLE_DOOR;
        }
        if (pathNodeType == PathNodeType.DOOR_OPEN && !bl2) {
            pathNodeType = PathNodeType.BLOCKED;
        }
        if (pathNodeType == PathNodeType.RAIL && !(iBlockReader.getBlockState(blockPos).getBlock() instanceof AbstractRailBlock) && !(iBlockReader.getBlockState(blockPos.down()).getBlock() instanceof AbstractRailBlock)) {
            pathNodeType = PathNodeType.UNPASSABLE_RAIL;
        }
        if (pathNodeType == PathNodeType.LEAVES) {
            pathNodeType = PathNodeType.BLOCKED;
        }
        return pathNodeType;
    }

    private PathNodeType getPathNodeType(MobEntity mobEntity, BlockPos blockPos) {
        return this.func_237230_a_(mobEntity, blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    private PathNodeType func_237230_a_(MobEntity mobEntity, int n, int n2, int n3) {
        return this.field_237226_k_.computeIfAbsent(BlockPos.pack(n, n2, n3), arg_0 -> this.lambda$func_237230_a_$1(n, n2, n3, mobEntity, arg_0));
    }

    @Override
    public PathNodeType getPathNodeType(IBlockReader iBlockReader, int n, int n2, int n3) {
        return WalkNodeProcessor.func_237231_a_(iBlockReader, new BlockPos.Mutable(n, n2, n3));
    }

    public static PathNodeType func_237231_a_(IBlockReader iBlockReader, BlockPos.Mutable mutable) {
        int n = mutable.getX();
        int n2 = mutable.getY();
        int n3 = mutable.getZ();
        PathNodeType pathNodeType = WalkNodeProcessor.func_237238_b_(iBlockReader, mutable);
        if (pathNodeType == PathNodeType.OPEN && n2 >= 1) {
            PathNodeType pathNodeType2 = WalkNodeProcessor.func_237238_b_(iBlockReader, mutable.setPos(n, n2 - 1, n3));
            PathNodeType pathNodeType3 = pathNodeType = pathNodeType2 != PathNodeType.WALKABLE && pathNodeType2 != PathNodeType.OPEN && pathNodeType2 != PathNodeType.WATER && pathNodeType2 != PathNodeType.LAVA ? PathNodeType.WALKABLE : PathNodeType.OPEN;
            if (pathNodeType2 == PathNodeType.DAMAGE_FIRE) {
                pathNodeType = PathNodeType.DAMAGE_FIRE;
            }
            if (pathNodeType2 == PathNodeType.DAMAGE_CACTUS) {
                pathNodeType = PathNodeType.DAMAGE_CACTUS;
            }
            if (pathNodeType2 == PathNodeType.DAMAGE_OTHER) {
                pathNodeType = PathNodeType.DAMAGE_OTHER;
            }
            if (pathNodeType2 == PathNodeType.STICKY_HONEY) {
                pathNodeType = PathNodeType.STICKY_HONEY;
            }
        }
        if (pathNodeType == PathNodeType.WALKABLE) {
            pathNodeType = WalkNodeProcessor.func_237232_a_(iBlockReader, mutable.setPos(n, n2, n3), pathNodeType);
        }
        return pathNodeType;
    }

    public static PathNodeType func_237232_a_(IBlockReader iBlockReader, BlockPos.Mutable mutable, PathNodeType pathNodeType) {
        int n = mutable.getX();
        int n2 = mutable.getY();
        int n3 = mutable.getZ();
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                for (int k = -1; k <= 1; ++k) {
                    if (i == 0 && k == 0) continue;
                    mutable.setPos(n + i, n2 + j, n3 + k);
                    BlockState blockState = iBlockReader.getBlockState(mutable);
                    if (blockState.isIn(Blocks.CACTUS)) {
                        return PathNodeType.DANGER_CACTUS;
                    }
                    if (blockState.isIn(Blocks.SWEET_BERRY_BUSH)) {
                        return PathNodeType.DANGER_OTHER;
                    }
                    if (WalkNodeProcessor.func_237233_a_(blockState)) {
                        return PathNodeType.DANGER_FIRE;
                    }
                    if (!iBlockReader.getFluidState(mutable).isTagged(FluidTags.WATER)) continue;
                    return PathNodeType.WATER_BORDER;
                }
            }
        }
        return pathNodeType;
    }

    protected static PathNodeType func_237238_b_(IBlockReader iBlockReader, BlockPos blockPos) {
        BlockState blockState = iBlockReader.getBlockState(blockPos);
        Block block = blockState.getBlock();
        Material material = blockState.getMaterial();
        if (blockState.isAir()) {
            return PathNodeType.OPEN;
        }
        if (!blockState.isIn(BlockTags.TRAPDOORS) && !blockState.isIn(Blocks.LILY_PAD)) {
            if (blockState.isIn(Blocks.CACTUS)) {
                return PathNodeType.DAMAGE_CACTUS;
            }
            if (blockState.isIn(Blocks.SWEET_BERRY_BUSH)) {
                return PathNodeType.DAMAGE_OTHER;
            }
            if (blockState.isIn(Blocks.HONEY_BLOCK)) {
                return PathNodeType.STICKY_HONEY;
            }
            if (blockState.isIn(Blocks.COCOA)) {
                return PathNodeType.COCOA;
            }
            FluidState fluidState = iBlockReader.getFluidState(blockPos);
            if (fluidState.isTagged(FluidTags.WATER)) {
                return PathNodeType.WATER;
            }
            if (fluidState.isTagged(FluidTags.LAVA)) {
                return PathNodeType.LAVA;
            }
            if (WalkNodeProcessor.func_237233_a_(blockState)) {
                return PathNodeType.DAMAGE_FIRE;
            }
            if (DoorBlock.isWooden(blockState) && !blockState.get(DoorBlock.OPEN).booleanValue()) {
                return PathNodeType.DOOR_WOOD_CLOSED;
            }
            if (block instanceof DoorBlock && material == Material.IRON && !blockState.get(DoorBlock.OPEN).booleanValue()) {
                return PathNodeType.DOOR_IRON_CLOSED;
            }
            if (block instanceof DoorBlock && blockState.get(DoorBlock.OPEN).booleanValue()) {
                return PathNodeType.DOOR_OPEN;
            }
            if (block instanceof AbstractRailBlock) {
                return PathNodeType.RAIL;
            }
            if (block instanceof LeavesBlock) {
                return PathNodeType.LEAVES;
            }
            if (!(block.isIn(BlockTags.FENCES) || block.isIn(BlockTags.WALLS) || block instanceof FenceGateBlock && !blockState.get(FenceGateBlock.OPEN).booleanValue())) {
                return !blockState.allowsMovement(iBlockReader, blockPos, PathType.LAND) ? PathNodeType.BLOCKED : PathNodeType.OPEN;
            }
            return PathNodeType.FENCE;
        }
        return PathNodeType.TRAPDOOR;
    }

    private static boolean func_237233_a_(BlockState blockState) {
        return blockState.isIn(BlockTags.FIRE) || blockState.isIn(Blocks.LAVA) || blockState.isIn(Blocks.MAGMA_BLOCK) || CampfireBlock.isLit(blockState);
    }

    private PathNodeType lambda$func_237230_a_$1(int n, int n2, int n3, MobEntity mobEntity, long l) {
        return this.getPathNodeType(this.blockaccess, n, n2, n3, mobEntity, this.entitySizeX, this.entitySizeY, this.entitySizeZ, this.getCanOpenDoors(), this.getCanEnterDoors());
    }

    private Boolean lambda$func_237236_a_$0(AxisAlignedBB axisAlignedBB, AxisAlignedBB axisAlignedBB2) {
        return !this.blockaccess.hasNoCollisions(this.entity, axisAlignedBB);
    }
}

