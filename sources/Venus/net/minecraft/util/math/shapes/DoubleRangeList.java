/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math.shapes;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleList;

public class DoubleRangeList
extends AbstractDoubleList {
    private final int field_197854_a;

    DoubleRangeList(int n) {
        this.field_197854_a = n;
    }

    @Override
    public double getDouble(int n) {
        return (double)n / (double)this.field_197854_a;
    }

    @Override
    public int size() {
        return this.field_197854_a + 1;
    }
}

