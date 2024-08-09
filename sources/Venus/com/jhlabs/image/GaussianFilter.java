/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.ConvolveFilter;
import com.jhlabs.image.PixelUtils;
import java.awt.image.BufferedImage;
import java.awt.image.Kernel;

public class GaussianFilter
extends ConvolveFilter {
    protected float radius;
    protected Kernel kernel;

    public GaussianFilter() {
        this(2.0f);
    }

    public GaussianFilter(float f) {
        this.setRadius(f);
    }

    public void setRadius(float f) {
        this.radius = f;
        this.kernel = GaussianFilter.makeKernel(f);
    }

    public float getRadius() {
        return this.radius;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        int n = bufferedImage.getWidth();
        int n2 = bufferedImage.getHeight();
        if (bufferedImage2 == null) {
            bufferedImage2 = this.createCompatibleDestImage(bufferedImage, null);
        }
        int[] nArray = new int[n * n2];
        int[] nArray2 = new int[n * n2];
        bufferedImage.getRGB(0, 0, n, n2, nArray, 0, n);
        if (this.radius > 0.0f) {
            GaussianFilter.convolveAndTranspose(this.kernel, nArray, nArray2, n, n2, this.alpha, this.alpha && this.premultiplyAlpha, false, CLAMP_EDGES);
            GaussianFilter.convolveAndTranspose(this.kernel, nArray2, nArray, n2, n, this.alpha, false, this.alpha && this.premultiplyAlpha, CLAMP_EDGES);
        }
        bufferedImage2.setRGB(0, 0, n, n2, nArray, 0, n);
        return bufferedImage2;
    }

    public static void convolveAndTranspose(Kernel kernel, int[] nArray, int[] nArray2, int n, int n2, boolean bl, boolean bl2, boolean bl3, int n3) {
        float[] fArray = kernel.getKernelData(null);
        int n4 = kernel.getWidth();
        int n5 = n4 / 2;
        for (int i = 0; i < n2; ++i) {
            int n6 = i;
            int n7 = i * n;
            for (int j = 0; j < n; ++j) {
                int n8;
                int n9;
                int n10;
                float f = 0.0f;
                float f2 = 0.0f;
                float f3 = 0.0f;
                float f4 = 0.0f;
                int n11 = n5;
                for (n10 = -n5; n10 <= n5; ++n10) {
                    float f5 = fArray[n11 + n10];
                    if (f5 == 0.0f) continue;
                    n9 = j + n10;
                    if (n9 < 0) {
                        if (n3 == CLAMP_EDGES) {
                            n9 = 0;
                        } else if (n3 == WRAP_EDGES) {
                            n9 = (j + n) % n;
                        }
                    } else if (n9 >= n) {
                        if (n3 == CLAMP_EDGES) {
                            n9 = n - 1;
                        } else if (n3 == WRAP_EDGES) {
                            n9 = (j + n) % n;
                        }
                    }
                    n8 = nArray[n7 + n9];
                    int n12 = n8 >> 24 & 0xFF;
                    int n13 = n8 >> 16 & 0xFF;
                    int n14 = n8 >> 8 & 0xFF;
                    int n15 = n8 & 0xFF;
                    if (bl2) {
                        float f6 = (float)n12 * 0.003921569f;
                        n13 = (int)((float)n13 * f6);
                        n14 = (int)((float)n14 * f6);
                        n15 = (int)((float)n15 * f6);
                    }
                    f4 += f5 * (float)n12;
                    f += f5 * (float)n13;
                    f2 += f5 * (float)n14;
                    f3 += f5 * (float)n15;
                }
                if (bl3 && f4 != 0.0f && f4 != 255.0f) {
                    float f7 = 255.0f / f4;
                    f *= f7;
                    f2 *= f7;
                    f3 *= f7;
                }
                n10 = bl ? PixelUtils.clamp((int)((double)f4 + 0.5)) : 255;
                int n16 = PixelUtils.clamp((int)((double)f + 0.5));
                n9 = PixelUtils.clamp((int)((double)f2 + 0.5));
                n8 = PixelUtils.clamp((int)((double)f3 + 0.5));
                nArray2[n6] = n10 << 24 | n16 << 16 | n9 << 8 | n8;
                n6 += n2;
            }
        }
    }

    public static Kernel makeKernel(float f) {
        int n;
        int n2 = (int)Math.ceil(f);
        int n3 = n2 * 2 + 1;
        float[] fArray = new float[n3];
        float f2 = f / 3.0f;
        float f3 = 2.0f * f2 * f2;
        float f4 = (float)Math.PI * 2 * f2;
        float f5 = (float)Math.sqrt(f4);
        float f6 = f * f;
        float f7 = 0.0f;
        int n4 = 0;
        for (n = -n2; n <= n2; ++n) {
            float f8 = n * n;
            fArray[n4] = f8 > f6 ? 0.0f : (float)Math.exp(-f8 / f3) / f5;
            f7 += fArray[n4];
            ++n4;
        }
        n = 0;
        while (n < n3) {
            int n5 = n++;
            fArray[n5] = fArray[n5] / f7;
        }
        return new Kernel(n3, 1, fArray);
    }

    @Override
    public String toString() {
        return "Blur/Gaussian Blur...";
    }
}

