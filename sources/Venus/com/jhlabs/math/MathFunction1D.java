/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.math;

import com.jhlabs.math.Function1D;

public class MathFunction1D
implements Function1D {
    public static final int SIN = 1;
    public static final int COS = 2;
    public static final int TAN = 3;
    public static final int SQRT = 4;
    public static final int ASIN = -1;
    public static final int ACOS = -2;
    public static final int ATAN = -3;
    public static final int SQR = -4;
    private int operation;

    public MathFunction1D(int n) {
        this.operation = n;
    }

    @Override
    public float evaluate(float f) {
        switch (this.operation) {
            case 1: {
                return (float)Math.sin(f);
            }
            case 2: {
                return (float)Math.cos(f);
            }
            case 3: {
                return (float)Math.tan(f);
            }
            case 4: {
                return (float)Math.sqrt(f);
            }
            case -1: {
                return (float)Math.asin(f);
            }
            case -2: {
                return (float)Math.acos(f);
            }
            case -3: {
                return (float)Math.atan(f);
            }
            case -4: {
                return f * f;
            }
        }
        return f;
    }
}

