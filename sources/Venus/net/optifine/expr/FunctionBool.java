/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.expr;

import net.optifine.expr.FunctionType;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionBool;

public class FunctionBool
implements IExpressionBool {
    private FunctionType type;
    private IExpression[] arguments;

    public FunctionBool(FunctionType functionType, IExpression[] iExpressionArray) {
        this.type = functionType;
        this.arguments = iExpressionArray;
    }

    @Override
    public boolean eval() {
        return this.type.evalBool(this.arguments);
    }

    public String toString() {
        return this.type + "()";
    }
}

