/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.composite;

import com.jhlabs.composite.RGBComposite;
import java.awt.CompositeContext;
import java.awt.RenderingHints;
import java.awt.image.ColorModel;

public final class AverageComposite
extends RGBComposite {
    public AverageComposite(float f) {
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
                int n2 = nArray[i];
                int n3 = nArray2[i];
                int n4 = nArray[i + 1];
                int n5 = nArray2[i + 1];
                int n6 = nArray[i + 2];
                int n7 = nArray2[i + 2];
                int n8 = nArray[i + 3];
                int n9 = nArray2[i + 3];
                int n10 = (n3 + n2) / 2;
                int n11 = (n5 + n4) / 2;
                int n12 = (n7 + n6) / 2;
                float f2 = f * (float)n8 / 255.0f;
                float f3 = 1.0f - f2;
                nArray2[i] = (int)(f2 * (float)n10 + f3 * (float)n3);
                nArray2[i + 1] = (int)(f2 * (float)n11 + f3 * (float)n5);
                nArray2[i + 2] = (int)(f2 * (float)n12 + f3 * (float)n7);
                nArray2[i + 3] = (int)((float)n8 * f + (float)n9 * f3);
            }
        }
    }
}

