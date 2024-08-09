/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.Colormap;
import com.jhlabs.image.ImageMath;

public class LinearColormap
implements Colormap {
    private int color1;
    private int color2;

    public LinearColormap() {
        this(-16777216, -1);
    }

    public LinearColormap(int n, int n2) {
        this.color1 = n;
        this.color2 = n2;
    }

    public void setColor1(int n) {
        this.color1 = n;
    }

    public int getColor1() {
        return this.color1;
    }

    public void setColor2(int n) {
        this.color2 = n;
    }

    public int getColor2() {
        return this.color2;
    }

    @Override
    public int getColor(float f) {
        return ImageMath.mixColors(ImageMath.clamp(f, 0.0f, 1.0f), this.color1, this.color2);
    }
}

