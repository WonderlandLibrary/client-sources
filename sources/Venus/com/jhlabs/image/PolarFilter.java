/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.TransformFilter;
import java.awt.image.BufferedImage;

public class PolarFilter
extends TransformFilter {
    public static final int RECT_TO_POLAR = 0;
    public static final int POLAR_TO_RECT = 1;
    public static final int INVERT_IN_CIRCLE = 2;
    private int type;
    private float width;
    private float height;
    private float centreX;
    private float centreY;
    private float radius;

    public PolarFilter() {
        this(0);
    }

    public PolarFilter(int n) {
        this.type = n;
        this.setEdgeAction(1);
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        this.width = bufferedImage.getWidth();
        this.height = bufferedImage.getHeight();
        this.centreX = this.width / 2.0f;
        this.centreY = this.height / 2.0f;
        this.radius = Math.max(this.centreY, this.centreX);
        return super.filter(bufferedImage, bufferedImage2);
    }

    public void setType(int n) {
        this.type = n;
    }

    public int getType() {
        return this.type;
    }

    private float sqr(float f) {
        return f * f;
    }

    @Override
    protected void transformInverse(int n, int n2, float[] fArray) {
        float f = 0.0f;
        switch (this.type) {
            case 0: {
                float f2 = 0.0f;
                if ((float)n >= this.centreX) {
                    if ((float)n2 > this.centreY) {
                        f2 = (float)Math.PI - (float)Math.atan(((float)n - this.centreX) / ((float)n2 - this.centreY));
                        f = (float)Math.sqrt(this.sqr((float)n - this.centreX) + this.sqr((float)n2 - this.centreY));
                    } else if ((float)n2 < this.centreY) {
                        f2 = (float)Math.atan(((float)n - this.centreX) / (this.centreY - (float)n2));
                        f = (float)Math.sqrt(this.sqr((float)n - this.centreX) + this.sqr(this.centreY - (float)n2));
                    } else {
                        f2 = 1.5707964f;
                        f = (float)n - this.centreX;
                    }
                } else if ((float)n < this.centreX) {
                    if ((float)n2 < this.centreY) {
                        f2 = (float)Math.PI * 2 - (float)Math.atan((this.centreX - (float)n) / (this.centreY - (float)n2));
                        f = (float)Math.sqrt(this.sqr(this.centreX - (float)n) + this.sqr(this.centreY - (float)n2));
                    } else if ((float)n2 > this.centreY) {
                        f2 = (float)Math.PI + (float)Math.atan((this.centreX - (float)n) / ((float)n2 - this.centreY));
                        f = (float)Math.sqrt(this.sqr(this.centreX - (float)n) + this.sqr((float)n2 - this.centreY));
                    } else {
                        f2 = 4.712389f;
                        f = this.centreX - (float)n;
                    }
                }
                float f3 = (float)n != this.centreX ? Math.abs(((float)n2 - this.centreY) / ((float)n - this.centreX)) : 0.0f;
                if (f3 <= this.height / this.width) {
                    if ((float)n == this.centreX) {
                        float f4 = 0.0f;
                        float f5 = this.centreY;
                    } else {
                        float f6 = this.centreX;
                        float f7 = f3 * f6;
                    }
                } else {
                    float f8 = this.centreY;
                    float f9 = f8 / f3;
                }
                fArray[0] = this.width - 1.0f - (this.width - 1.0f) / ((float)Math.PI * 2) * f2;
                fArray[1] = this.height * f / this.radius;
                break;
            }
            case 1: {
                float f10 = (float)n / this.width * ((float)Math.PI * 2);
                float f11 = f10 >= 4.712389f ? (float)Math.PI * 2 - f10 : (f10 >= (float)Math.PI ? f10 - (float)Math.PI : (f10 >= 1.5707964f ? (float)Math.PI - f10 : f10));
                float f12 = (float)Math.tan(f11);
                float f13 = f12 != 0.0f ? 1.0f / f12 : 0.0f;
                if (f13 <= this.height / this.width) {
                    if (f11 == 0.0f) {
                        float f14 = 0.0f;
                        float f15 = this.centreY;
                    } else {
                        float f16 = this.centreX;
                        float f17 = f13 * f16;
                    }
                } else {
                    float f18 = this.centreY;
                    float f19 = f18 / f13;
                }
                f = this.radius * ((float)n2 / this.height);
                float f20 = -f * (float)Math.sin(f11);
                float f21 = f * (float)Math.cos(f11);
                if (f10 >= 4.712389f) {
                    fArray[0] = this.centreX - f20;
                    fArray[1] = this.centreY - f21;
                    break;
                }
                if ((double)f10 >= Math.PI) {
                    fArray[0] = this.centreX - f20;
                    fArray[1] = this.centreY + f21;
                    break;
                }
                if ((double)f10 >= 1.5707963267948966) {
                    fArray[0] = this.centreX + f20;
                    fArray[1] = this.centreY + f21;
                    break;
                }
                fArray[0] = this.centreX + f20;
                fArray[1] = this.centreY - f21;
                break;
            }
            case 2: {
                float f22 = (float)n - this.centreX;
                float f23 = (float)n2 - this.centreY;
                float f24 = f22 * f22 + f23 * f23;
                fArray[0] = this.centreX + this.centreX * this.centreX * f22 / f24;
                fArray[1] = this.centreY + this.centreY * this.centreY * f23 / f24;
            }
        }
    }

    public String toString() {
        return "Distort/Polar Coordinates...";
    }
}

