/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.expr;

import net.optifine.expr.ExpressionType;
import net.optifine.expr.IExpression;

public interface IExpressionFloat
extends IExpression {
    public float eval();

    @Override
    default public ExpressionType getExpressionType() {
        return ExpressionType.FLOAT;
    }
}

