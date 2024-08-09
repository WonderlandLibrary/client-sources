/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import net.minecraft.util.math.vector.Vector3d;
import net.optifine.Config;

public class CustomColorFader {
    private Vector3d color = null;
    private long timeUpdate = System.currentTimeMillis();

    public Vector3d getColor(double d, double d2, double d3) {
        if (this.color == null) {
            this.color = new Vector3d(d, d2, d3);
            return this.color;
        }
        long l = System.currentTimeMillis();
        long l2 = l - this.timeUpdate;
        if (l2 == 0L) {
            return this.color;
        }
        this.timeUpdate = l;
        if (Math.abs(d - this.color.x) < 0.004 && Math.abs(d2 - this.color.y) < 0.004 && Math.abs(d3 - this.color.z) < 0.004) {
            return this.color;
        }
        double d4 = (double)l2 * 0.001;
        d4 = Config.limit(d4, 0.0, 1.0);
        double d5 = d - this.color.x;
        double d6 = d2 - this.color.y;
        double d7 = d3 - this.color.z;
        double d8 = this.color.x + d5 * d4;
        double d9 = this.color.y + d6 * d4;
        double d10 = this.color.z + d7 * d4;
        this.color = new Vector3d(d8, d9, d10);
        return this.color;
    }
}

