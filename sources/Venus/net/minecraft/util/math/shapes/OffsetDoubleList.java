/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math.shapes;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleList;

public class OffsetDoubleList
extends AbstractDoubleList {
    private final DoubleList delegate;
    private final double offset;

    public OffsetDoubleList(DoubleList doubleList, double d) {
        this.delegate = doubleList;
        this.offset = d;
    }

    @Override
    public double getDouble(int n) {
        return this.delegate.getDouble(n) + this.offset;
    }

    @Override
    public int size() {
        return this.delegate.size();
    }
}

