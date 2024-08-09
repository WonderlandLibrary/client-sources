/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.stb;

import org.lwjgl.stb.LibSTB;

public class STBPerlin {
    protected STBPerlin() {
        throw new UnsupportedOperationException();
    }

    public static native float stb_perlin_noise3(float var0, float var1, float var2, int var3, int var4, int var5);

    public static native float stb_perlin_noise3_seed(float var0, float var1, float var2, int var3, int var4, int var5, int var6);

    public static native float stb_perlin_ridge_noise3(float var0, float var1, float var2, float var3, float var4, float var5, int var6);

    public static native float stb_perlin_fbm_noise3(float var0, float var1, float var2, float var3, float var4, int var5);

    public static native float stb_perlin_turbulence_noise3(float var0, float var1, float var2, float var3, float var4, int var5);

    static {
        LibSTB.initialize();
    }
}

