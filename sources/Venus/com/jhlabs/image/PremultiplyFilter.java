/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.PointFilter;

public class PremultiplyFilter
extends PointFilter {
    @Override
    public int filterRGB(int n, int n2, int n3) {
        int n4 = n3 >> 24 & 0xFF;
        int n5 = n3 >> 16 & 0xFF;
        int n6 = n3 >> 8 & 0xFF;
        int n7 = n3 & 0xFF;
        float f = (float)n4 * 0.003921569f;
        n5 = (int)((float)n5 * f);
        n6 = (int)((float)n6 * f);
        n7 = (int)((float)n7 * f);
        return n4 << 24 | n5 << 16 | n6 << 8 | n7;
    }

    public String toString() {
        return "Alpha/Premultiply";
    }
}

