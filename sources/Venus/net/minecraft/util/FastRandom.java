/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

public class FastRandom {
    public static long mix(long l, long l2) {
        l *= l * 6364136223846793005L + 1442695040888963407L;
        return l + l2;
    }
}

