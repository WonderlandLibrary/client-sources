/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

public class FoliageColors {
    private static int[] foliageBuffer = new int[65536];

    public static void setFoliageBiomeColorizer(int[] nArray) {
        foliageBuffer = nArray;
    }

    public static int get(double d, double d2) {
        int n = (int)((1.0 - d) * 255.0);
        int n2 = (int)((1.0 - (d2 *= d)) * 255.0);
        return foliageBuffer[n2 << 8 | n];
    }

    public static int getSpruce() {
        return 0;
    }

    public static int getBirch() {
        return 0;
    }

    public static int getDefault() {
        return 1;
    }
}

