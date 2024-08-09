/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.Colormap;

public class GrayscaleColormap
implements Colormap {
    @Override
    public int getColor(float f) {
        int n = (int)(f * 255.0f);
        if (n < 0) {
            n = 0;
        } else if (n > 255) {
            n = 255;
        }
        return 0xFF000000 | n << 16 | n << 8 | n;
    }
}

