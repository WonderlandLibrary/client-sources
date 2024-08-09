/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.WholeImageFilter;
import java.awt.Rectangle;

public class DespeckleFilter
extends WholeImageFilter {
    private short pepperAndSalt(short s, short s2, short s3) {
        if (s < s2) {
            s = (short)(s + 1);
        }
        if (s < s3) {
            s = (short)(s + 1);
        }
        if (s > s2) {
            s = (short)(s - 1);
        }
        if (s > s3) {
            s = (short)(s - 1);
        }
        return s;
    }

    @Override
    protected int[] filterPixels(int n, int n2, int[] nArray, Rectangle rectangle) {
        int n3;
        int n4;
        int n5 = 0;
        short[][] sArray = new short[3][n];
        short[][] sArray2 = new short[3][n];
        short[][] sArray3 = new short[3][n];
        int[] nArray2 = new int[n * n2];
        for (n4 = 0; n4 < n; ++n4) {
            n3 = nArray[n4];
            sArray[0][n4] = (short)(n3 >> 16 & 0xFF);
            sArray2[0][n4] = (short)(n3 >> 8 & 0xFF);
            sArray3[0][n4] = (short)(n3 & 0xFF);
        }
        for (n4 = 0; n4 < n2; ++n4) {
            int n6;
            int n7;
            n3 = n4 > 0 && n4 < n2 - 1 ? 1 : 0;
            int n8 = n5 + n;
            if (n4 < n2 - 1) {
                for (n7 = 0; n7 < n; ++n7) {
                    n6 = nArray[n8++];
                    sArray[5][n7] = (short)(n6 >> 16 & 0xFF);
                    sArray2[5][n7] = (short)(n6 >> 8 & 0xFF);
                    sArray3[5][n7] = (short)(n6 & 0xFF);
                }
            }
            for (n7 = 0; n7 < n; ++n7) {
                n6 = n7 > 0 && n7 < n - 1 ? 1 : 0;
                short s = sArray[0][n7];
                short s2 = sArray2[0][n7];
                short s3 = sArray3[0][n7];
                int n9 = n7 - 1;
                int n10 = n7 + 1;
                if (n3 != 0) {
                    s = this.pepperAndSalt(s, sArray[5][n7], sArray[5][n7]);
                    s2 = this.pepperAndSalt(s2, sArray2[5][n7], sArray2[5][n7]);
                    s3 = this.pepperAndSalt(s3, sArray3[5][n7], sArray3[5][n7]);
                }
                if (n6 != 0) {
                    s = this.pepperAndSalt(s, sArray[0][n9], sArray[0][n10]);
                    s2 = this.pepperAndSalt(s2, sArray2[0][n9], sArray2[0][n10]);
                    s3 = this.pepperAndSalt(s3, sArray3[0][n9], sArray3[0][n10]);
                }
                if (n3 != 0 && n6 != 0) {
                    s = this.pepperAndSalt(s, sArray[5][n9], sArray[5][n10]);
                    s2 = this.pepperAndSalt(s2, sArray2[5][n9], sArray2[5][n10]);
                    s3 = this.pepperAndSalt(s3, sArray3[5][n9], sArray3[5][n10]);
                    s = this.pepperAndSalt(s, sArray[5][n9], sArray[5][n10]);
                    s2 = this.pepperAndSalt(s2, sArray2[5][n9], sArray2[5][n10]);
                    s3 = this.pepperAndSalt(s3, sArray3[5][n9], sArray3[5][n10]);
                }
                nArray2[n5] = nArray[n5] & 0xFF000000 | s << 16 | s2 << 8 | s3;
                ++n5;
            }
            short[] sArray4 = sArray[5];
            sArray[0] = sArray[0];
            sArray[1] = sArray[5];
            sArray[2] = sArray4;
            sArray4 = sArray2[5];
            sArray2[0] = sArray2[0];
            sArray2[1] = sArray2[5];
            sArray2[2] = sArray4;
            sArray4 = sArray3[5];
            sArray3[0] = sArray3[0];
            sArray3[1] = sArray3[5];
            sArray3[2] = sArray4;
        }
        return nArray2;
    }

    public String toString() {
        return "Blur/Despeckle...";
    }
}

