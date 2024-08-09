/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.pathfinding;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.FlaggedPathPoint;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathHeap;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Region;

public class PathFinder {
    private final PathPoint[] pathOptions = new PathPoint[32];
    private final int field_215751_d;
    private final NodeProcessor nodeProcessor;
    private final PathHeap path = new PathHeap();

    public PathFinder(NodeProcessor nodeProcessor, int n) {
        this.nodeProcessor = nodeProcessor;
        this.field_215751_d = n;
    }

    @Nullable
    public Path func_227478_a_(Region region, MobEntity mobEntity, Set<BlockPos> set, float f, int n, float f2) {
        this.path.clearPath();
        this.nodeProcessor.func_225578_a_(region, mobEntity);
        PathPoint pathPoint = this.nodeProcessor.getStart();
        Map<FlaggedPathPoint, BlockPos> map = set.stream().collect(Collectors.toMap(this::lambda$func_227478_a_$0, Function.identity()));
        Path path = this.func_227479_a_(pathPoint, map, f, n, f2);
        this.nodeProcessor.postProcess();
        return path;
    }

    @Nullable
    private Path func_227479_a_(PathPoint pathPoint, Map<FlaggedPathPoint, BlockPos> map, float f, int n, float f2) {
        Object object;
        Set<FlaggedPathPoint> set = map.keySet();
        pathPoint.totalPathDistance = 0.0f;
        pathPoint.distanceToTarget = pathPoint.distanceToNext = this.func_224776_a(pathPoint, set);
        this.path.clearPath();
        this.path.addPoint(pathPoint);
        ImmutableSet immutableSet = ImmutableSet.of();
        int n2 = 0;
        HashSet<FlaggedPathPoint> hashSet = Sets.newHashSetWithExpectedSize(set.size());
        int n3 = (int)((float)this.field_215751_d * f2);
        while (!this.path.isPathEmpty() && ++n2 < n3) {
            object = this.path.dequeue();
            ((PathPoint)object).visited = true;
            for (FlaggedPathPoint flaggedPathPoint : set) {
                if (!(((PathPoint)object).func_224757_c(flaggedPathPoint) <= (float)n)) continue;
                flaggedPathPoint.func_224764_e();
                hashSet.add(flaggedPathPoint);
            }
            if (!hashSet.isEmpty()) break;
            if (((PathPoint)object).distanceTo(pathPoint) >= f) continue;
            int n4 = this.nodeProcessor.func_222859_a(this.pathOptions, (PathPoint)object);
            for (int i = 0; i < n4; ++i) {
                PathPoint pathPoint2 = this.pathOptions[i];
                float f3 = ((PathPoint)object).distanceTo(pathPoint2);
                pathPoint2.field_222861_j = ((PathPoint)object).field_222861_j + f3;
                float f4 = ((PathPoint)object).totalPathDistance + f3 + pathPoint2.costMalus;
                if (!(pathPoint2.field_222861_j < f) || pathPoint2.isAssigned() && !(f4 < pathPoint2.totalPathDistance)) continue;
                pathPoint2.previous = object;
                pathPoint2.totalPathDistance = f4;
                pathPoint2.distanceToNext = this.func_224776_a(pathPoint2, set) * 1.5f;
                if (pathPoint2.isAssigned()) {
                    this.path.changeDistance(pathPoint2, pathPoint2.totalPathDistance + pathPoint2.distanceToNext);
                    continue;
                }
                pathPoint2.distanceToTarget = pathPoint2.totalPathDistance + pathPoint2.distanceToNext;
                this.path.addPoint(pathPoint2);
            }
        }
        object = !hashSet.isEmpty() ? hashSet.stream().map(arg_0 -> this.lambda$func_227479_a_$1(map, arg_0)).min(Comparator.comparingInt(Path::getCurrentPathLength)) : set.stream().map(arg_0 -> this.lambda$func_227479_a_$2(map, arg_0)).min(Comparator.comparingDouble(Path::func_224769_l).thenComparingInt(Path::getCurrentPathLength));
        return !((Optional)object).isPresent() ? null : (Path)((Optional)object).get();
    }

    private float func_224776_a(PathPoint pathPoint, Set<FlaggedPathPoint> set) {
        float f = Float.MAX_VALUE;
        for (FlaggedPathPoint flaggedPathPoint : set) {
            float f2 = pathPoint.distanceTo(flaggedPathPoint);
            flaggedPathPoint.func_224761_a(f2, pathPoint);
            f = Math.min(f2, f);
        }
        return f;
    }

    private Path func_224780_a(PathPoint pathPoint, BlockPos blockPos, boolean bl) {
        ArrayList<PathPoint> arrayList = Lists.newArrayList();
        PathPoint pathPoint2 = pathPoint;
        arrayList.add(0, pathPoint);
        while (pathPoint2.previous != null) {
            pathPoint2 = pathPoint2.previous;
            arrayList.add(0, pathPoint2);
        }
        return new Path(arrayList, blockPos, bl);
    }

    private Path lambda$func_227479_a_$2(Map map, FlaggedPathPoint flaggedPathPoint) {
        return this.func_224780_a(flaggedPathPoint.func_224763_d(), (BlockPos)map.get(flaggedPathPoint), true);
    }

    private Path lambda$func_227479_a_$1(Map map, FlaggedPathPoint flaggedPathPoint) {
        return this.func_224780_a(flaggedPathPoint.func_224763_d(), (BlockPos)map.get(flaggedPathPoint), false);
    }

    private FlaggedPathPoint lambda$func_227478_a_$0(BlockPos blockPos) {
        return this.nodeProcessor.func_224768_a(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }
}

