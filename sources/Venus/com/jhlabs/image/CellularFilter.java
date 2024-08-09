/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.Colormap;
import com.jhlabs.image.Gradient;
import com.jhlabs.image.ImageMath;
import com.jhlabs.image.PixelUtils;
import com.jhlabs.image.WholeImageFilter;
import com.jhlabs.math.Function2D;
import com.jhlabs.math.Noise;
import java.awt.Rectangle;
import java.util.Random;

public class CellularFilter
extends WholeImageFilter
implements Function2D,
Cloneable {
    protected float scale = 32.0f;
    protected float stretch = 1.0f;
    protected float angle = 0.0f;
    public float amount = 1.0f;
    public float turbulence = 1.0f;
    public float gain = 0.5f;
    public float bias = 0.5f;
    public float distancePower = 2.0f;
    public boolean useColor = false;
    protected Colormap colormap = new Gradient();
    protected float[] coefficients = new float[]{1.0f, 0.0f, 0.0f, 0.0f};
    protected float angleCoefficient;
    protected Random random = new Random();
    protected float m00 = 1.0f;
    protected float m01 = 0.0f;
    protected float m10 = 0.0f;
    protected float m11 = 1.0f;
    protected Point[] results = new Point[3];
    protected float randomness = 0.0f;
    protected int gridType = 2;
    private float min;
    private float max;
    private static byte[] probabilities;
    private float gradientCoefficient;
    public static final int RANDOM = 0;
    public static final int SQUARE = 1;
    public static final int HEXAGONAL = 2;
    public static final int OCTAGONAL = 3;
    public static final int TRIANGULAR = 4;

    public CellularFilter() {
        for (int i = 0; i < this.results.length; ++i) {
            this.results[i] = new Point(this);
        }
        if (probabilities == null) {
            probabilities = new byte[8192];
            float f = 1.0f;
            float f2 = 0.0f;
            float f3 = 2.5f;
            for (int i = 0; i < 10; ++i) {
                if (i > 1) {
                    f *= (float)i;
                }
                float f4 = (float)Math.pow(f3, i) * (float)Math.exp(-f3) / f;
                int n = (int)(f2 * 8192.0f);
                int n2 = (int)((f2 += f4) * 8192.0f);
                for (int j = n; j < n2; ++j) {
                    CellularFilter.probabilities[j] = (byte)i;
                }
            }
        }
    }

    public void setScale(float f) {
        this.scale = f;
    }

    public float getScale() {
        return this.scale;
    }

    public void setStretch(float f) {
        this.stretch = f;
    }

    public float getStretch() {
        return this.stretch;
    }

    public void setAngle(float f) {
        this.angle = f;
        float f2 = (float)Math.cos(f);
        float f3 = (float)Math.sin(f);
        this.m00 = f2;
        this.m01 = f3;
        this.m10 = -f3;
        this.m11 = f2;
    }

    public float getAngle() {
        return this.angle;
    }

    public void setCoefficient(int n, float f) {
        this.coefficients[n] = f;
    }

    public float getCoefficient(int n) {
        return this.coefficients[n];
    }

    public void setAngleCoefficient(float f) {
        this.angleCoefficient = f;
    }

    public float getAngleCoefficient() {
        return this.angleCoefficient;
    }

    public void setGradientCoefficient(float f) {
        this.gradientCoefficient = f;
    }

    public float getGradientCoefficient() {
        return this.gradientCoefficient;
    }

    public void setF1(float f) {
        this.coefficients[0] = f;
    }

    public float getF1() {
        return this.coefficients[0];
    }

    public void setF2(float f) {
        this.coefficients[1] = f;
    }

    public float getF2() {
        return this.coefficients[1];
    }

    public void setF3(float f) {
        this.coefficients[2] = f;
    }

    public float getF3() {
        return this.coefficients[2];
    }

    public void setF4(float f) {
        this.coefficients[3] = f;
    }

    public float getF4() {
        return this.coefficients[3];
    }

    public void setColormap(Colormap colormap) {
        this.colormap = colormap;
    }

    public Colormap getColormap() {
        return this.colormap;
    }

    public void setRandomness(float f) {
        this.randomness = f;
    }

    public float getRandomness() {
        return this.randomness;
    }

    public void setGridType(int n) {
        this.gridType = n;
    }

    public int getGridType() {
        return this.gridType;
    }

    public void setDistancePower(float f) {
        this.distancePower = f;
    }

    public float getDistancePower() {
        return this.distancePower;
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

    private float checkCube(float f, float f2, int n, int n2, Point[] pointArray) {
        this.random.setSeed(571 * n + 23 * n2);
        for (int i = 0; i < (switch (this.gridType) {
            default -> probabilities[this.random.nextInt() & 0x1FFF];
            case 1 -> 1;
            case 2 -> 1;
            case 3 -> 2;
            case 4 -> 2;
        }); ++i) {
            Point point;
            float f3 = 0.0f;
            float f4 = 0.0f;
            float f5 = 1.0f;
            switch (this.gridType) {
                case 0: {
                    f3 = this.random.nextFloat();
                    f4 = this.random.nextFloat();
                    break;
                }
                case 1: {
                    f4 = 0.5f;
                    f3 = 0.5f;
                    if (this.randomness == 0.0f) break;
                    f3 = (float)((double)f3 + (double)this.randomness * ((double)this.random.nextFloat() - 0.5));
                    f4 = (float)((double)f4 + (double)this.randomness * ((double)this.random.nextFloat() - 0.5));
                    break;
                }
                case 2: {
                    if ((n & 1) == 0) {
                        f3 = 0.75f;
                        f4 = 0.0f;
                    } else {
                        f3 = 0.75f;
                        f4 = 0.5f;
                    }
                    if (this.randomness == 0.0f) break;
                    f3 += this.randomness * Noise.noise2(271.0f * ((float)n + f3), 271.0f * ((float)n2 + f4));
                    f4 += this.randomness * Noise.noise2(271.0f * ((float)n + f3) + 89.0f, 271.0f * ((float)n2 + f4) + 137.0f);
                    break;
                }
                case 3: {
                    switch (i) {
                        case 0: {
                            f3 = 0.207f;
                            f4 = 0.207f;
                            break;
                        }
                        case 1: {
                            f3 = 0.707f;
                            f4 = 0.707f;
                            f5 = 1.6f;
                        }
                    }
                    if (this.randomness == 0.0f) break;
                    f3 += this.randomness * Noise.noise2(271.0f * ((float)n + f3), 271.0f * ((float)n2 + f4));
                    f4 += this.randomness * Noise.noise2(271.0f * ((float)n + f3) + 89.0f, 271.0f * ((float)n2 + f4) + 137.0f);
                    break;
                }
                case 4: {
                    if ((n2 & 1) == 0) {
                        if (i == 0) {
                            f3 = 0.25f;
                            f4 = 0.35f;
                        } else {
                            f3 = 0.75f;
                            f4 = 0.65f;
                        }
                    } else if (i == 0) {
                        f3 = 0.75f;
                        f4 = 0.35f;
                    } else {
                        f3 = 0.25f;
                        f4 = 0.65f;
                    }
                    if (this.randomness == 0.0f) break;
                    f3 += this.randomness * Noise.noise2(271.0f * ((float)n + f3), 271.0f * ((float)n2 + f4));
                    f4 += this.randomness * Noise.noise2(271.0f * ((float)n + f3) + 89.0f, 271.0f * ((float)n2 + f4) + 137.0f);
                }
            }
            float f6 = Math.abs(f - f3);
            float f7 = Math.abs(f2 - f4);
            float f8 = this.distancePower == 1.0f ? f6 + f7 : (this.distancePower == 2.0f ? (float)Math.sqrt(f6 * f6 + f7 * f7) : (float)Math.pow((float)Math.pow(f6 *= f5, this.distancePower) + (float)Math.pow(f7 *= f5, this.distancePower), 1.0f / this.distancePower));
            if (f8 < pointArray[0].distance) {
                point = pointArray[5];
                pointArray[2] = pointArray[5];
                pointArray[1] = pointArray[0];
                pointArray[0] = point;
                point.distance = f8;
                point.dx = f6;
                point.dy = f7;
                point.x = (float)n + f3;
                point.y = (float)n2 + f4;
                continue;
            }
            if (f8 < pointArray[5].distance) {
                point = pointArray[5];
                pointArray[2] = pointArray[5];
                pointArray[1] = point;
                point.distance = f8;
                point.dx = f6;
                point.dy = f7;
                point.x = (float)n + f3;
                point.y = (float)n2 + f4;
                continue;
            }
            if (!(f8 < pointArray[5].distance)) continue;
            point = pointArray[5];
            point.distance = f8;
            point.dx = f6;
            point.dy = f7;
            point.x = (float)n + f3;
            point.y = (float)n2 + f4;
        }
        return pointArray[5].distance;
    }

    @Override
    public float evaluate(float f, float f2) {
        int n;
        for (n = 0; n < this.results.length; ++n) {
            this.results[n].distance = Float.POSITIVE_INFINITY;
        }
        n = (int)f;
        float f3 = f - (float)n;
        int n2 = (int)f2;
        float f4 = f2 - (float)n2;
        float f5 = this.checkCube(f3, f4, n, n2, this.results);
        if (f5 > f4) {
            f5 = this.checkCube(f3, f4 + 1.0f, n, n2 - 1, this.results);
        }
        if (f5 > 1.0f - f4) {
            f5 = this.checkCube(f3, f4 - 1.0f, n, n2 + 1, this.results);
        }
        if (f5 > f3) {
            this.checkCube(f3 + 1.0f, f4, n - 1, n2, this.results);
            if (f5 > f4) {
                f5 = this.checkCube(f3 + 1.0f, f4 + 1.0f, n - 1, n2 - 1, this.results);
            }
            if (f5 > 1.0f - f4) {
                f5 = this.checkCube(f3 + 1.0f, f4 - 1.0f, n - 1, n2 + 1, this.results);
            }
        }
        if (f5 > 1.0f - f3) {
            f5 = this.checkCube(f3 - 1.0f, f4, n + 1, n2, this.results);
            if (f5 > f4) {
                f5 = this.checkCube(f3 - 1.0f, f4 + 1.0f, n + 1, n2 - 1, this.results);
            }
            if (f5 > 1.0f - f4) {
                f5 = this.checkCube(f3 - 1.0f, f4 - 1.0f, n + 1, n2 + 1, this.results);
            }
        }
        float f6 = 0.0f;
        for (int i = 0; i < 3; ++i) {
            f6 += this.coefficients[i] * this.results[i].distance;
        }
        if (this.angleCoefficient != 0.0f) {
            float f7 = (float)Math.atan2(f2 - this.results[0].y, f - this.results[0].x);
            if (f7 < 0.0f) {
                f7 += (float)Math.PI * 2;
            }
            f6 += this.angleCoefficient * (f7 /= (float)Math.PI * 4);
        }
        if (this.gradientCoefficient != 0.0f) {
            float f8 = 1.0f / (this.results[0].dy + this.results[0].dx);
            f6 += this.gradientCoefficient * f8;
        }
        return f6;
    }

    public float turbulence2(float f, float f2, float f3) {
        float f4 = 0.0f;
        for (float f5 = 1.0f; f5 <= f3; f5 *= 2.0f) {
            f4 += this.evaluate(f5 * f, f5 * f2) / f5;
        }
        return f4;
    }

    public int getPixel(int n, int n2, int[] nArray, int n3, int n4) {
        float f = this.m00 * (float)n + this.m01 * (float)n2;
        float f2 = this.m10 * (float)n + this.m11 * (float)n2;
        f /= this.scale;
        f2 /= this.scale * this.stretch;
        float f3 = this.turbulence == 1.0f ? this.evaluate(f, f2) : this.turbulence2(f += 1000.0f, f2 += 1000.0f, this.turbulence);
        f3 *= 2.0f;
        f3 *= this.amount;
        int n5 = -16777216;
        if (this.colormap != null) {
            int n6 = this.colormap.getColor(f3);
            if (this.useColor) {
                int n7 = ImageMath.clamp((int)((this.results[0].x - 1000.0f) * this.scale), 0, n3 - 1);
                int n8 = ImageMath.clamp((int)((this.results[0].y - 1000.0f) * this.scale), 0, n4 - 1);
                n6 = nArray[n8 * n3 + n7];
                f3 = (this.results[1].distance - this.results[0].distance) / (this.results[1].distance + this.results[0].distance);
                f3 = ImageMath.smoothStep(this.coefficients[1], this.coefficients[0], f3);
                n6 = ImageMath.mixColors(f3, -16777216, n6);
            }
            return n6;
        }
        int n9 = PixelUtils.clamp((int)(f3 * 255.0f));
        int n10 = n9 << 16;
        int n11 = n9 << 8;
        int n12 = n9;
        return n5 | n10 | n11 | n12;
    }

    @Override
    protected int[] filterPixels(int n, int n2, int[] nArray, Rectangle rectangle) {
        int n3 = 0;
        int[] nArray2 = new int[n * n2];
        for (int i = 0; i < n2; ++i) {
            for (int j = 0; j < n; ++j) {
                nArray2[n3++] = this.getPixel(j, i, nArray, n, n2);
            }
        }
        return nArray2;
    }

    @Override
    public Object clone() {
        CellularFilter cellularFilter = (CellularFilter)super.clone();
        cellularFilter.coefficients = (float[])this.coefficients.clone();
        cellularFilter.results = (Point[])this.results.clone();
        cellularFilter.random = new Random();
        return cellularFilter;
    }

    public String toString() {
        return "Texture/Cellular...";
    }

    public class Point {
        public int index;
        public float x;
        public float y;
        public float dx;
        public float dy;
        public float cubeX;
        public float cubeY;
        public float distance;
        final CellularFilter this$0;

        public Point(CellularFilter cellularFilter) {
            this.this$0 = cellularFilter;
        }
    }
}

