/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.render.xd;

public final class Box {
    public final double minX;
    public final double minY;
    public final double minZ;
    public final double maxX;
    public final double maxY;
    public final double maxZ;

    public Box(double x2, double y2, double z2, double x1, double y1, double z1) {
        this.minX = x2;
        this.minY = y2;
        this.minZ = z2;
        this.maxX = x1;
        this.maxY = y1;
        this.maxZ = z1;
    }
}

