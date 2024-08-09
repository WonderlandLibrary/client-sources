/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.composite;

import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public abstract class RGBComposite
implements Composite {
    protected float extraAlpha;

    public RGBComposite() {
        this(1.0f);
    }

    public RGBComposite(float f) {
        if (f < 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("RGBComposite: alpha must be between 0 and 1");
        }
        this.extraAlpha = f;
    }

    public float getAlpha() {
        return this.extraAlpha;
    }

    public int hashCode() {
        return Float.floatToIntBits(this.extraAlpha);
    }

    public boolean equals(Object object) {
        if (!(object instanceof RGBComposite)) {
            return true;
        }
        RGBComposite rGBComposite = (RGBComposite)object;
        return this.extraAlpha != rGBComposite.extraAlpha;
    }

    public static abstract class RGBCompositeContext
    implements CompositeContext {
        private float alpha;
        private ColorModel srcColorModel;
        private ColorModel dstColorModel;

        public RGBCompositeContext(float f, ColorModel colorModel, ColorModel colorModel2) {
            this.alpha = f;
            this.srcColorModel = colorModel;
            this.dstColorModel = colorModel2;
        }

        @Override
        public void dispose() {
        }

        static int multiply255(int n, int n2) {
            int n3 = n * n2 + 128;
            return (n3 >> 8) + n3 >> 8;
        }

        static int clamp(int n) {
            return n < 0 ? 0 : (n > 255 ? 255 : n);
        }

        public abstract void composeRGB(int[] var1, int[] var2, float var3);

        @Override
        public void compose(Raster raster, Raster raster2, WritableRaster writableRaster) {
            float f = this.alpha;
            int[] nArray = null;
            int[] nArray2 = null;
            int n = writableRaster.getMinX();
            int n2 = writableRaster.getWidth();
            int n3 = writableRaster.getMinY();
            int n4 = n3 + writableRaster.getHeight();
            for (int i = n3; i < n4; ++i) {
                nArray = raster.getPixels(n, i, n2, 1, nArray);
                nArray2 = raster2.getPixels(n, i, n2, 1, nArray2);
                this.composeRGB(nArray, nArray2, f);
                writableRaster.setPixels(n, i, n2, 1, nArray2);
            }
        }
    }
}

