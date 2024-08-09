/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.pathfinding;

import net.minecraft.network.PacketBuffer;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class PathPoint {
    public final int x;
    public final int y;
    public final int z;
    private final int hash;
    public int index = -1;
    public float totalPathDistance;
    public float distanceToNext;
    public float distanceToTarget;
    public PathPoint previous;
    public boolean visited;
    public float field_222861_j;
    public float costMalus;
    public PathNodeType nodeType = PathNodeType.BLOCKED;

    public PathPoint(int n, int n2, int n3) {
        this.x = n;
        this.y = n2;
        this.z = n3;
        this.hash = PathPoint.makeHash(n, n2, n3);
    }

    public PathPoint cloneMove(int n, int n2, int n3) {
        PathPoint pathPoint = new PathPoint(n, n2, n3);
        pathPoint.index = this.index;
        pathPoint.totalPathDistance = this.totalPathDistance;
        pathPoint.distanceToNext = this.distanceToNext;
        pathPoint.distanceToTarget = this.distanceToTarget;
        pathPoint.previous = this.previous;
        pathPoint.visited = this.visited;
        pathPoint.field_222861_j = this.field_222861_j;
        pathPoint.costMalus = this.costMalus;
        pathPoint.nodeType = this.nodeType;
        return pathPoint;
    }

    public static int makeHash(int n, int n2, int n3) {
        return n2 & 0xFF | (n & Short.MAX_VALUE) << 8 | (n3 & Short.MAX_VALUE) << 24 | (n < 0 ? Integer.MIN_VALUE : 0) | (n3 < 0 ? 32768 : 0);
    }

    public float distanceTo(PathPoint pathPoint) {
        float f = pathPoint.x - this.x;
        float f2 = pathPoint.y - this.y;
        float f3 = pathPoint.z - this.z;
        return MathHelper.sqrt(f * f + f2 * f2 + f3 * f3);
    }

    public float distanceToSquared(PathPoint pathPoint) {
        float f = pathPoint.x - this.x;
        float f2 = pathPoint.y - this.y;
        float f3 = pathPoint.z - this.z;
        return f * f + f2 * f2 + f3 * f3;
    }

    public float func_224757_c(PathPoint pathPoint) {
        float f = Math.abs(pathPoint.x - this.x);
        float f2 = Math.abs(pathPoint.y - this.y);
        float f3 = Math.abs(pathPoint.z - this.z);
        return f + f2 + f3;
    }

    public float func_224758_c(BlockPos blockPos) {
        float f = Math.abs(blockPos.getX() - this.x);
        float f2 = Math.abs(blockPos.getY() - this.y);
        float f3 = Math.abs(blockPos.getZ() - this.z);
        return f + f2 + f3;
    }

    public BlockPos func_224759_a() {
        return new BlockPos(this.x, this.y, this.z);
    }

    public boolean equals(Object object) {
        if (!(object instanceof PathPoint)) {
            return true;
        }
        PathPoint pathPoint = (PathPoint)object;
        return this.hash == pathPoint.hash && this.x == pathPoint.x && this.y == pathPoint.y && this.z == pathPoint.z;
    }

    public int hashCode() {
        return this.hash;
    }

    public boolean isAssigned() {
        return this.index >= 0;
    }

    public String toString() {
        return "Node{x=" + this.x + ", y=" + this.y + ", z=" + this.z + "}";
    }

    public static PathPoint createFromBuffer(PacketBuffer packetBuffer) {
        PathPoint pathPoint = new PathPoint(packetBuffer.readInt(), packetBuffer.readInt(), packetBuffer.readInt());
        pathPoint.field_222861_j = packetBuffer.readFloat();
        pathPoint.costMalus = packetBuffer.readFloat();
        pathPoint.visited = packetBuffer.readBoolean();
        pathPoint.nodeType = PathNodeType.values()[packetBuffer.readInt()];
        pathPoint.distanceToTarget = packetBuffer.readFloat();
        return pathPoint;
    }
}

