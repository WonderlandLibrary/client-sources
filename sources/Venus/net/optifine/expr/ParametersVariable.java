/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.expr;

import java.util.ArrayList;
import java.util.Arrays;
import net.optifine.expr.ExpressionType;
import net.optifine.expr.IExpression;
import net.optifine.expr.IParameters;

public class ParametersVariable
implements IParameters {
    private ExpressionType[] first;
    private ExpressionType[] repeat;
    private ExpressionType[] last;
    private int maxCount = Integer.MAX_VALUE;
    private static final ExpressionType[] EMPTY = new ExpressionType[0];

    public ParametersVariable() {
        this(null, null, null);
    }

    public ParametersVariable(ExpressionType[] expressionTypeArray, ExpressionType[] expressionTypeArray2, ExpressionType[] expressionTypeArray3) {
        this(expressionTypeArray, expressionTypeArray2, expressionTypeArray3, Integer.MAX_VALUE);
    }

    public ParametersVariable(ExpressionType[] expressionTypeArray, ExpressionType[] expressionTypeArray2, ExpressionType[] expressionTypeArray3, int n) {
        this.first = ParametersVariable.normalize(expressionTypeArray);
        this.repeat = ParametersVariable.normalize(expressionTypeArray2);
        this.last = ParametersVariable.normalize(expressionTypeArray3);
        this.maxCount = n;
    }

    private static ExpressionType[] normalize(ExpressionType[] expressionTypeArray) {
        return expressionTypeArray == null ? EMPTY : expressionTypeArray;
    }

    public ExpressionType[] getFirst() {
        return this.first;
    }

    public ExpressionType[] getRepeat() {
        return this.repeat;
    }

    public ExpressionType[] getLast() {
        return this.last;
    }

    public int getCountRepeat() {
        return this.first == null ? 0 : this.first.length;
    }

    @Override
    public ExpressionType[] getParameterTypes(IExpression[] iExpressionArray) {
        int n = this.first.length + this.last.length;
        int n2 = iExpressionArray.length - n;
        int n3 = 0;
        int n4 = 0;
        while (n4 + this.repeat.length <= n2 && n + n4 + this.repeat.length <= this.maxCount) {
            ++n3;
            n4 += this.repeat.length;
        }
        ArrayList<ExpressionType> arrayList = new ArrayList<ExpressionType>();
        arrayList.addAll(Arrays.asList(this.first));
        for (int i = 0; i < n3; ++i) {
            arrayList.addAll(Arrays.asList(this.repeat));
        }
        arrayList.addAll(Arrays.asList(this.last));
        return arrayList.toArray(new ExpressionType[arrayList.size()]);
    }

    public ParametersVariable first(ExpressionType ... expressionTypeArray) {
        return new ParametersVariable(expressionTypeArray, this.repeat, this.last);
    }

    public ParametersVariable repeat(ExpressionType ... expressionTypeArray) {
        return new ParametersVariable(this.first, expressionTypeArray, this.last);
    }

    public ParametersVariable last(ExpressionType ... expressionTypeArray) {
        return new ParametersVariable(this.first, this.repeat, expressionTypeArray);
    }

    public ParametersVariable maxCount(int n) {
        return new ParametersVariable(this.first, this.repeat, this.last, n);
    }
}

