/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.WholeImageFilter;
import com.jhlabs.math.Noise;
import java.awt.Rectangle;
import java.util.Random;

public class CausticsFilter
extends WholeImageFilter {
    private float scale = 32.0f;
    private float angle = 0.0f;
    private int brightness = 10;
    private float amount = 1.0f;
    private float turbulence = 1.0f;
    private float dispersion = 0.0f;
    private float time = 0.0f;
    private int samples = 2;
    private int bgColor = -8806401;
    private float s;
    private float c;

    public void setScale(float f) {
        this.scale = f;
    }

    public float getScale() {
        return this.scale;
    }

    public void setBrightness(int n) {
        this.brightness = n;
    }

    public int getBrightness() {
        return this.brightness;
    }

    public void setTurbulence(float f) {
        this.turbulence = f;
    }

    public float getTurbulence() {
        return this.turbulence;
    }

    public void setAmount(float f) {
        this.amount = f;
    }

    public float getAmount() {
        return this.amount;
    }

    public void setDispersion(float f) {
        this.dispersion = f;
    }

    public float getDispersion() {
        return this.dispersion;
    }

    public void setTime(float f) {
        this.time = f;
    }

    public float getTime() {
        return this.time;
    }

    public void setSamples(int n) {
        this.samples = n;
    }

    public int getSamples() {
        return this.samples;
    }

    public void setBgColor(int n) {
        this.bgColor = n;
    }

    public int getBgColor() {
        return this.bgColor;
    }

    @Override
    protected int[] filterPixels(int n, int n2, int[] nArray, Rectangle rectangle) {
        int n3;
        Random random2 = new Random(0L);
        this.s = (float)Math.sin(0.1);
        this.c = (float)Math.cos(0.1);
        int n4 = this.originalSpace.width;
        int n5 = this.originalSpace.height;
        int n6 = rectangle.width;
        int n7 = rectangle.height;
        int n8 = 0;
        int[] nArray2 = new int[n6 * n7];
        for (n3 = 0; n3 < n7; ++n3) {
            for (int i = 0; i < n6; ++i) {
                nArray2[n8++] = this.bgColor;
            }
        }
        n3 = this.brightness / this.samples;
        if (n3 == 0) {
            n3 = 1;
        }
        float f = 1.0f / this.scale;
        float f2 = 0.95f;
        n8 = 0;
        for (int i = 0; i < n7; ++i) {
            for (int j = 0; j < n6; ++j) {
                for (int k = 0; k < this.samples; ++k) {
                    int n9;
                    int n10;
                    int n11;
                    float f3;
                    float f4 = (float)j + random2.nextFloat();
                    float f5 = (float)i + random2.nextFloat();
                    float f6 = f4 * f;
                    float f7 = f5 * f;
                    float f8 = 0.1f + this.amount;
                    float f9 = this.evaluate(f6 - f2, f7) - this.evaluate(f6 + f2, f7);
                    float f10 = this.evaluate(f6, f7 + f2) - this.evaluate(f6, f7 - f2);
                    if (this.dispersion > 0.0f) {
                        for (int i2 = 0; i2 < 3; ++i2) {
                            f3 = 1.0f + (float)i2 * this.dispersion;
                            float f11 = f4 + this.scale * f8 * f9 * f3;
                            float f12 = f5 + this.scale * f8 * f10 * f3;
                            if (f11 < 0.0f || f11 >= (float)(n6 - 1) || f12 < 0.0f || f12 >= (float)(n7 - 1)) continue;
                            n11 = (int)f12 * n6 + (int)f11;
                            n10 = nArray2[n11];
                            n9 = n10 >> 16 & 0xFF;
                            int n12 = n10 >> 8 & 0xFF;
                            int n13 = n10 & 0xFF;
                            if (i2 == 2) {
                                n9 += n3;
                            } else if (i2 == 1) {
                                n12 += n3;
                            } else {
                                n13 += n3;
                            }
                            if (n9 > 255) {
                                n9 = 255;
                            }
                            if (n12 > 255) {
                                n12 = 255;
                            }
                            if (n13 > 255) {
                                n13 = 255;
                            }
                            nArray2[n11] = 0xFF000000 | n9 << 16 | n12 << 8 | n13;
                        }
                        continue;
                    }
                    float f13 = f4 + this.scale * f8 * f9;
                    f3 = f5 + this.scale * f8 * f10;
                    if (f13 < 0.0f || f13 >= (float)(n6 - 1) || f3 < 0.0f || f3 >= (float)(n7 - 1)) continue;
                    int n14 = (int)f3 * n6 + (int)f13;
                    int n15 = nArray2[n14];
                    n11 = n15 >> 16 & 0xFF;
                    n10 = n15 >> 8 & 0xFF;
                    n9 = n15 & 0xFF;
                    n10 += n3;
                    n9 += n3;
                    if ((n11 += n3) > 255) {
                        n11 = 255;
                    }
                    if (n10 > 255) {
                        n10 = 255;
                    }
                    if (n9 > 255) {
                        n9 = 255;
                    }
                    nArray2[n14] = 0xFF000000 | n11 << 16 | n10 << 8 | n9;
                }
            }
        }
        return nArray2;
    }

    private static int add(int n, float f) {
        int n2 = n >> 16 & 0xFF;
        int n3 = n >> 8 & 0xFF;
        int n4 = n & 0xFF;
        n2 = (int)((float)n2 + f);
        n3 = (int)((float)n3 + f);
        n4 = (int)((float)n4 + f);
        if (n2 > 255) {
            n2 = 255;
        }
        if (n3 > 255) {
            n3 = 255;
        }
        if (n4 > 255) {
            n4 = 255;
        }
        return 0xFF000000 | n2 << 16 | n3 << 8 | n4;
    }

    private static int add(int n, float f, int n2) {
        int n3 = n >> 16 & 0xFF;
        int n4 = n >> 8 & 0xFF;
        int n5 = n & 0xFF;
        if (n2 == 2) {
            n3 = (int)((float)n3 + f);
        } else if (n2 == 1) {
            n4 = (int)((float)n4 + f);
        } else {
            n5 = (int)((float)n5 + f);
        }
        if (n3 > 255) {
            n3 = 255;
        }
        if (n4 > 255) {
            n4 = 255;
        }
        if (n5 > 255) {
            n5 = 255;
        }
        return 0xFF000000 | n3 << 16 | n4 << 8 | n5;
    }

    private static float turbulence2(float f, float f2, float f3, float f4) {
        float f5 = 0.0f;
        float f6 = 2.0f;
        float f7 = 1.0f;
        f += 371.0f;
        f2 += 529.0f;
        for (int i = 0; i < (int)f4; ++i) {
            f5 += Noise.noise3(f, f2, f3) / f7;
            f *= f6;
            f2 *= f6;
            f7 *= 2.0f;
        }
        float f8 = f4 - (float)((int)f4);
        if (f8 != 0.0f) {
            f5 += f8 * Noise.noise3(f, f2, f3) / f7;
        }
        return f5;
    }

    private float evaluate(float f, float f2) {
        float f3 = this.s * f + this.c * this.time;
        float f4 = this.c * f - this.c * this.time;
        float f5 = (double)this.turbulence == 0.0 ? Noise.noise3(f3, f2, f4) : CausticsFilter.turbulence2(f3, f2, f4, this.turbulence);
        return f5;
    }

    public String toString() {
        return "Texture/Caustics...";
    }
}

