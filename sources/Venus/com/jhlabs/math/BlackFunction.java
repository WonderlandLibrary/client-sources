/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.math;

import com.jhlabs.math.BinaryFunction;

public class BlackFunction
implements BinaryFunction {
    @Override
    public boolean isBlack(int n) {
        return n == -16777216;
    }
}

