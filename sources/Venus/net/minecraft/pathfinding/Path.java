/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.pathfinding;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.pathfinding.FlaggedPathPoint;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class Path {
    private final List<PathPoint> points;
    private PathPoint[] openSet = new PathPoint[0];
    private PathPoint[] closedSet = new PathPoint[0];
    private Set<FlaggedPathPoint> field_224772_d;
    private int currentPathIndex;
    private final BlockPos target;
    private final float field_224773_g;
    private final boolean field_224774_h;

    public Path(List<PathPoint> list, BlockPos blockPos, boolean bl) {
        this.points = list;
        this.target = blockPos;
        this.field_224773_g = list.isEmpty() ? Float.MAX_VALUE : this.points.get(this.points.size() - 1).func_224758_c(this.target);
        this.field_224774_h = bl;
    }

    public void incrementPathIndex() {
        ++this.currentPathIndex;
    }

    public boolean func_242945_b() {
        return this.currentPathIndex <= 0;
    }

    public boolean isFinished() {
        return this.currentPathIndex >= this.points.size();
    }

    @Nullable
    public PathPoint getFinalPathPoint() {
        return !this.points.isEmpty() ? this.points.get(this.points.size() - 1) : null;
    }

    public PathPoint getPathPointFromIndex(int n) {
        return this.points.get(n);
    }

    public void setCurrentPathLength(int n) {
        if (this.points.size() > n) {
            this.points.subList(n, this.points.size()).clear();
        }
    }

    public void setPoint(int n, PathPoint pathPoint) {
        this.points.set(n, pathPoint);
    }

    public int getCurrentPathLength() {
        return this.points.size();
    }

    public int getCurrentPathIndex() {
        return this.currentPathIndex;
    }

    public void setCurrentPathIndex(int n) {
        this.currentPathIndex = n;
    }

    public Vector3d getVectorFromIndex(Entity entity2, int n) {
        PathPoint pathPoint = this.points.get(n);
        double d = (double)pathPoint.x + (double)((int)(entity2.getWidth() + 1.0f)) * 0.5;
        double d2 = pathPoint.y;
        double d3 = (double)pathPoint.z + (double)((int)(entity2.getWidth() + 1.0f)) * 0.5;
        return new Vector3d(d, d2, d3);
    }

    public BlockPos func_242947_d(int n) {
        return this.points.get(n).func_224759_a();
    }

    public Vector3d getPosition(Entity entity2) {
        return this.getVectorFromIndex(entity2, this.currentPathIndex);
    }

    public BlockPos func_242948_g() {
        return this.points.get(this.currentPathIndex).func_224759_a();
    }

    public PathPoint func_237225_h_() {
        return this.points.get(this.currentPathIndex);
    }

    @Nullable
    public PathPoint func_242950_i() {
        return this.currentPathIndex > 0 ? this.points.get(this.currentPathIndex - 1) : null;
    }

    public boolean isSamePath(@Nullable Path path) {
        if (path == null) {
            return true;
        }
        if (path.points.size() != this.points.size()) {
            return true;
        }
        for (int i = 0; i < this.points.size(); ++i) {
            PathPoint pathPoint = this.points.get(i);
            PathPoint pathPoint2 = path.points.get(i);
            if (pathPoint.x == pathPoint2.x && pathPoint.y == pathPoint2.y && pathPoint.z == pathPoint2.z) continue;
            return true;
        }
        return false;
    }

    public boolean reachesTarget() {
        return this.field_224774_h;
    }

    public PathPoint[] getOpenSet() {
        return this.openSet;
    }

    public PathPoint[] getClosedSet() {
        return this.closedSet;
    }

    public static Path read(PacketBuffer packetBuffer) {
        boolean bl = packetBuffer.readBoolean();
        int n = packetBuffer.readInt();
        int n2 = packetBuffer.readInt();
        HashSet<FlaggedPathPoint> hashSet = Sets.newHashSet();
        for (int i = 0; i < n2; ++i) {
            hashSet.add(FlaggedPathPoint.func_224760_c(packetBuffer));
        }
        BlockPos blockPos = new BlockPos(packetBuffer.readInt(), packetBuffer.readInt(), packetBuffer.readInt());
        ArrayList<PathPoint> arrayList = Lists.newArrayList();
        int n3 = packetBuffer.readInt();
        for (int i = 0; i < n3; ++i) {
            arrayList.add(PathPoint.createFromBuffer(packetBuffer));
        }
        PathPoint[] pathPointArray = new PathPoint[packetBuffer.readInt()];
        for (int i = 0; i < pathPointArray.length; ++i) {
            pathPointArray[i] = PathPoint.createFromBuffer(packetBuffer);
        }
        PathPoint[] pathPointArray2 = new PathPoint[packetBuffer.readInt()];
        for (int i = 0; i < pathPointArray2.length; ++i) {
            pathPointArray2[i] = PathPoint.createFromBuffer(packetBuffer);
        }
        Path path = new Path(arrayList, blockPos, bl);
        path.openSet = pathPointArray;
        path.closedSet = pathPointArray2;
        path.field_224772_d = hashSet;
        path.currentPathIndex = n;
        return path;
    }

    public String toString() {
        return "Path(length=" + this.points.size() + ")";
    }

    public BlockPos getTarget() {
        return this.target;
    }

    public float func_224769_l() {
        return this.field_224773_g;
    }
}

