/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import javax.annotation.Nonnull;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class CubicSampler {
    private static final double[] field_240806_a_ = new double[]{0.0, 1.0, 4.0, 6.0, 4.0, 1.0, 0.0};

    @Nonnull
    public static Vector3d func_240807_a_(Vector3d vector3d, Vec3Fetcher vec3Fetcher) {
        int n = MathHelper.floor(vector3d.getX());
        int n2 = MathHelper.floor(vector3d.getY());
        int n3 = MathHelper.floor(vector3d.getZ());
        double d = vector3d.getX() - (double)n;
        double d2 = vector3d.getY() - (double)n2;
        double d3 = vector3d.getZ() - (double)n3;
        double d4 = 0.0;
        Vector3d vector3d2 = Vector3d.ZERO;
        for (int i = 0; i < 6; ++i) {
            double d5 = MathHelper.lerp(d, field_240806_a_[i + 1], field_240806_a_[i]);
            int n4 = n - 2 + i;
            for (int j = 0; j < 6; ++j) {
                double d6 = MathHelper.lerp(d2, field_240806_a_[j + 1], field_240806_a_[j]);
                int n5 = n2 - 2 + j;
                for (int k = 0; k < 6; ++k) {
                    double d7 = MathHelper.lerp(d3, field_240806_a_[k + 1], field_240806_a_[k]);
                    int n6 = n3 - 2 + k;
                    double d8 = d5 * d6 * d7;
                    d4 += d8;
                    vector3d2 = vector3d2.add(vec3Fetcher.fetch(n4, n5, n6).scale(d8));
                }
            }
        }
        return vector3d2.scale(1.0 / d4);
    }

    public static interface Vec3Fetcher {
        public Vector3d fetch(int var1, int var2, int var3);
    }
}

