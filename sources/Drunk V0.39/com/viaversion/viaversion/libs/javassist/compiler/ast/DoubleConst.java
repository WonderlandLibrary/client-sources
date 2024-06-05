/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.javassist.compiler.ast;

import com.viaversion.viaversion.libs.javassist.compiler.CompileError;
import com.viaversion.viaversion.libs.javassist.compiler.ast.ASTree;
import com.viaversion.viaversion.libs.javassist.compiler.ast.IntConst;
import com.viaversion.viaversion.libs.javassist.compiler.ast.Visitor;

public class DoubleConst
extends ASTree {
    private static final long serialVersionUID = 1L;
    protected double value;
    protected int type;

    public DoubleConst(double v, int tokenId) {
        this.value = v;
        this.type = tokenId;
    }

    public double get() {
        return this.value;
    }

    public void set(double v) {
        this.value = v;
    }

    public int getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return Double.toString(this.value);
    }

    @Override
    public void accept(Visitor v) throws CompileError {
        v.atDoubleConst(this);
    }

    public ASTree compute(int op, ASTree right) {
        if (right instanceof IntConst) {
            return this.compute0(op, (IntConst)right);
        }
        if (!(right instanceof DoubleConst)) return null;
        return this.compute0(op, (DoubleConst)right);
    }

    private DoubleConst compute0(int op, DoubleConst right) {
        int newType;
        if (this.type != 405 && right.type != 405) {
            newType = 404;
            return DoubleConst.compute(op, this.value, right.value, newType);
        }
        newType = 405;
        return DoubleConst.compute(op, this.value, right.value, newType);
    }

    private DoubleConst compute0(int op, IntConst right) {
        return DoubleConst.compute(op, this.value, right.value, this.type);
    }

    private static DoubleConst compute(int op, double value1, double value2, int newType) {
        switch (op) {
            case 43: {
                double newValue = value1 + value2;
                return new DoubleConst(newValue, newType);
            }
            case 45: {
                double newValue = value1 - value2;
                return new DoubleConst(newValue, newType);
            }
            case 42: {
                double newValue = value1 * value2;
                return new DoubleConst(newValue, newType);
            }
            case 47: {
                double newValue = value1 / value2;
                return new DoubleConst(newValue, newType);
            }
            case 37: {
                double newValue = value1 % value2;
                return new DoubleConst(newValue, newType);
            }
        }
        return null;
    }
}

