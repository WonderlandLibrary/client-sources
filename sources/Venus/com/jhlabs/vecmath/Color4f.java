/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.vecmath;

import com.jhlabs.vecmath.Tuple4f;
import java.awt.Color;

public class Color4f
extends Tuple4f {
    public Color4f() {
        this(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public Color4f(float[] fArray) {
        this.x = fArray[0];
        this.y = fArray[1];
        this.z = fArray[2];
        this.w = fArray[3];
    }

    public Color4f(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.z = f3;
        this.w = f4;
    }

    public Color4f(Color4f color4f) {
        this.x = color4f.x;
        this.y = color4f.y;
        this.z = color4f.z;
        this.w = color4f.w;
    }

    public Color4f(Tuple4f tuple4f) {
        this.x = tuple4f.x;
        this.y = tuple4f.y;
        this.z = tuple4f.z;
        this.w = tuple4f.w;
    }

    public Color4f(Color color) {
        this.set(color);
    }

    public void set(Color color) {
        this.set(color.getRGBComponents(null));
    }

    public Color get() {
        return new Color(this.x, this.y, this.z, this.w);
    }
}

