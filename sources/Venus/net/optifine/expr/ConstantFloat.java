/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.expr;

import net.optifine.expr.IExpressionFloat;

public class ConstantFloat
implements IExpressionFloat {
    private float value;

    public ConstantFloat(float f) {
        this.value = f;
    }

    @Override
    public float eval() {
        return this.value;
    }

    public String toString() {
        return "" + this.value;
    }
}

