/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.ImageMath;
import com.jhlabs.image.PointFilter;

public class JavaLnFFilter
extends PointFilter {
    @Override
    public int filterRGB(int n, int n2, int n3) {
        if ((n & 1) == (n2 & 1)) {
            return n3;
        }
        return ImageMath.mixColors(0.25f, -6710887, n3);
    }

    public String toString() {
        return "Stylize/Java L&F Stipple";
    }
}

