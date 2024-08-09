/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.math;

import com.jhlabs.math.Function2D;
import com.jhlabs.math.Noise;

public class FBM
implements Function2D {
    protected float[] exponents;
    protected float H;
    protected float lacunarity;
    protected float octaves;
    protected Function2D basis;

    public FBM(float f, float f2, float f3) {
        this(f, f2, f3, new Noise());
    }

    public FBM(float f, float f2, float f3, Function2D function2D) {
        this.H = f;
        this.lacunarity = f2;
        this.octaves = f3;
        this.basis = function2D;
        this.exponents = new float[(int)f3 + 1];
        float f4 = 1.0f;
        for (int i = 0; i <= (int)f3; ++i) {
            this.exponents[i] = (float)Math.pow(f4, -f);
            f4 *= f2;
        }
    }

    public void setBasis(Function2D function2D) {
        this.basis = function2D;
    }

    public Function2D getBasisType() {
        return this.basis;
    }

    @Override
    public float evaluate(float f, float f2) {
        int n;
        float f3 = 0.0f;
        f += 371.0f;
        f2 += 529.0f;
        for (n = 0; n < (int)this.octaves; ++n) {
            f3 += this.basis.evaluate(f, f2) * this.exponents[n];
            f *= this.lacunarity;
            f2 *= this.lacunarity;
        }
        float f4 = this.octaves - (float)((int)this.octaves);
        if (f4 != 0.0f) {
            f3 += f4 * this.basis.evaluate(f, f2) * this.exponents[n];
        }
        return f3;
    }
}

