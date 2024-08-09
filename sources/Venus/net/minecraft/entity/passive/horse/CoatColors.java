/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive.horse;

import java.util.Arrays;
import java.util.Comparator;

public enum CoatColors {
    WHITE(0),
    CREAMY(1),
    CHESTNUT(2),
    BROWN(3),
    BLACK(4),
    GRAY(5),
    DARKBROWN(6);

    private static final CoatColors[] VALUES;
    private final int id;

    private CoatColors(int n2) {
        this.id = n2;
    }

    public int getId() {
        return this.id;
    }

    public static CoatColors func_234254_a_(int n) {
        return VALUES[n % VALUES.length];
    }

    private static CoatColors[] lambda$static$0(int n) {
        return new CoatColors[n];
    }

    static {
        VALUES = (CoatColors[])Arrays.stream(CoatColors.values()).sorted(Comparator.comparingInt(CoatColors::getId)).toArray(CoatColors::lambda$static$0);
    }
}

