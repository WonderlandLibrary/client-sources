/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.javassist.compiler.ast;

import com.viaversion.viaversion.libs.javassist.compiler.CompileError;
import com.viaversion.viaversion.libs.javassist.compiler.ast.ASTree;
import com.viaversion.viaversion.libs.javassist.compiler.ast.DoubleConst;
import com.viaversion.viaversion.libs.javassist.compiler.ast.Visitor;

public class IntConst
extends ASTree {
    private static final long serialVersionUID = 1L;
    protected long value;
    protected int type;

    public IntConst(long v, int tokenId) {
        this.value = v;
        this.type = tokenId;
    }

    public long get() {
        return this.value;
    }

    public void set(long v) {
        this.value = v;
    }

    public int getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return Long.toString(this.value);
    }

    @Override
    public void accept(Visitor v) throws CompileError {
        v.atIntConst(this);
    }

    public ASTree compute(int op, ASTree right) {
        if (right instanceof IntConst) {
            return this.compute0(op, (IntConst)right);
        }
        if (!(right instanceof DoubleConst)) return null;
        return this.compute0(op, (DoubleConst)right);
    }

    private IntConst compute0(int op, IntConst right) {
        int type1 = this.type;
        int type2 = right.type;
        int newType = type1 == 403 || type2 == 403 ? 403 : (type1 == 401 && type2 == 401 ? 401 : 402);
        long value1 = this.value;
        long value2 = right.value;
        switch (op) {
            case 43: {
                long newValue = value1 + value2;
                return new IntConst(newValue, newType);
            }
            case 45: {
                long newValue = value1 - value2;
                return new IntConst(newValue, newType);
            }
            case 42: {
                long newValue = value1 * value2;
                return new IntConst(newValue, newType);
            }
            case 47: {
                long newValue = value1 / value2;
                return new IntConst(newValue, newType);
            }
            case 37: {
                long newValue = value1 % value2;
                return new IntConst(newValue, newType);
            }
            case 124: {
                long newValue = value1 | value2;
                return new IntConst(newValue, newType);
            }
            case 94: {
                long newValue = value1 ^ value2;
                return new IntConst(newValue, newType);
            }
            case 38: {
                long newValue = value1 & value2;
                return new IntConst(newValue, newType);
            }
            case 364: {
                long newValue = this.value << (int)value2;
                newType = type1;
                return new IntConst(newValue, newType);
            }
            case 366: {
                long newValue = this.value >> (int)value2;
                newType = type1;
                return new IntConst(newValue, newType);
            }
            case 370: {
                long newValue = this.value >>> (int)value2;
                newType = type1;
                return new IntConst(newValue, newType);
            }
        }
        return null;
    }

    private DoubleConst compute0(int op, DoubleConst right) {
        double value1 = this.value;
        double value2 = right.value;
        switch (op) {
            case 43: {
                double newValue = value1 + value2;
                return new DoubleConst(newValue, right.type);
            }
            case 45: {
                double newValue = value1 - value2;
                return new DoubleConst(newValue, right.type);
            }
            case 42: {
                double newValue = value1 * value2;
                return new DoubleConst(newValue, right.type);
            }
            case 47: {
                double newValue = value1 / value2;
                return new DoubleConst(newValue, right.type);
            }
            case 37: {
                double newValue = value1 % value2;
                return new DoubleConst(newValue, right.type);
            }
        }
        return null;
    }
}

