/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.Colormap;
import com.jhlabs.image.ImageMath;
import com.jhlabs.image.LinearColormap;
import com.jhlabs.image.WholeImageFilter;
import java.awt.Rectangle;
import java.util.Date;
import java.util.Random;

public class SmearFilter
extends WholeImageFilter {
    public static final int CROSSES = 0;
    public static final int LINES = 1;
    public static final int CIRCLES = 2;
    public static final int SQUARES = 3;
    private Colormap colormap = new LinearColormap();
    private float angle = 0.0f;
    private float density = 0.5f;
    private float scatter = 0.0f;
    private int distance = 8;
    private Random randomGenerator = new Random();
    private long seed = 567L;
    private int shape = 1;
    private float mix = 0.5f;
    private int fadeout = 0;
    private boolean background = false;

    public void setShape(int n) {
        this.shape = n;
    }

    public int getShape() {
        return this.shape;
    }

    public void setDistance(int n) {
        this.distance = n;
    }

    public int getDistance() {
        return this.distance;
    }

    public void setDensity(float f) {
        this.density = f;
    }

    public float getDensity() {
        return this.density;
    }

    public void setScatter(float f) {
        this.scatter = f;
    }

    public float getScatter() {
        return this.scatter;
    }

    public void setAngle(float f) {
        this.angle = f;
    }

    public float getAngle() {
        return this.angle;
    }

    public void setMix(float f) {
        this.mix = f;
    }

    public float getMix() {
        return this.mix;
    }

    public void setFadeout(int n) {
        this.fadeout = n;
    }

    public int getFadeout() {
        return this.fadeout;
    }

    public void setBackground(boolean bl) {
        this.background = bl;
    }

    public boolean getBackground() {
        return this.background;
    }

    public void randomize() {
        this.seed = new Date().getTime();
    }

    private float random(float f, float f2) {
        return f + (f2 - f) * this.randomGenerator.nextFloat();
    }

    @Override
    protected int[] filterPixels(int n, int n2, int[] nArray, Rectangle rectangle) {
        int n3;
        int n4;
        int[] nArray2 = new int[n * n2];
        this.randomGenerator.setSeed(this.seed);
        float f = (float)Math.sin(this.angle);
        float f2 = (float)Math.cos(this.angle);
        int n5 = 0;
        for (n4 = 0; n4 < n2; ++n4) {
            for (n3 = 0; n3 < n; ++n3) {
                nArray2[n5] = this.background ? -1 : nArray[n5];
                ++n5;
            }
        }
        switch (this.shape) {
            case 0: {
                int n6 = (int)(2.0f * this.density * (float)n * (float)n2 / (float)(this.distance + 1));
                for (n5 = 0; n5 < n6; ++n5) {
                    int n7;
                    int n8;
                    n4 = (this.randomGenerator.nextInt() & Integer.MAX_VALUE) % n;
                    n3 = (this.randomGenerator.nextInt() & Integer.MAX_VALUE) % n2;
                    int n9 = this.randomGenerator.nextInt() % this.distance + 1;
                    int n10 = nArray[n3 * n + n4];
                    for (n8 = n4 - n9; n8 < n4 + n9 + 1; ++n8) {
                        if (n8 < 0 || n8 >= n) continue;
                        n7 = this.background ? -1 : nArray2[n3 * n + n8];
                        nArray2[n3 * n + n8] = ImageMath.mixColors(this.mix, n7, n10);
                    }
                    for (n8 = n3 - n9; n8 < n3 + n9 + 1; ++n8) {
                        if (n8 < 0 || n8 >= n2) continue;
                        n7 = this.background ? -1 : nArray2[n8 * n + n4];
                        nArray2[n8 * n + n4] = ImageMath.mixColors(this.mix, n7, n10);
                    }
                }
                break;
            }
            case 1: {
                int n11 = (int)(2.0f * this.density * (float)n * (float)n2 / 2.0f);
                for (n5 = 0; n5 < n11; ++n5) {
                    int n12;
                    int n13;
                    int n14;
                    int n15;
                    n4 = (this.randomGenerator.nextInt() & Integer.MAX_VALUE) % n;
                    n3 = (this.randomGenerator.nextInt() & Integer.MAX_VALUE) % n2;
                    int n16 = nArray[n3 * n + n4];
                    int n17 = (this.randomGenerator.nextInt() & Integer.MAX_VALUE) % this.distance;
                    int n18 = (int)((float)n17 * f2);
                    int n19 = (int)((float)n17 * f);
                    int n20 = n4 - n18;
                    int n21 = n3 - n19;
                    int n22 = n4 + n18;
                    int n23 = n3 + n19;
                    int n24 = n22 < n20 ? -1 : 1;
                    int n25 = n23 < n21 ? -1 : 1;
                    n18 = n22 - n20;
                    n19 = n23 - n21;
                    n18 = Math.abs(n18);
                    n19 = Math.abs(n19);
                    int n26 = n20;
                    int n27 = n21;
                    if (n26 < n && n26 >= 0 && n27 < n2 && n27 >= 0) {
                        n15 = this.background ? -1 : nArray2[n27 * n + n26];
                        nArray2[n27 * n + n26] = ImageMath.mixColors(this.mix, n15, n16);
                    }
                    if (Math.abs(n18) > Math.abs(n19)) {
                        n14 = 2 * n19 - n18;
                        n13 = 2 * n19;
                        n12 = 2 * (n19 - n18);
                        while (n26 != n22) {
                            if (n14 <= 0) {
                                n14 += n13;
                            } else {
                                n14 += n12;
                                n27 += n25;
                            }
                            if ((n26 += n24) >= n || n26 < 0 || n27 >= n2 || n27 < 0) continue;
                            n15 = this.background ? -1 : nArray2[n27 * n + n26];
                            nArray2[n27 * n + n26] = ImageMath.mixColors(this.mix, n15, n16);
                        }
                        continue;
                    }
                    n14 = 2 * n18 - n19;
                    n13 = 2 * n18;
                    n12 = 2 * (n18 - n19);
                    while (n27 != n23) {
                        if (n14 <= 0) {
                            n14 += n13;
                        } else {
                            n14 += n12;
                            n26 += n24;
                        }
                        if (n26 >= n || n26 < 0 || (n27 += n25) >= n2 || n27 < 0) continue;
                        n15 = this.background ? -1 : nArray2[n27 * n + n26];
                        nArray2[n27 * n + n26] = ImageMath.mixColors(this.mix, n15, n16);
                    }
                }
                break;
            }
            case 2: 
            case 3: {
                n4 = this.distance + 1;
                n3 = n4 * n4;
                int n28 = (int)(2.0f * this.density * (float)n * (float)n2 / (float)n4);
                for (n5 = 0; n5 < n28; ++n5) {
                    int n29 = (this.randomGenerator.nextInt() & Integer.MAX_VALUE) % n;
                    int n30 = (this.randomGenerator.nextInt() & Integer.MAX_VALUE) % n2;
                    int n31 = nArray[n30 * n + n29];
                    for (int i = n29 - n4; i < n29 + n4 + 1; ++i) {
                        for (int j = n30 - n4; j < n30 + n4 + 1; ++j) {
                            int n32 = this.shape == 2 ? (i - n29) * (i - n29) + (j - n30) * (j - n30) : 0;
                            if (i < 0 || i >= n || j < 0 || j >= n2 || n32 > n3) continue;
                            int n33 = this.background ? -1 : nArray2[j * n + i];
                            nArray2[j * n + i] = ImageMath.mixColors(this.mix, n33, n31);
                        }
                    }
                }
                break;
            }
        }
        return nArray2;
    }

    public String toString() {
        return "Effects/Smear...";
    }
}

