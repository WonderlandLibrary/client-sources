/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.PointFilter;

public class UnpremultiplyFilter
extends PointFilter {
    @Override
    public int filterRGB(int n, int n2, int n3) {
        int n4 = n3 >> 24 & 0xFF;
        int n5 = n3 >> 16 & 0xFF;
        int n6 = n3 >> 8 & 0xFF;
        int n7 = n3 & 0xFF;
        if (n4 == 0 || n4 == 255) {
            return n3;
        }
        float f = 255.0f / (float)n4;
        n5 = (int)((float)n5 * f);
        n6 = (int)((float)n6 * f);
        n7 = (int)((float)n7 * f);
        if (n5 > 255) {
            n5 = 255;
        }
        if (n6 > 255) {
            n6 = 255;
        }
        if (n7 > 255) {
            n7 = 255;
        }
        return n4 << 24 | n5 << 16 | n6 << 8 | n7;
    }

    public String toString() {
        return "Alpha/Unpremultiply";
    }
}

