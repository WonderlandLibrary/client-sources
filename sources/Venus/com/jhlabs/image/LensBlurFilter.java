/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import com.jhlabs.image.ImageMath;
import com.jhlabs.math.FFT;
import java.awt.image.BufferedImage;

public class LensBlurFilter
extends AbstractBufferedImageOp {
    private float radius = 10.0f;
    private float bloom = 2.0f;
    private float bloomThreshold = 255.0f;
    private float angle = 0.0f;
    private int sides = 5;

    public void setRadius(float f) {
        this.radius = f;
    }

    public float getRadius() {
        return this.radius;
    }

    public void setSides(int n) {
        this.sides = n;
    }

    public int getSides() {
        return this.sides;
    }

    public void setBloom(float f) {
        this.bloom = f;
    }

    public float getBloom() {
        return this.bloom;
    }

    public void setBloomThreshold(float f) {
        this.bloomThreshold = f;
    }

    public float getBloomThreshold() {
        return this.bloomThreshold;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        int n;
        int n2;
        int n3;
        int n4 = bufferedImage.getWidth();
        int n5 = bufferedImage.getHeight();
        int n6 = 1;
        int n7 = 1;
        int n8 = 0;
        int n9 = 0;
        int n10 = (int)Math.ceil(this.radius);
        int n11 = n3 = 128;
        int n12 = n4 + n10 * 2;
        int n13 = n5 + n10 * 2;
        n3 = n10 < 32 ? Math.min(128, n4 + 2 * n10) : Math.min(256, n4 + 2 * n10);
        int n14 = n11 = n10 < 32 ? Math.min(128, n5 + 2 * n10) : Math.min(256, n5 + 2 * n10);
        if (bufferedImage2 == null) {
            bufferedImage2 = new BufferedImage(n4, n5, 2);
        }
        while (n6 < n11) {
            n6 *= 2;
            ++n8;
        }
        while (n7 < n3) {
            n7 *= 2;
            ++n9;
        }
        int n15 = n7;
        int n16 = n6;
        n3 = n15;
        n11 = n16;
        FFT fFT = new FFT(Math.max(n8, n9));
        int[] nArray = new int[n15 * n16];
        float[][] fArray = new float[2][n15 * n16];
        float[][] fArray2 = new float[2][n15 * n16];
        float[][] fArray3 = new float[2][n15 * n16];
        double d = Math.PI / (double)this.sides;
        double d2 = 1.0 / Math.cos(d);
        double d3 = this.radius * this.radius;
        double d4 = Math.toRadians(this.angle);
        float f = 0.0f;
        int n17 = 0;
        for (n2 = 0; n2 < n16; ++n2) {
            for (n = 0; n < n15; ++n) {
                double d5;
                double d6 = (float)n - (float)n15 / 2.0f;
                double d7 = (float)n2 - (float)n16 / 2.0f;
                double d8 = d6 * d6 + d7 * d7;
                double d9 = d5 = d8 < d3 ? 1.0 : 0.0;
                if (d5 != 0.0) {
                    d8 = Math.sqrt(d8);
                    if (this.sides != 0) {
                        double d10 = Math.atan2(d7, d6) + d4;
                        d10 = ImageMath.mod(d10, d * 2.0) - d;
                        d5 = Math.cos(d10) * d2;
                    } else {
                        d5 = 1.0;
                    }
                    d5 = d5 * d8 < (double)this.radius ? 1.0 : 0.0;
                }
                f += (float)d5;
                fArray[0][n17] = (float)d5;
                fArray[5][n17] = 0.0f;
                ++n17;
            }
        }
        n17 = 0;
        for (n2 = 0; n2 < n16; ++n2) {
            for (n = 0; n < n15; ++n) {
                float[] fArray4 = fArray[0];
                int n18 = n17++;
                fArray4[n18] = fArray4[n18] / f;
            }
        }
        fFT.transform2D(fArray[0], fArray[5], n15, n16, false);
        for (n2 = -n10; n2 < n5; n2 += n11 - 2 * n10) {
            for (n = -n10; n < n4; n += n3 - 2 * n10) {
                int n19;
                int n20;
                int n21;
                int n22 = n;
                int n23 = n2;
                int n24 = n3;
                int n25 = n11;
                int n26 = 0;
                int n27 = 0;
                if (n22 < 0) {
                    n24 += n22;
                    n26 -= n22;
                    n22 = 0;
                }
                if (n23 < 0) {
                    n25 += n23;
                    n27 -= n23;
                    n23 = 0;
                }
                if (n22 + n24 > n4) {
                    n24 = n4 - n22;
                }
                if (n23 + n25 > n5) {
                    n25 = n5 - n23;
                }
                bufferedImage.getRGB(n22, n23, n24, n25, nArray, n27 * n15 + n26, n15);
                n17 = 0;
                for (n21 = 0; n21 < n16; ++n21) {
                    n20 = n21 + n2;
                    int n28 = n20 < 0 ? n27 : (n20 > n5 ? n27 + n25 - 1 : n21);
                    n28 *= n15;
                    for (n19 = 0; n19 < n15; ++n19) {
                        int n29 = n19 + n;
                        int n30 = n29 < 0 ? n26 : (n29 > n4 ? n26 + n24 - 1 : n19);
                        fArray3[0][n17] = nArray[n30 += n28] >> 24 & 0xFF;
                        float f2 = nArray[n30] >> 16 & 0xFF;
                        float f3 = nArray[n30] >> 8 & 0xFF;
                        float f4 = nArray[n30] & 0xFF;
                        if (f2 > this.bloomThreshold) {
                            f2 *= this.bloom;
                        }
                        if (f3 > this.bloomThreshold) {
                            f3 *= this.bloom;
                        }
                        if (f4 > this.bloomThreshold) {
                            f4 *= this.bloom;
                        }
                        fArray3[5][n17] = f2;
                        fArray2[0][n17] = f3;
                        fArray2[5][n17] = f4;
                        ++n17;
                        ++n30;
                    }
                }
                fFT.transform2D(fArray3[0], fArray3[5], n7, n6, false);
                fFT.transform2D(fArray2[0], fArray2[5], n7, n6, false);
                n17 = 0;
                for (n21 = 0; n21 < n16; ++n21) {
                    for (n20 = 0; n20 < n15; ++n20) {
                        float f5 = fArray3[0][n17];
                        float f6 = fArray3[5][n17];
                        float f7 = fArray[0][n17];
                        float f8 = fArray[5][n17];
                        fArray3[0][n17] = f5 * f7 - f6 * f8;
                        fArray3[5][n17] = f5 * f8 + f6 * f7;
                        f5 = fArray2[0][n17];
                        f6 = fArray2[5][n17];
                        fArray2[0][n17] = f5 * f7 - f6 * f8;
                        fArray2[5][n17] = f5 * f8 + f6 * f7;
                        ++n17;
                    }
                }
                fFT.transform2D(fArray3[0], fArray3[5], n7, n6, true);
                fFT.transform2D(fArray2[0], fArray2[5], n7, n6, true);
                n21 = n15 >> 1;
                n20 = n16 >> 1;
                int n31 = 0;
                for (n19 = 0; n19 < n15; ++n19) {
                    int n32 = n19 ^ n21;
                    int n33 = n32 * n7;
                    for (int i = 0; i < n15; ++i) {
                        int n34 = n33 + (i ^ n20);
                        int n35 = (int)fArray3[0][n34];
                        int n36 = (int)fArray3[5][n34];
                        int n37 = (int)fArray2[0][n34];
                        int n38 = (int)fArray2[5][n34];
                        if (n36 > 255) {
                            n36 = 255;
                        }
                        if (n37 > 255) {
                            n37 = 255;
                        }
                        if (n38 > 255) {
                            n38 = 255;
                        }
                        int n39 = n35 << 24 | n36 << 16 | n37 << 8 | n38;
                        nArray[n31++] = n39;
                    }
                }
                n22 = n + n10;
                n23 = n2 + n10;
                n24 = n3 - 2 * n10;
                n25 = n11 - 2 * n10;
                if (n22 + n24 > n4) {
                    n24 = n4 - n22;
                }
                if (n23 + n25 > n5) {
                    n25 = n5 - n23;
                }
                bufferedImage2.setRGB(n22, n23, n24, n25, nArray, n10 * n15 + n10, n15);
            }
        }
        return bufferedImage2;
    }

    public String toString() {
        return "Blur/Lens Blur...";
    }
}

