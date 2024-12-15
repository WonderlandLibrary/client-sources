package com.alan.clients.util.pathfinding.custom.api;

import net.minecraft.util.Vec3i;

public class Point extends Vec3i {
    public Point(int xIn, int yIn, int zIn) {
        super(xIn, yIn, zIn);
    }

    public Point(double xIn, double yIn, double zIn) {
        super(xIn, yIn, zIn);
    }

    public Point(Vec3i point) {
        super(point.getX(), point.getY(), point.getZ());
    }
}
