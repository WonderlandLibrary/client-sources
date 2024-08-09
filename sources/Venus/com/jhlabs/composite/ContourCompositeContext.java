/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.composite;

import java.awt.CompositeContext;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

class ContourCompositeContext
implements CompositeContext {
    private int offset;

    public ContourCompositeContext(int n, ColorModel colorModel, ColorModel colorModel2) {
        this.offset = n;
    }

    @Override
    public void dispose() {
    }

    @Override
    public void compose(Raster raster, Raster raster2, WritableRaster writableRaster) {
        int n = raster.getMinX();
        int n2 = raster.getMinY();
        int n3 = raster.getWidth();
        int n4 = raster.getHeight();
        int[] nArray = null;
        int[] nArray2 = null;
        int[] nArray3 = null;
        int[] nArray4 = new int[n3 * 4];
        for (int i = 0; i < n4; ++i) {
            nArray = raster.getPixels(n, n2, n3, 1, nArray);
            nArray3 = raster2.getPixels(n, n2, n3, 1, nArray3);
            int n5 = 0;
            int n6 = 0;
            for (int j = 0; j < n3; ++j) {
                int n7;
                int n8 = nArray[n6 + 3];
                int n9 = n7 = i != 0 ? nArray2[n6 + 3] : n8;
                if (i != 0 && j != 0 && ((n8 ^ n5) & 0x80) != 0 || ((n8 ^ n7) & 0x80) != 0) {
                    if ((this.offset + i + j) % 10 > 4) {
                        nArray4[n6] = 0;
                        nArray4[n6 + 1] = 0;
                        nArray4[n6 + 2] = 0;
                    } else {
                        nArray4[n6] = 255;
                        nArray4[n6 + 1] = 255;
                        nArray4[n6 + 2] = 127;
                    }
                    nArray4[n6 + 3] = 255;
                } else {
                    nArray4[n6] = nArray3[n6];
                    nArray4[n6 + 1] = nArray3[n6 + 1];
                    nArray4[n6 + 2] = nArray3[n6 + 2];
                    nArray4[n6] = 255;
                    nArray4[n6 + 1] = 0;
                    nArray4[n6 + 2] = 0;
                    nArray4[n6 + 3] = 0;
                }
                n5 = n8;
                n6 += 4;
            }
            writableRaster.setPixels(n, n2, n3, 1, nArray4);
            int[] nArray5 = nArray;
            nArray = nArray2;
            nArray2 = nArray5;
            ++n2;
        }
    }
}

