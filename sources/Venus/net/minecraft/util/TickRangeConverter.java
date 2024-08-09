/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import net.minecraft.util.RangedInteger;

public class TickRangeConverter {
    public static RangedInteger convertRange(int n, int n2) {
        return new RangedInteger(n * 20, n2 * 20);
    }
}

