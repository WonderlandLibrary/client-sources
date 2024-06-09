/*
 * Decompiled with CFR 0.145.
 */
package optifine;

import net.minecraft.util.Vec3;
import optifine.Config;

public class CustomColorFader {
    private Vec3 color = null;
    private long timeUpdate = System.currentTimeMillis();

    public Vec3 getColor(double x2, double y2, double z2) {
        if (this.color == null) {
            this.color = new Vec3(x2, y2, z2);
            return this.color;
        }
        long timeNow = System.currentTimeMillis();
        long timeDiff = timeNow - this.timeUpdate;
        if (timeDiff == 0L) {
            return this.color;
        }
        this.timeUpdate = timeNow;
        if (Math.abs(x2 - this.color.xCoord) < 0.004 && Math.abs(y2 - this.color.yCoord) < 0.004 && Math.abs(z2 - this.color.zCoord) < 0.004) {
            return this.color;
        }
        double k2 = (double)timeDiff * 0.001;
        k2 = Config.limit(k2, 0.0, 1.0);
        double dx2 = x2 - this.color.xCoord;
        double dy2 = y2 - this.color.yCoord;
        double dz2 = z2 - this.color.zCoord;
        double xn2 = this.color.xCoord + dx2 * k2;
        double yn2 = this.color.yCoord + dy2 * k2;
        double zn2 = this.color.zCoord + dz2 * k2;
        this.color = new Vec3(xn2, yn2, zn2);
        return this.color;
    }
}

