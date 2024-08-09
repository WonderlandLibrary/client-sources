/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.Colormap;
import com.jhlabs.image.ImageMath;
import com.jhlabs.image.LinearColormap;
import com.jhlabs.image.PixelUtils;
import com.jhlabs.image.WholeImageFilter;
import java.awt.Rectangle;

public class ShapeFilter
extends WholeImageFilter {
    public static final int LINEAR = 0;
    public static final int CIRCLE_UP = 1;
    public static final int CIRCLE_DOWN = 2;
    public static final int SMOOTH = 3;
    private float factor = 1.0f;
    protected Colormap colormap = new LinearColormap();
    private boolean useAlpha = true;
    private boolean invert = false;
    private boolean merge = false;
    private int type;
    private static final int one = 41;
    private static final int sqrt2 = (int)(41.0 * Math.sqrt(2.0));
    private static final int sqrt5 = (int)(41.0 * Math.sqrt(5.0));

    public void setFactor(float f) {
        this.factor = f;
    }

    public float getFactor() {
        return this.factor;
    }

    public void setColormap(Colormap colormap) {
        this.colormap = colormap;
    }

    public Colormap getColormap() {
        return this.colormap;
    }

    public void setUseAlpha(boolean bl) {
        this.useAlpha = bl;
    }

    public boolean getUseAlpha() {
        return this.useAlpha;
    }

    public void setType(int n) {
        this.type = n;
    }

    public int getType() {
        return this.type;
    }

    public void setInvert(boolean bl) {
        this.invert = bl;
    }

    public boolean getInvert() {
        return this.invert;
    }

    public void setMerge(boolean bl) {
        this.merge = bl;
    }

    public boolean getMerge() {
        return this.merge;
    }

    @Override
    protected int[] filterPixels(int n, int n2, int[] nArray, Rectangle rectangle) {
        int[] nArray2 = new int[n * n2];
        this.makeMap(nArray, nArray2, n, n2);
        int n3 = this.distanceMap(nArray2, n, n2);
        this.applyMap(nArray2, nArray, n, n2, n3);
        return nArray;
    }

    public int distanceMap(int[] nArray, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7 = n - 3;
        int n8 = n2 - 3;
        int n9 = 0;
        for (n6 = 0; n6 < n2; ++n6) {
            for (n5 = 0; n5 < n; ++n5) {
                n4 = n5 + n6 * n;
                if (nArray[n4] <= 0 || (n3 = n5 < 2 || n5 > n7 || n6 < 2 || n6 > n8 ? this.setEdgeValue(n5, n6, nArray, n, n4, n7, n8) : this.setValue(nArray, n, n4)) <= n9) continue;
                n9 = n3;
            }
        }
        for (n6 = n2 - 1; n6 >= 0; --n6) {
            for (n5 = n - 1; n5 >= 0; --n5) {
                n4 = n5 + n6 * n;
                if (nArray[n4] <= 0 || (n3 = n5 < 2 || n5 > n7 || n6 < 2 || n6 > n8 ? this.setEdgeValue(n5, n6, nArray, n, n4, n7, n8) : this.setValue(nArray, n, n4)) <= n9) continue;
                n9 = n3;
            }
        }
        return n9;
    }

    private void makeMap(int[] nArray, int[] nArray2, int n, int n2) {
        for (int i = 0; i < n2; ++i) {
            for (int j = 0; j < n; ++j) {
                int n3 = j + i * n;
                int n4 = this.useAlpha ? nArray[n3] >> 24 & 0xFF : PixelUtils.brightness(nArray[n3]);
                nArray2[n3] = n4 * 41 / 10;
            }
        }
    }

    private void applyMap(int[] nArray, int[] nArray2, int n, int n2, int n3) {
        if (n3 == 0) {
            n3 = 1;
        }
        for (int i = 0; i < n2; ++i) {
            for (int j = 0; j < n; ++j) {
                int n4;
                int n5 = j + i * n;
                int n6 = nArray[n5];
                float f = 0.0f;
                int n7 = 0;
                int n8 = 0;
                int n9 = 0;
                int n10 = 0;
                if (n6 == 0) {
                    n10 = 0;
                    n9 = 0;
                    n8 = 0;
                    n7 = 0;
                    n7 = nArray2[n5] >> 24 & 0xFF;
                } else {
                    f = ImageMath.clamp(this.factor * (float)n6 / (float)n3, 0.0f, 1.0f);
                    switch (this.type) {
                        case 1: {
                            f = ImageMath.circleUp(f);
                            break;
                        }
                        case 2: {
                            f = ImageMath.circleDown(f);
                            break;
                        }
                        case 3: {
                            f = ImageMath.smoothStep(0.0f, 1.0f, f);
                        }
                    }
                    if (this.colormap == null) {
                        n9 = n10 = (int)(f * 255.0f);
                        n8 = n10;
                    } else {
                        n4 = this.colormap.getColor(f);
                        n8 = n4 >> 16 & 0xFF;
                        n9 = n4 >> 8 & 0xFF;
                        n10 = n4 & 0xFF;
                    }
                    int n11 = n7 = this.useAlpha ? nArray2[n5] >> 24 & 0xFF : PixelUtils.brightness(nArray2[n5]);
                    if (this.invert) {
                        n8 = 255 - n8;
                        n9 = 255 - n9;
                        n10 = 255 - n10;
                    }
                }
                if (this.merge) {
                    n4 = 255;
                    int n12 = nArray2[n5];
                    int n13 = (n12 & 0xFF000000) >> 24;
                    int n14 = (n12 & 0xFF0000) >> 16;
                    int n15 = (n12 & 0xFF00) >> 8;
                    int n16 = n12 & 0xFF;
                    n14 = n8 * n14 / n4;
                    n15 = n9 * n15 / n4;
                    n16 = n10 * n16 / n4;
                    if (n14 < 0) {
                        n14 = 0;
                    }
                    if (n14 > 255) {
                        n14 = 255;
                    }
                    if (n15 < 0) {
                        n15 = 0;
                    }
                    if (n15 > 255) {
                        n15 = 255;
                    }
                    if (n16 < 0) {
                        n16 = 0;
                    }
                    if (n16 > 255) {
                        n16 = 255;
                    }
                    nArray2[n5] = n13 << 24 | n14 << 16 | n15 << 8 | n16;
                    continue;
                }
                nArray2[n5] = n7 << 24 | n8 << 16 | n9 << 8 | n10;
            }
        }
    }

    private int setEdgeValue(int n, int n2, int[] nArray, int n3, int n4, int n5, int n6) {
        int n7 = n4 - n3 - n3 - 2;
        int n8 = n7 + n3;
        int n9 = n8 + n3;
        int n10 = n9 + n3;
        int n11 = n10 + n3;
        if (n2 == 0 || n == 0 || n2 == n6 + 2 || n == n5 + 2) {
            nArray[n4] = 41;
            return 41;
        }
        int n12 = nArray[n9 + 1] + 41;
        n12 = nArray[n8 + 2] + 41;
        int n13 = n12;
        if (n12 < n13) {
            n13 = n12;
        }
        if ((n12 = nArray[n9 + 3] + 41) < n13) {
            n13 = n12;
        }
        if ((n12 = nArray[n10 + 2] + 41) < n13) {
            n13 = n12;
        }
        if ((n12 = nArray[n8 + 1] + sqrt2) < n13) {
            n13 = n12;
        }
        if ((n12 = nArray[n8 + 3] + sqrt2) < n13) {
            n13 = n12;
        }
        if ((n12 = nArray[n10 + 1] + sqrt2) < n13) {
            n13 = n12;
        }
        if ((n12 = nArray[n10 + 3] + sqrt2) < n13) {
            n13 = n12;
        }
        if (n2 == 1 || n == 1 || n2 == n6 + 1 || n == n5 + 1) {
            nArray[n4] = n13;
            return nArray[n4];
        }
        n12 = nArray[n7 + 1] + sqrt5;
        if (n12 < n13) {
            n13 = n12;
        }
        if ((n12 = nArray[n7 + 3] + sqrt5) < n13) {
            n13 = n12;
        }
        if ((n12 = nArray[n8 + 4] + sqrt5) < n13) {
            n13 = n12;
        }
        if ((n12 = nArray[n10 + 4] + sqrt5) < n13) {
            n13 = n12;
        }
        if ((n12 = nArray[n11 + 3] + sqrt5) < n13) {
            n13 = n12;
        }
        if ((n12 = nArray[n11 + 1] + sqrt5) < n13) {
            n13 = n12;
        }
        if ((n12 = nArray[n10] + sqrt5) < n13) {
            n13 = n12;
        }
        if ((n12 = nArray[n8] + sqrt5) < n13) {
            n13 = n12;
        }
        nArray[n4] = n13;
        return nArray[n4];
    }

    private int setValue(int[] nArray, int n, int n2) {
        int n3 = n2 - n - n - 2;
        int n4 = n3 + n;
        int n5 = n4 + n;
        int n6 = n5 + n;
        int n7 = n6 + n;
        int n8 = nArray[n5 + 1] + 41;
        n8 = nArray[n4 + 2] + 41;
        int n9 = n8;
        if (n8 < n9) {
            n9 = n8;
        }
        if ((n8 = nArray[n5 + 3] + 41) < n9) {
            n9 = n8;
        }
        if ((n8 = nArray[n6 + 2] + 41) < n9) {
            n9 = n8;
        }
        if ((n8 = nArray[n4 + 1] + sqrt2) < n9) {
            n9 = n8;
        }
        if ((n8 = nArray[n4 + 3] + sqrt2) < n9) {
            n9 = n8;
        }
        if ((n8 = nArray[n6 + 1] + sqrt2) < n9) {
            n9 = n8;
        }
        if ((n8 = nArray[n6 + 3] + sqrt2) < n9) {
            n9 = n8;
        }
        if ((n8 = nArray[n3 + 1] + sqrt5) < n9) {
            n9 = n8;
        }
        if ((n8 = nArray[n3 + 3] + sqrt5) < n9) {
            n9 = n8;
        }
        if ((n8 = nArray[n4 + 4] + sqrt5) < n9) {
            n9 = n8;
        }
        if ((n8 = nArray[n6 + 4] + sqrt5) < n9) {
            n9 = n8;
        }
        if ((n8 = nArray[n7 + 3] + sqrt5) < n9) {
            n9 = n8;
        }
        if ((n8 = nArray[n7 + 1] + sqrt5) < n9) {
            n9 = n8;
        }
        if ((n8 = nArray[n6] + sqrt5) < n9) {
            n9 = n8;
        }
        if ((n8 = nArray[n4] + sqrt5) < n9) {
            n9 = n8;
        }
        nArray[n2] = n9;
        return nArray[n2];
    }

    public String toString() {
        return "Stylize/Shapeburst...";
    }
}

