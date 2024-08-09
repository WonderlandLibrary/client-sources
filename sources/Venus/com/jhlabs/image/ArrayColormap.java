/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.Colormap;
import com.jhlabs.image.ImageMath;

public class ArrayColormap
implements Colormap,
Cloneable {
    protected int[] map;

    public ArrayColormap() {
        this.map = new int[256];
    }

    public ArrayColormap(int[] nArray) {
        this.map = nArray;
    }

    public Object clone() {
        try {
            ArrayColormap arrayColormap = (ArrayColormap)super.clone();
            arrayColormap.map = (int[])this.map.clone();
            return arrayColormap;
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            return null;
        }
    }

    public void setMap(int[] nArray) {
        this.map = nArray;
    }

    public int[] getMap() {
        return this.map;
    }

    @Override
    public int getColor(float f) {
        int n = (int)(f * 255.0f);
        if (n < 0) {
            n = 0;
        } else if (n > 255) {
            n = 255;
        }
        return this.map[n];
    }

    public void setColorInterpolated(int n, int n2, int n3, int n4) {
        int n5;
        int n6 = this.map[n2];
        int n7 = this.map[n3];
        for (n5 = n2; n5 <= n; ++n5) {
            this.map[n5] = ImageMath.mixColors((float)(n5 - n2) / (float)(n - n2), n6, n4);
        }
        for (n5 = n; n5 < n3; ++n5) {
            this.map[n5] = ImageMath.mixColors((float)(n5 - n) / (float)(n3 - n), n4, n7);
        }
    }

    public void setColorRange(int n, int n2, int n3, int n4) {
        for (int i = n; i <= n2; ++i) {
            this.map[i] = ImageMath.mixColors((float)(i - n) / (float)(n2 - n), n3, n4);
        }
    }

    public void setColorRange(int n, int n2, int n3) {
        for (int i = n; i <= n2; ++i) {
            this.map[i] = n3;
        }
    }

    public void setColor(int n, int n2) {
        this.map[n] = n2;
    }
}

