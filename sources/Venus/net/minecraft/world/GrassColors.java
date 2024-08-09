/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

public class GrassColors {
    private static int[] grassBuffer = new int[65536];

    public static void setGrassBiomeColorizer(int[] nArray) {
        grassBuffer = nArray;
    }

    public static int get(double d, double d2) {
        int n = (int)((1.0 - (d2 *= d)) * 255.0);
        int n2 = (int)((1.0 - d) * 255.0);
        int n3 = n << 8 | n2;
        return n3 > grassBuffer.length ? -65281 : grassBuffer[n3];
    }
}

