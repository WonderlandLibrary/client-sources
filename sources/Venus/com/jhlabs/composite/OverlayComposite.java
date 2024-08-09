/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.composite;

import com.jhlabs.composite.RGBComposite;
import java.awt.CompositeContext;
import java.awt.RenderingHints;
import java.awt.image.ColorModel;

public final class OverlayComposite
extends RGBComposite {
    public OverlayComposite(float f) {
        super(f);
    }

    @Override
    public CompositeContext createContext(ColorModel colorModel, ColorModel colorModel2, RenderingHints renderingHints) {
        return new Context(this.extraAlpha, colorModel, colorModel2);
    }

    static class Context
    extends RGBComposite.RGBCompositeContext {
        public Context(float f, ColorModel colorModel, ColorModel colorModel2) {
            super(f, colorModel, colorModel2);
        }

        @Override
        public void composeRGB(int[] nArray, int[] nArray2, float f) {
            int n = nArray.length;
            for (int i = 0; i < n; i += 4) {
                int n2;
                int n3;
                int n4;
                int n5;
                int n6 = nArray[i];
                int n7 = nArray2[i];
                int n8 = nArray[i + 1];
                int n9 = nArray2[i + 1];
                int n10 = nArray[i + 2];
                int n11 = nArray2[i + 2];
                int n12 = nArray[i + 3];
                int n13 = nArray2[i + 3];
                if (n7 < 128) {
                    n5 = n7 * n6 + 128;
                    n4 = 2 * ((n5 >> 8) + n5 >> 8);
                } else {
                    n5 = (255 - n7) * (255 - n6) + 128;
                    n4 = 2 * (255 - ((n5 >> 8) + n5 >> 8));
                }
                if (n9 < 128) {
                    n5 = n9 * n8 + 128;
                    n3 = 2 * ((n5 >> 8) + n5 >> 8);
                } else {
                    n5 = (255 - n9) * (255 - n8) + 128;
                    n3 = 2 * (255 - ((n5 >> 8) + n5 >> 8));
                }
                if (n11 < 128) {
                    n5 = n11 * n10 + 128;
                    n2 = 2 * ((n5 >> 8) + n5 >> 8);
                } else {
                    n5 = (255 - n11) * (255 - n10) + 128;
                    n2 = 2 * (255 - ((n5 >> 8) + n5 >> 8));
                }
                float f2 = f * (float)n12 / 255.0f;
                float f3 = 1.0f - f2;
                nArray2[i] = (int)(f2 * (float)n4 + f3 * (float)n7);
                nArray2[i + 1] = (int)(f2 * (float)n3 + f3 * (float)n9);
                nArray2[i + 2] = (int)(f2 * (float)n2 + f3 * (float)n11);
                nArray2[i + 3] = (int)((float)n12 * f + (float)n13 * f3);
            }
        }
    }
}

