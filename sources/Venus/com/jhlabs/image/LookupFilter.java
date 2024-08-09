/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.Colormap;
import com.jhlabs.image.Gradient;
import com.jhlabs.image.PointFilter;

public class LookupFilter
extends PointFilter {
    private Colormap colormap = new Gradient();

    public LookupFilter() {
        this.canFilterIndexColorModel = true;
    }

    public LookupFilter(Colormap colormap) {
        this.canFilterIndexColorModel = true;
        this.colormap = colormap;
    }

    public void setColormap(Colormap colormap) {
        this.colormap = colormap;
    }

    public Colormap getColormap() {
        return this.colormap;
    }

    @Override
    public int filterRGB(int n, int n2, int n3) {
        int n4 = n3 >> 16 & 0xFF;
        int n5 = n3 >> 8 & 0xFF;
        int n6 = n3 & 0xFF;
        n3 = (n4 + n5 + n6) / 3;
        return this.colormap.getColor((float)n3 / 255.0f);
    }

    public String toString() {
        return "Colors/Lookup...";
    }
}

