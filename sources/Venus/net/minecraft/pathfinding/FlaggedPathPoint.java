/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.pathfinding;

import net.minecraft.network.PacketBuffer;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathPoint;

public class FlaggedPathPoint
extends PathPoint {
    private float field_224765_m = Float.MAX_VALUE;
    private PathPoint field_224766_n;
    private boolean field_224767_o;

    public FlaggedPathPoint(PathPoint pathPoint) {
        super(pathPoint.x, pathPoint.y, pathPoint.z);
    }

    public FlaggedPathPoint(int n, int n2, int n3) {
        super(n, n2, n3);
    }

    public void func_224761_a(float f, PathPoint pathPoint) {
        if (f < this.field_224765_m) {
            this.field_224765_m = f;
            this.field_224766_n = pathPoint;
        }
    }

    public PathPoint func_224763_d() {
        return this.field_224766_n;
    }

    public void func_224764_e() {
        this.field_224767_o = true;
    }

    public static FlaggedPathPoint func_224760_c(PacketBuffer packetBuffer) {
        FlaggedPathPoint flaggedPathPoint = new FlaggedPathPoint(packetBuffer.readInt(), packetBuffer.readInt(), packetBuffer.readInt());
        flaggedPathPoint.field_222861_j = packetBuffer.readFloat();
        flaggedPathPoint.costMalus = packetBuffer.readFloat();
        flaggedPathPoint.visited = packetBuffer.readBoolean();
        flaggedPathPoint.nodeType = PathNodeType.values()[packetBuffer.readInt()];
        flaggedPathPoint.distanceToTarget = packetBuffer.readFloat();
        return flaggedPathPoint;
    }
}

