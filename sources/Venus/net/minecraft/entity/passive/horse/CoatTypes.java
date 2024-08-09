/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive.horse;

import java.util.Arrays;
import java.util.Comparator;

public enum CoatTypes {
    NONE(0),
    WHITE(1),
    WHITE_FIELD(2),
    WHITE_DOTS(3),
    BLACK_DOTS(4);

    private static final CoatTypes[] VALUES;
    private final int id;

    private CoatTypes(int n2) {
        this.id = n2;
    }

    public int getId() {
        return this.id;
    }

    public static CoatTypes func_234248_a_(int n) {
        return VALUES[n % VALUES.length];
    }

    private static CoatTypes[] lambda$static$0(int n) {
        return new CoatTypes[n];
    }

    static {
        VALUES = (CoatTypes[])Arrays.stream(CoatTypes.values()).sorted(Comparator.comparingInt(CoatTypes::getId)).toArray(CoatTypes::lambda$static$0);
    }
}

