/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.expr;

import net.optifine.expr.ExpressionType;
import net.optifine.expr.IExpressionCached;
import net.optifine.expr.IExpressionFloatArray;

public class ExpressionFloatArrayCached
implements IExpressionFloatArray,
IExpressionCached {
    private IExpressionFloatArray expression;
    private boolean cached;
    private float[] value;

    public ExpressionFloatArrayCached(IExpressionFloatArray iExpressionFloatArray) {
        this.expression = iExpressionFloatArray;
    }

    @Override
    public float[] eval() {
        if (!this.cached) {
            this.value = this.expression.eval();
            this.cached = true;
        }
        return this.value;
    }

    @Override
    public void reset() {
        this.cached = false;
    }

    @Override
    public ExpressionType getExpressionType() {
        return ExpressionType.FLOAT;
    }

    public String toString() {
        return "cached(" + this.expression + ")";
    }
}

