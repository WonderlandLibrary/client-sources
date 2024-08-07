/*
 * Decompiled with CFR 0_118.
 */
package optifine;

import net.minecraft.util.Vec3;
import optifine.Config;

public class CustomColorFader {
    private Vec3 color = null;
    private long timeUpdate = System.currentTimeMillis();

    public Vec3 getColor(double x, double y, double z) {
        if (this.color == null) {
            this.color = new Vec3(x, y, z);
            return this.color;
        }
        long timeNow = System.currentTimeMillis();
        long timeDiff = timeNow - this.timeUpdate;
        if (timeDiff == 0) {
            return this.color;
        }
        this.timeUpdate = timeNow;
        if (Math.abs(x - this.color.xCoord) < 0.004 && Math.abs(y - this.color.yCoord) < 0.004 && Math.abs(z - this.color.zCoord) < 0.004) {
            return this.color;
        }
        double k = (double)timeDiff * 0.001;
        k = Config.limit(k, 0.0, 1.0);
        double dx = x - this.color.xCoord;
        double dy = y - this.color.yCoord;
        double dz = z - this.color.zCoord;
        double xn = this.color.xCoord + dx * k;
        double yn = this.color.yCoord + dy * k;
        double zn = this.color.zCoord + dz * k;
        this.color = new Vec3(xn, yn, zn);
        return this.color;
    }
}

