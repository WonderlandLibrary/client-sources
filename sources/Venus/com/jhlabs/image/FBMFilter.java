/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.Colormap;
import com.jhlabs.image.Gradient;
import com.jhlabs.image.ImageMath;
import com.jhlabs.image.PixelUtils;
import com.jhlabs.image.PointFilter;
import com.jhlabs.math.CellularFunction2D;
import com.jhlabs.math.FBM;
import com.jhlabs.math.Function2D;
import com.jhlabs.math.Noise;
import com.jhlabs.math.RidgedFBM;
import com.jhlabs.math.SCNoise;
import com.jhlabs.math.VLNoise;
import java.awt.image.BufferedImage;
import java.util.Random;

public class FBMFilter
extends PointFilter
implements Cloneable {
    public static final int NOISE = 0;
    public static final int RIDGED = 1;
    public static final int VLNOISE = 2;
    public static final int SCNOISE = 3;
    public static final int CELLULAR = 4;
    private float scale = 32.0f;
    private float stretch = 1.0f;
    private float angle = 0.0f;
    private float amount = 1.0f;
    private float H = 1.0f;
    private float octaves = 4.0f;
    private float lacunarity = 2.0f;
    private float gain = 0.5f;
    private float bias = 0.5f;
    private int operation;
    private float m00 = 1.0f;
    private float m01 = 0.0f;
    private float m10 = 0.0f;
    private float m11 = 1.0f;
    private float min;
    private float max;
    private Colormap colormap = new Gradient();
    private boolean ridged;
    private FBM fBm;
    protected Random random = new Random();
    private int basisType = 0;
    private Function2D basis;

    public FBMFilter() {
        this.setBasisType(0);
    }

    public void setAmount(float f) {
        this.amount = f;
    }

    public float getAmount() {
        return this.amount;
    }

    public void setOperation(int n) {
        this.operation = n;
    }

    public int getOperation() {
        return this.operation;
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
        float f2 = (float)Math.cos(this.angle);
        float f3 = (float)Math.sin(this.angle);
        this.m00 = f2;
        this.m01 = f3;
        this.m10 = -f3;
        this.m11 = f2;
    }

    public float getAngle() {
        return this.angle;
    }

    public void setOctaves(float f) {
        this.octaves = f;
    }

    public float getOctaves() {
        return this.octaves;
    }

    public void setH(float f) {
        this.H = f;
    }

    public float getH() {
        return this.H;
    }

    public void setLacunarity(float f) {
        this.lacunarity = f;
    }

    public float getLacunarity() {
        return this.lacunarity;
    }

    public void setGain(float f) {
        this.gain = f;
    }

    public float getGain() {
        return this.gain;
    }

    public void setBias(float f) {
        this.bias = f;
    }

    public float getBias() {
        return this.bias;
    }

    public void setColormap(Colormap colormap) {
        this.colormap = colormap;
    }

    public Colormap getColormap() {
        return this.colormap;
    }

    public void setBasisType(int n) {
        this.basisType = n;
        switch (n) {
            default: {
                this.basis = new Noise();
                break;
            }
            case 1: {
                this.basis = new RidgedFBM();
                break;
            }
            case 2: {
                this.basis = new VLNoise();
                break;
            }
            case 3: {
                this.basis = new SCNoise();
                break;
            }
            case 4: {
                this.basis = new CellularFunction2D();
            }
        }
    }

    public int getBasisType() {
        return this.basisType;
    }

    public void setBasis(Function2D function2D) {
        this.basis = function2D;
    }

    public Function2D getBasis() {
        return this.basis;
    }

    protected FBM makeFBM(float f, float f2, float f3) {
        FBM fBM = new FBM(f, f2, f3, this.basis);
        float[] fArray = Noise.findRange(fBM, null);
        this.min = fArray[0];
        this.max = fArray[1];
        return fBM;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        this.fBm = this.makeFBM(this.H, this.lacunarity, this.octaves);
        return super.filter(bufferedImage, bufferedImage2);
    }

    @Override
    public int filterRGB(int n, int n2, int n3) {
        int n4;
        float f = this.m00 * (float)n + this.m01 * (float)n2;
        float f2 = this.m10 * (float)n + this.m11 * (float)n2;
        float f3 = this.fBm.evaluate(f /= this.scale, f2 /= this.scale * this.stretch);
        f3 = (f3 - this.min) / (this.max - this.min);
        f3 = ImageMath.gain(f3, this.gain);
        f3 = ImageMath.bias(f3, this.bias);
        f3 *= this.amount;
        int n5 = n3 & 0xFF000000;
        if (this.colormap != null) {
            n4 = this.colormap.getColor(f3);
        } else {
            n4 = PixelUtils.clamp((int)(f3 * 255.0f));
            int n6 = n4 << 16;
            int n7 = n4 << 8;
            int n8 = n4;
            n4 = n5 | n6 | n7 | n8;
        }
        if (this.operation != 0) {
            n4 = PixelUtils.combinePixels(n3, n4, this.operation);
        }
        return n4;
    }

    public String toString() {
        return "Texture/Fractal Brownian Motion...";
    }
}

