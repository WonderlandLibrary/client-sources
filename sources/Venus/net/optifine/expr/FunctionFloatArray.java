/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.expr;

import net.optifine.expr.FunctionType;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionFloatArray;

public class FunctionFloatArray
implements IExpressionFloatArray {
    private FunctionType type;
    private IExpression[] arguments;

    public FunctionFloatArray(FunctionType functionType, IExpression[] iExpressionArray) {
        this.type = functionType;
        this.arguments = iExpressionArray;
    }

    @Override
    public float[] eval() {
        return this.type.evalFloatArray(this.arguments);
    }

    public String toString() {
        return this.type + "()";
    }
}

