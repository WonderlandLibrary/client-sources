/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.ConvolveFilter;
import com.jhlabs.image.GaussianFilter;
import com.jhlabs.image.ImageMath;
import com.jhlabs.image.WholeImageFilter;
import com.jhlabs.math.Function2D;
import com.jhlabs.math.ImageFunction2D;
import com.jhlabs.vecmath.Color4f;
import com.jhlabs.vecmath.Tuple3f;
import com.jhlabs.vecmath.Vector3f;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.Kernel;

public class ShadeFilter
extends WholeImageFilter {
    public static final int COLORS_FROM_IMAGE = 0;
    public static final int COLORS_CONSTANT = 1;
    public static final int BUMPS_FROM_IMAGE = 0;
    public static final int BUMPS_FROM_IMAGE_ALPHA = 1;
    public static final int BUMPS_FROM_MAP = 2;
    public static final int BUMPS_FROM_BEVEL = 3;
    private float bumpHeight = 1.0f;
    private float bumpSoftness = 5.0f;
    private float viewDistance = 10000.0f;
    private int colorSource = 0;
    private int bumpSource = 0;
    private Function2D bumpFunction;
    private BufferedImage environmentMap;
    private int[] envPixels;
    private int envWidth = 1;
    private int envHeight = 1;
    private Vector3f l = new Vector3f();
    private Vector3f v = new Vector3f();
    private Vector3f n = new Vector3f();
    private Color4f shadedColor = new Color4f();
    private Color4f diffuse_color = new Color4f();
    private Color4f specular_color = new Color4f();
    private Vector3f tmpv = new Vector3f();
    private Vector3f tmpv2 = new Vector3f();
    protected static final float r255 = 0.003921569f;

    public void setBumpFunction(Function2D function2D) {
        this.bumpFunction = function2D;
    }

    public Function2D getBumpFunction() {
        return this.bumpFunction;
    }

    public void setBumpHeight(float f) {
        this.bumpHeight = f;
    }

    public float getBumpHeight() {
        return this.bumpHeight;
    }

    public void setBumpSoftness(float f) {
        this.bumpSoftness = f;
    }

    public float getBumpSoftness() {
        return this.bumpSoftness;
    }

    public void setEnvironmentMap(BufferedImage bufferedImage) {
        this.environmentMap = bufferedImage;
        if (bufferedImage != null) {
            this.envWidth = bufferedImage.getWidth();
            this.envHeight = bufferedImage.getHeight();
            this.envPixels = this.getRGB(bufferedImage, 0, 0, this.envWidth, this.envHeight, null);
        } else {
            this.envHeight = 1;
            this.envWidth = 1;
            this.envPixels = null;
        }
    }

    public BufferedImage getEnvironmentMap() {
        return this.environmentMap;
    }

    public void setBumpSource(int n) {
        this.bumpSource = n;
    }

    public int getBumpSource() {
        return this.bumpSource;
    }

    protected void setFromRGB(Color4f color4f, int n) {
        color4f.set((float)(n >> 16 & 0xFF) * 0.003921569f, (float)(n >> 8 & 0xFF) * 0.003921569f, (float)(n & 0xFF) * 0.003921569f, (float)(n >> 24 & 0xFF) * 0.003921569f);
    }

    @Override
    protected int[] filterPixels(int n, int n2, int[] nArray, Rectangle rectangle) {
        Object object;
        int n3 = 0;
        int[] nArray2 = new int[n * n2];
        float f = Math.abs(6.0f * this.bumpHeight);
        boolean bl = this.bumpHeight < 0.0f;
        Vector3f vector3f = new Vector3f(0.0f, 0.0f, 0.0f);
        Vector3f vector3f2 = new Vector3f((float)n / 2.0f, (float)n2 / 2.0f, this.viewDistance);
        Vector3f vector3f3 = new Vector3f();
        Color4f color4f = new Color4f();
        Function2D function2D = this.bumpFunction;
        if (this.bumpSource == 0 || this.bumpSource == 1 || this.bumpSource == 2 || function2D == null) {
            if (this.bumpSoftness != 0.0f) {
                Object object2;
                int n4 = n;
                int n5 = n2;
                object = nArray;
                if (this.bumpSource == 2 && this.bumpFunction instanceof ImageFunction2D) {
                    object2 = (ImageFunction2D)this.bumpFunction;
                    n4 = ((ImageFunction2D)object2).getWidth();
                    n5 = ((ImageFunction2D)object2).getHeight();
                    object = ((ImageFunction2D)object2).getPixels();
                }
                object2 = GaussianFilter.makeKernel(this.bumpSoftness);
                int[] nArray3 = new int[n4 * n5];
                int[] nArray4 = new int[n4 * n5];
                GaussianFilter.convolveAndTranspose((Kernel)object2, (int[])object, nArray3, n4, n5, true, false, false, ConvolveFilter.CLAMP_EDGES);
                GaussianFilter.convolveAndTranspose((Kernel)object2, nArray3, nArray4, n5, n4, true, false, false, ConvolveFilter.CLAMP_EDGES);
                function2D = new ImageFunction2D(nArray4, n4, n5, 1, this.bumpSource == 1);
            } else {
                function2D = new ImageFunction2D(nArray, n, n2, 1, this.bumpSource == 1);
            }
        }
        Vector3f vector3f4 = new Vector3f();
        Vector3f vector3f5 = new Vector3f();
        object = new Vector3f();
        for (int i = 0; i < n2; ++i) {
            float f2 = i;
            vector3f.y = i;
            for (int j = 0; j < n; ++j) {
                int n6;
                float f3 = j;
                if (this.bumpSource != 3) {
                    float f4;
                    n6 = 0;
                    vector3f3.z = 0.0f;
                    vector3f3.y = 0.0f;
                    vector3f3.x = 0.0f;
                    float f5 = f * function2D.evaluate(f3, f2);
                    float f6 = j > 0 ? f * function2D.evaluate(f3 - 1.0f, f2) - f5 : -2.0f;
                    float f7 = i > 0 ? f * function2D.evaluate(f3, f2 - 1.0f) - f5 : -2.0f;
                    float f8 = j < n - 1 ? f * function2D.evaluate(f3 + 1.0f, f2) - f5 : -2.0f;
                    float f9 = f4 = i < n2 - 1 ? f * function2D.evaluate(f3, f2 + 1.0f) - f5 : -2.0f;
                    if (f6 != -2.0f && f4 != -2.0f) {
                        vector3f4.x = -1.0f;
                        vector3f4.y = 0.0f;
                        vector3f4.z = f6;
                        vector3f5.x = 0.0f;
                        vector3f5.y = 1.0f;
                        vector3f5.z = f4;
                        ((Vector3f)object).cross(vector3f4, vector3f5);
                        ((Vector3f)object).normalize();
                        if ((double)((Vector3f)object).z < 0.0) {
                            ((Vector3f)object).z = -((Vector3f)object).z;
                        }
                        vector3f3.add((Tuple3f)object);
                        ++n6;
                    }
                    if (f6 != -2.0f && f7 != -2.0f) {
                        vector3f4.x = -1.0f;
                        vector3f4.y = 0.0f;
                        vector3f4.z = f6;
                        vector3f5.x = 0.0f;
                        vector3f5.y = -1.0f;
                        vector3f5.z = f7;
                        ((Vector3f)object).cross(vector3f4, vector3f5);
                        ((Vector3f)object).normalize();
                        if ((double)((Vector3f)object).z < 0.0) {
                            ((Vector3f)object).z = -((Vector3f)object).z;
                        }
                        vector3f3.add((Tuple3f)object);
                        ++n6;
                    }
                    if (f7 != -2.0f && f8 != -2.0f) {
                        vector3f4.x = 0.0f;
                        vector3f4.y = -1.0f;
                        vector3f4.z = f7;
                        vector3f5.x = 1.0f;
                        vector3f5.y = 0.0f;
                        vector3f5.z = f8;
                        ((Vector3f)object).cross(vector3f4, vector3f5);
                        ((Vector3f)object).normalize();
                        if ((double)((Vector3f)object).z < 0.0) {
                            ((Vector3f)object).z = -((Vector3f)object).z;
                        }
                        vector3f3.add((Tuple3f)object);
                        ++n6;
                    }
                    if (f8 != -2.0f && f4 != -2.0f) {
                        vector3f4.x = 1.0f;
                        vector3f4.y = 0.0f;
                        vector3f4.z = f8;
                        vector3f5.x = 0.0f;
                        vector3f5.y = 1.0f;
                        vector3f5.z = f4;
                        ((Vector3f)object).cross(vector3f4, vector3f5);
                        ((Vector3f)object).normalize();
                        if ((double)((Vector3f)object).z < 0.0) {
                            ((Vector3f)object).z = -((Vector3f)object).z;
                        }
                        vector3f3.add((Tuple3f)object);
                        ++n6;
                    }
                    vector3f3.x /= (float)n6;
                    vector3f3.y /= (float)n6;
                    vector3f3.z /= (float)n6;
                }
                if (bl) {
                    vector3f3.x = -vector3f3.x;
                    vector3f3.y = -vector3f3.y;
                }
                vector3f.x = j;
                if (vector3f3.z >= 0.0f) {
                    if (this.environmentMap != null) {
                        this.tmpv2.set(vector3f2);
                        this.tmpv2.sub(vector3f);
                        this.tmpv2.normalize();
                        this.tmpv.set(vector3f3);
                        this.tmpv.normalize();
                        this.tmpv.scale(2.0f * this.tmpv.dot(this.tmpv2));
                        this.tmpv.sub(this.v);
                        this.tmpv.normalize();
                        this.setFromRGB(color4f, this.getEnvironmentMapP(vector3f3, nArray, n, n2));
                        n6 = nArray[n3] & 0xFF000000;
                        int n7 = (int)(color4f.x * 255.0f) << 16 | (int)(color4f.y * 255.0f) << 8 | (int)(color4f.z * 255.0f);
                        nArray2[n3++] = n6 | n7;
                        continue;
                    }
                    nArray2[n3++] = 0;
                    continue;
                }
                nArray2[n3++] = 0;
            }
        }
        return nArray2;
    }

    private int getEnvironmentMapP(Vector3f vector3f, int[] nArray, int n, int n2) {
        if (this.environmentMap != null) {
            float f = 0.5f * (1.0f + vector3f.x);
            float f2 = 0.5f * (1.0f + vector3f.y);
            f = ImageMath.clamp(f * (float)this.envWidth, 0.0f, (float)(this.envWidth - 1));
            f2 = ImageMath.clamp(f2 * (float)this.envHeight, 0.0f, (float)(this.envHeight - 1));
            int n3 = (int)f;
            int n4 = (int)f2;
            float f3 = f - (float)n3;
            float f4 = f2 - (float)n4;
            int n5 = this.envWidth * n4 + n3;
            int n6 = n3 == this.envWidth - 1 ? 0 : 1;
            int n7 = n4 == this.envHeight - 1 ? 0 : this.envWidth;
            return ImageMath.bilinearInterpolate(f3, f4, this.envPixels[n5], this.envPixels[n5 + n6], this.envPixels[n5 + n7], this.envPixels[n5 + n6 + n7]);
        }
        return 1;
    }

    public String toString() {
        return "Stylize/Shade...";
    }
}

