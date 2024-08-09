/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.pathfinding;

import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.HashSet;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.FlaggedPathPoint;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.Region;

public class FlyingNodeProcessor
extends WalkNodeProcessor {
    @Override
    public void func_225578_a_(Region region, MobEntity mobEntity) {
        super.func_225578_a_(region, mobEntity);
        this.avoidsWater = mobEntity.getPathPriority(PathNodeType.WATER);
    }

    @Override
    public void postProcess() {
        this.entity.setPathPriority(PathNodeType.WATER, this.avoidsWater);
        super.postProcess();
    }

    @Override
    public PathPoint getStart() {
        Object object;
        BlockPos blockPos;
        int n;
        if (this.getCanSwim() && this.entity.isInWater()) {
            n = MathHelper.floor(this.entity.getPosY());
            blockPos = new BlockPos.Mutable(this.entity.getPosX(), (double)n, this.entity.getPosZ());
            object = this.blockaccess.getBlockState(blockPos).getBlock();
            while (object == Blocks.WATER) {
                ((BlockPos.Mutable)blockPos).setPos(this.entity.getPosX(), ++n, this.entity.getPosZ());
                object = this.blockaccess.getBlockState(blockPos).getBlock();
            }
        } else {
            n = MathHelper.floor(this.entity.getPosY() + 0.5);
        }
        if (this.entity.getPathPriority((PathNodeType)((Object)(object = this.getPathNodeType(this.entity, (blockPos = this.entity.getPosition()).getX(), n, blockPos.getZ())))) < 0.0f) {
            HashSet<BlockPos> hashSet = Sets.newHashSet();
            hashSet.add(new BlockPos(this.entity.getBoundingBox().minX, (double)n, this.entity.getBoundingBox().minZ));
            hashSet.add(new BlockPos(this.entity.getBoundingBox().minX, (double)n, this.entity.getBoundingBox().maxZ));
            hashSet.add(new BlockPos(this.entity.getBoundingBox().maxX, (double)n, this.entity.getBoundingBox().minZ));
            hashSet.add(new BlockPos(this.entity.getBoundingBox().maxX, (double)n, this.entity.getBoundingBox().maxZ));
            for (BlockPos blockPos2 : hashSet) {
                PathNodeType pathNodeType = this.getPathNodeType(this.entity, blockPos2);
                if (!(this.entity.getPathPriority(pathNodeType) >= 0.0f)) continue;
                return super.openPoint(blockPos2.getX(), blockPos2.getY(), blockPos2.getZ());
            }
        }
        return super.openPoint(blockPos.getX(), n, blockPos.getZ());
    }

    @Override
    public FlaggedPathPoint func_224768_a(double d, double d2, double d3) {
        return new FlaggedPathPoint(super.openPoint(MathHelper.floor(d), MathHelper.floor(d2), MathHelper.floor(d3)));
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
        PathPoint pathPoint9;
        PathPoint pathPoint10;
        PathPoint pathPoint11;
        PathPoint pathPoint12;
        PathPoint pathPoint13;
        PathPoint pathPoint14;
        PathPoint pathPoint15;
        PathPoint pathPoint16;
        PathPoint pathPoint17;
        PathPoint pathPoint18;
        PathPoint pathPoint19;
        PathPoint pathPoint20;
        PathPoint pathPoint21;
        PathPoint pathPoint22;
        PathPoint pathPoint23;
        PathPoint pathPoint24;
        PathPoint pathPoint25;
        PathPoint pathPoint26;
        int n = 0;
        PathPoint pathPoint27 = this.openPoint(pathPoint.x, pathPoint.y, pathPoint.z + 1);
        if (this.func_227477_b_(pathPoint27)) {
            pathPointArray[n++] = pathPoint27;
        }
        if (this.func_227477_b_(pathPoint26 = this.openPoint(pathPoint.x - 1, pathPoint.y, pathPoint.z))) {
            pathPointArray[n++] = pathPoint26;
        }
        if (this.func_227477_b_(pathPoint25 = this.openPoint(pathPoint.x + 1, pathPoint.y, pathPoint.z))) {
            pathPointArray[n++] = pathPoint25;
        }
        if (this.func_227477_b_(pathPoint24 = this.openPoint(pathPoint.x, pathPoint.y, pathPoint.z - 1))) {
            pathPointArray[n++] = pathPoint24;
        }
        if (this.func_227477_b_(pathPoint23 = this.openPoint(pathPoint.x, pathPoint.y + 1, pathPoint.z))) {
            pathPointArray[n++] = pathPoint23;
        }
        if (this.func_227477_b_(pathPoint22 = this.openPoint(pathPoint.x, pathPoint.y - 1, pathPoint.z))) {
            pathPointArray[n++] = pathPoint22;
        }
        if (this.func_227477_b_(pathPoint21 = this.openPoint(pathPoint.x, pathPoint.y + 1, pathPoint.z + 1)) && this.func_227476_a_(pathPoint27) && this.func_227476_a_(pathPoint23)) {
            pathPointArray[n++] = pathPoint21;
        }
        if (this.func_227477_b_(pathPoint20 = this.openPoint(pathPoint.x - 1, pathPoint.y + 1, pathPoint.z)) && this.func_227476_a_(pathPoint26) && this.func_227476_a_(pathPoint23)) {
            pathPointArray[n++] = pathPoint20;
        }
        if (this.func_227477_b_(pathPoint19 = this.openPoint(pathPoint.x + 1, pathPoint.y + 1, pathPoint.z)) && this.func_227476_a_(pathPoint25) && this.func_227476_a_(pathPoint23)) {
            pathPointArray[n++] = pathPoint19;
        }
        if (this.func_227477_b_(pathPoint18 = this.openPoint(pathPoint.x, pathPoint.y + 1, pathPoint.z - 1)) && this.func_227476_a_(pathPoint24) && this.func_227476_a_(pathPoint23)) {
            pathPointArray[n++] = pathPoint18;
        }
        if (this.func_227477_b_(pathPoint17 = this.openPoint(pathPoint.x, pathPoint.y - 1, pathPoint.z + 1)) && this.func_227476_a_(pathPoint27) && this.func_227476_a_(pathPoint22)) {
            pathPointArray[n++] = pathPoint17;
        }
        if (this.func_227477_b_(pathPoint16 = this.openPoint(pathPoint.x - 1, pathPoint.y - 1, pathPoint.z)) && this.func_227476_a_(pathPoint26) && this.func_227476_a_(pathPoint22)) {
            pathPointArray[n++] = pathPoint16;
        }
        if (this.func_227477_b_(pathPoint15 = this.openPoint(pathPoint.x + 1, pathPoint.y - 1, pathPoint.z)) && this.func_227476_a_(pathPoint25) && this.func_227476_a_(pathPoint22)) {
            pathPointArray[n++] = pathPoint15;
        }
        if (this.func_227477_b_(pathPoint14 = this.openPoint(pathPoint.x, pathPoint.y - 1, pathPoint.z - 1)) && this.func_227476_a_(pathPoint24) && this.func_227476_a_(pathPoint22)) {
            pathPointArray[n++] = pathPoint14;
        }
        if (this.func_227477_b_(pathPoint13 = this.openPoint(pathPoint.x + 1, pathPoint.y, pathPoint.z - 1)) && this.func_227476_a_(pathPoint24) && this.func_227476_a_(pathPoint25)) {
            pathPointArray[n++] = pathPoint13;
        }
        if (this.func_227477_b_(pathPoint12 = this.openPoint(pathPoint.x + 1, pathPoint.y, pathPoint.z + 1)) && this.func_227476_a_(pathPoint27) && this.func_227476_a_(pathPoint25)) {
            pathPointArray[n++] = pathPoint12;
        }
        if (this.func_227477_b_(pathPoint11 = this.openPoint(pathPoint.x - 1, pathPoint.y, pathPoint.z - 1)) && this.func_227476_a_(pathPoint24) && this.func_227476_a_(pathPoint26)) {
            pathPointArray[n++] = pathPoint11;
        }
        if (this.func_227477_b_(pathPoint10 = this.openPoint(pathPoint.x - 1, pathPoint.y, pathPoint.z + 1)) && this.func_227476_a_(pathPoint27) && this.func_227476_a_(pathPoint26)) {
            pathPointArray[n++] = pathPoint10;
        }
        if (this.func_227477_b_(pathPoint9 = this.openPoint(pathPoint.x + 1, pathPoint.y + 1, pathPoint.z - 1)) && this.func_227476_a_(pathPoint13) && this.func_227476_a_(pathPoint24) && this.func_227476_a_(pathPoint25) && this.func_227476_a_(pathPoint23) && this.func_227476_a_(pathPoint18) && this.func_227476_a_(pathPoint19)) {
            pathPointArray[n++] = pathPoint9;
        }
        if (this.func_227477_b_(pathPoint8 = this.openPoint(pathPoint.x + 1, pathPoint.y + 1, pathPoint.z + 1)) && this.func_227476_a_(pathPoint12) && this.func_227476_a_(pathPoint27) && this.func_227476_a_(pathPoint25) && this.func_227476_a_(pathPoint23) && this.func_227476_a_(pathPoint21) && this.func_227476_a_(pathPoint19)) {
            pathPointArray[n++] = pathPoint8;
        }
        if (this.func_227477_b_(pathPoint7 = this.openPoint(pathPoint.x - 1, pathPoint.y + 1, pathPoint.z - 1)) && this.func_227476_a_(pathPoint11) && this.func_227476_a_(pathPoint24) && this.func_227476_a_(pathPoint26) & this.func_227476_a_(pathPoint23) && this.func_227476_a_(pathPoint18) && this.func_227476_a_(pathPoint20)) {
            pathPointArray[n++] = pathPoint7;
        }
        if (this.func_227477_b_(pathPoint6 = this.openPoint(pathPoint.x - 1, pathPoint.y + 1, pathPoint.z + 1)) && this.func_227476_a_(pathPoint10) && this.func_227476_a_(pathPoint27) && this.func_227476_a_(pathPoint26) & this.func_227476_a_(pathPoint23) && this.func_227476_a_(pathPoint21) && this.func_227476_a_(pathPoint20)) {
            pathPointArray[n++] = pathPoint6;
        }
        if (this.func_227477_b_(pathPoint5 = this.openPoint(pathPoint.x + 1, pathPoint.y - 1, pathPoint.z - 1)) && this.func_227476_a_(pathPoint13) && this.func_227476_a_(pathPoint24) && this.func_227476_a_(pathPoint25) && this.func_227476_a_(pathPoint22) && this.func_227476_a_(pathPoint14) && this.func_227476_a_(pathPoint15)) {
            pathPointArray[n++] = pathPoint5;
        }
        if (this.func_227477_b_(pathPoint4 = this.openPoint(pathPoint.x + 1, pathPoint.y - 1, pathPoint.z + 1)) && this.func_227476_a_(pathPoint12) && this.func_227476_a_(pathPoint27) && this.func_227476_a_(pathPoint25) && this.func_227476_a_(pathPoint22) && this.func_227476_a_(pathPoint17) && this.func_227476_a_(pathPoint15)) {
            pathPointArray[n++] = pathPoint4;
        }
        if (this.func_227477_b_(pathPoint3 = this.openPoint(pathPoint.x - 1, pathPoint.y - 1, pathPoint.z - 1)) && this.func_227476_a_(pathPoint11) && this.func_227476_a_(pathPoint24) && this.func_227476_a_(pathPoint26) && this.func_227476_a_(pathPoint22) && this.func_227476_a_(pathPoint14) && this.func_227476_a_(pathPoint16)) {
            pathPointArray[n++] = pathPoint3;
        }
        if (this.func_227477_b_(pathPoint2 = this.openPoint(pathPoint.x - 1, pathPoint.y - 1, pathPoint.z + 1)) && this.func_227476_a_(pathPoint10) && this.func_227476_a_(pathPoint27) && this.func_227476_a_(pathPoint26) && this.func_227476_a_(pathPoint22) && this.func_227476_a_(pathPoint17) && this.func_227476_a_(pathPoint16)) {
            pathPointArray[n++] = pathPoint2;
        }
        return n;
    }

    private boolean func_227476_a_(@Nullable PathPoint pathPoint) {
        return pathPoint != null && pathPoint.costMalus >= 0.0f;
    }

    private boolean func_227477_b_(@Nullable PathPoint pathPoint) {
        return pathPoint != null && !pathPoint.visited;
    }

    @Override
    @Nullable
    protected PathPoint openPoint(int n, int n2, int n3) {
        PathPoint pathPoint = null;
        PathNodeType pathNodeType = this.getPathNodeType(this.entity, n, n2, n3);
        float f = this.entity.getPathPriority(pathNodeType);
        if (f >= 0.0f) {
            pathPoint = super.openPoint(n, n2, n3);
            pathPoint.nodeType = pathNodeType;
            pathPoint.costMalus = Math.max(pathPoint.costMalus, f);
            if (pathNodeType == PathNodeType.WALKABLE) {
                pathPoint.costMalus += 1.0f;
            }
        }
        return pathNodeType != PathNodeType.OPEN && pathNodeType != PathNodeType.WALKABLE ? pathPoint : pathPoint;
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
        PathNodeType pathNodeType2 = PathNodeType.BLOCKED;
        for (PathNodeType pathNodeType3 : enumSet) {
            if (mobEntity.getPathPriority(pathNodeType3) < 0.0f) {
                return pathNodeType3;
            }
            if (!(mobEntity.getPathPriority(pathNodeType3) >= mobEntity.getPathPriority(pathNodeType2))) continue;
            pathNodeType2 = pathNodeType3;
        }
        return pathNodeType == PathNodeType.OPEN && mobEntity.getPathPriority(pathNodeType2) == 0.0f ? PathNodeType.OPEN : pathNodeType2;
    }

    @Override
    public PathNodeType getPathNodeType(IBlockReader iBlockReader, int n, int n2, int n3) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        PathNodeType pathNodeType = FlyingNodeProcessor.func_237238_b_(iBlockReader, mutable.setPos(n, n2, n3));
        if (pathNodeType == PathNodeType.OPEN && n2 >= 1) {
            BlockState blockState = iBlockReader.getBlockState(mutable.setPos(n, n2 - 1, n3));
            PathNodeType pathNodeType2 = FlyingNodeProcessor.func_237238_b_(iBlockReader, mutable.setPos(n, n2 - 1, n3));
            pathNodeType = pathNodeType2 != PathNodeType.DAMAGE_FIRE && !blockState.isIn(Blocks.MAGMA_BLOCK) && pathNodeType2 != PathNodeType.LAVA && !blockState.isIn(BlockTags.CAMPFIRES) ? (pathNodeType2 == PathNodeType.DAMAGE_CACTUS ? PathNodeType.DAMAGE_CACTUS : (pathNodeType2 == PathNodeType.DAMAGE_OTHER ? PathNodeType.DAMAGE_OTHER : (pathNodeType2 == PathNodeType.COCOA ? PathNodeType.COCOA : (pathNodeType2 == PathNodeType.FENCE ? PathNodeType.FENCE : (pathNodeType2 != PathNodeType.WALKABLE && pathNodeType2 != PathNodeType.OPEN && pathNodeType2 != PathNodeType.WATER ? PathNodeType.WALKABLE : PathNodeType.OPEN))))) : PathNodeType.DAMAGE_FIRE;
        }
        if (pathNodeType == PathNodeType.WALKABLE || pathNodeType == PathNodeType.OPEN) {
            pathNodeType = FlyingNodeProcessor.func_237232_a_(iBlockReader, mutable.setPos(n, n2, n3), pathNodeType);
        }
        return pathNodeType;
    }

    private PathNodeType getPathNodeType(MobEntity mobEntity, BlockPos blockPos) {
        return this.getPathNodeType(mobEntity, blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    private PathNodeType getPathNodeType(MobEntity mobEntity, int n, int n2, int n3) {
        return this.getPathNodeType(this.blockaccess, n, n2, n3, mobEntity, this.entitySizeX, this.entitySizeY, this.entitySizeZ, this.getCanOpenDoors(), this.getCanEnterDoors());
    }
}

