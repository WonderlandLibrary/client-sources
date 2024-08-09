/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.ImageUtils;
import com.jhlabs.image.PointFilter;
import com.jhlabs.math.FBM;
import com.jhlabs.math.Function2D;
import com.jhlabs.math.Noise;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Random;

public class SkyFilter
extends PointFilter {
    private float scale = 0.1f;
    private float stretch = 1.0f;
    private float angle = 0.0f;
    private float amount = 1.0f;
    private float H = 1.0f;
    private float octaves = 8.0f;
    private float lacunarity = 2.0f;
    private float gain = 1.0f;
    private float bias = 0.6f;
    private int operation;
    private float min;
    private float max;
    private boolean ridged;
    private FBM fBm;
    protected Random random = new Random();
    private Function2D basis;
    private float cloudCover = 0.5f;
    private float cloudSharpness = 0.5f;
    private float time = 0.3f;
    private float glow = 0.5f;
    private float glowFalloff = 0.5f;
    private float haziness = 0.96f;
    private float t = 0.0f;
    private float sunRadius = 10.0f;
    private int sunColor = -1;
    private float sunR;
    private float sunG;
    private float sunB;
    private float sunAzimuth = 0.5f;
    private float sunElevation = 0.5f;
    private float windSpeed = 0.0f;
    private float cameraAzimuth = 0.0f;
    private float cameraElevation = 0.0f;
    private float fov = 1.0f;
    private float[] exponents;
    private float[] tan;
    private BufferedImage skyColors;
    private int[] skyPixels;
    private static final float r255 = 0.003921569f;
    private float width;
    private float height;
    float mn;
    float mx;

    public SkyFilter() {
        if (this.skyColors == null) {
            this.skyColors = ImageUtils.createImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("SkyColors.png")).getSource());
        }
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

    public void setT(float f) {
        this.t = f;
    }

    public float getT() {
        return this.t;
    }

    public void setFOV(float f) {
        this.fov = f;
    }

    public float getFOV() {
        return this.fov;
    }

    public void setCloudCover(float f) {
        this.cloudCover = f;
    }

    public float getCloudCover() {
        return this.cloudCover;
    }

    public void setCloudSharpness(float f) {
        this.cloudSharpness = f;
    }

    public float getCloudSharpness() {
        return this.cloudSharpness;
    }

    public void setTime(float f) {
        this.time = f;
    }

    public float getTime() {
        return this.time;
    }

    public void setGlow(float f) {
        this.glow = f;
    }

    public float getGlow() {
        return this.glow;
    }

    public void setGlowFalloff(float f) {
        this.glowFalloff = f;
    }

    public float getGlowFalloff() {
        return this.glowFalloff;
    }

    public void setAngle(float f) {
        this.angle = f;
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

    public void setHaziness(float f) {
        this.haziness = f;
    }

    public float getHaziness() {
        return this.haziness;
    }

    public void setSunElevation(float f) {
        this.sunElevation = f;
    }

    public float getSunElevation() {
        return this.sunElevation;
    }

    public void setSunAzimuth(float f) {
        this.sunAzimuth = f;
    }

    public float getSunAzimuth() {
        return this.sunAzimuth;
    }

    public void setSunColor(int n) {
        this.sunColor = n;
    }

    public int getSunColor() {
        return this.sunColor;
    }

    public void setCameraElevation(float f) {
        this.cameraElevation = f;
    }

    public float getCameraElevation() {
        return this.cameraElevation;
    }

    public void setCameraAzimuth(float f) {
        this.cameraAzimuth = f;
    }

    public float getCameraAzimuth() {
        return this.cameraAzimuth;
    }

    public void setWindSpeed(float f) {
        this.windSpeed = f;
    }

    public float getWindSpeed() {
        return this.windSpeed;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        int n;
        int n2;
        long l = System.currentTimeMillis();
        this.sunR = (float)(this.sunColor >> 16 & 0xFF) * 0.003921569f;
        this.sunG = (float)(this.sunColor >> 8 & 0xFF) * 0.003921569f;
        this.sunB = (float)(this.sunColor & 0xFF) * 0.003921569f;
        this.mn = 10000.0f;
        this.mx = -10000.0f;
        this.exponents = new float[(int)this.octaves + 1];
        float f = 1.0f;
        for (n2 = 0; n2 <= (int)this.octaves; ++n2) {
            this.exponents[n2] = (float)Math.pow(2.0, -n2);
            f *= this.lacunarity;
        }
        this.min = -1.0f;
        this.max = 1.0f;
        this.width = bufferedImage.getWidth();
        this.height = bufferedImage.getHeight();
        n2 = bufferedImage.getHeight();
        this.tan = new float[n2];
        for (n = 0; n < n2; ++n) {
            this.tan[n] = (float)Math.tan((double)(this.fov * (float)n / (float)n2) * Math.PI * 0.5);
        }
        if (bufferedImage2 == null) {
            bufferedImage2 = this.createCompatibleDestImage(bufferedImage, null);
        }
        n = (int)(63.0f * this.time);
        Graphics2D graphics2D = bufferedImage2.createGraphics();
        graphics2D.drawImage(this.skyColors, 0, 0, bufferedImage2.getWidth(), bufferedImage2.getHeight(), n, 0, n + 1, 64, null);
        graphics2D.dispose();
        BufferedImage bufferedImage3 = super.filter(bufferedImage2, bufferedImage2);
        long l2 = System.currentTimeMillis();
        System.out.println(this.mn + " " + this.mx + " " + (float)(l2 - l) * 0.001f);
        this.exponents = null;
        this.tan = null;
        return bufferedImage2;
    }

    public float evaluate(float f, float f2) {
        int n;
        float f3 = 0.0f;
        f += 371.0f;
        f2 += 529.0f;
        for (n = 0; n < (int)this.octaves; ++n) {
            f3 += Noise.noise3(f, f2, this.t) * this.exponents[n];
            f *= this.lacunarity;
            f2 *= this.lacunarity;
        }
        float f4 = this.octaves - (float)((int)this.octaves);
        if (f4 != 0.0f) {
            f3 += f4 * Noise.noise3(f, f2, this.t) * this.exponents[n];
        }
        return f3;
    }

    @Override
    public int filterRGB(int n, int n2, int n3) {
        float f;
        float f2 = (float)n / this.width;
        float f3 = (float)n2 / this.height;
        float f4 = (float)Math.pow(this.haziness, 100.0f * f3 * f3);
        float f5 = (float)(n3 >> 16 & 0xFF) * 0.003921569f;
        float f6 = (float)(n3 >> 8 & 0xFF) * 0.003921569f;
        float f7 = (float)(n3 & 0xFF) * 0.003921569f;
        float f8 = this.width * 0.5f;
        float f9 = (float)n - f8;
        float f10 = n2;
        f10 = this.tan[n2];
        f9 = (f2 - 0.5f) * (1.0f + f10);
        f10 += this.t * this.windSpeed;
        float f11 = f = this.evaluate(f9 /= this.scale, f10 /= this.scale * this.stretch);
        f = (f + 1.23f) / 2.46f;
        int n4 = n3 & 0xFF000000;
        float f12 = f - this.cloudCover;
        if (f12 < 0.0f) {
            f12 = 0.0f;
        }
        float f13 = 1.0f - (float)Math.pow(this.cloudSharpness, f12);
        this.mn = Math.min(this.mn, f13);
        this.mx = Math.max(this.mx, f13);
        float f14 = this.width * this.sunAzimuth;
        float f15 = this.height * this.sunElevation;
        float f16 = (float)n - f14;
        float f17 = (float)n2 - f15;
        float f18 = f16 * f16 + f17 * f17;
        f18 = (float)Math.pow(f18, this.glowFalloff);
        float f19 = 10.0f * (float)Math.exp(-f18 * this.glow * 0.1f);
        f5 += f19 * this.sunR;
        f6 += f19 * this.sunG;
        f7 += f19 * this.sunB;
        float f20 = (1.0f - f13 * f13 * f13 * f13) * this.amount;
        float f21 = this.sunR * f20;
        float f22 = this.sunG * f20;
        float f23 = this.sunB * f20;
        float f24 = 1.0f - (f13 *= f4);
        f5 = f24 * f5 + f13 * f21;
        f6 = f24 * f6 + f13 * f22;
        f7 = f24 * f7 + f13 * f23;
        float f25 = this.gain;
        f5 = 1.0f - (float)Math.exp(-f5 * f25);
        f6 = 1.0f - (float)Math.exp(-f6 * f25);
        f7 = 1.0f - (float)Math.exp(-f7 * f25);
        int n5 = (int)(255.0f * f5) << 16;
        int n6 = (int)(255.0f * f6) << 8;
        int n7 = (int)(255.0f * f7);
        int n8 = 0xFF000000 | n5 | n6 | n7;
        return n8;
    }

    public String toString() {
        return "Texture/Sky...";
    }
}

