/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.util.math.MathHelper;

public class ClippingHelperShadow
extends ClippingHelper {
    private static ClippingHelperShadow instance = new ClippingHelperShadow();
    float[] frustumTest = new float[6];
    float[][] shadowClipPlanes = new float[10][4];
    int shadowClipPlaneCount;
    float[] matInvMP = new float[16];
    float[] vecIntersection = new float[4];
    float[][] frustum;

    public ClippingHelperShadow() {
        super(null, null);
    }

    public boolean isBoxInFrustum(double d, double d2, double d3, double d4, double d5, double d6) {
        for (int i = 0; i < this.shadowClipPlaneCount; ++i) {
            float[] fArray = this.shadowClipPlanes[i];
            if (!(this.dot4(fArray, d, d2, d3) <= 0.0) || !(this.dot4(fArray, d4, d2, d3) <= 0.0) || !(this.dot4(fArray, d, d5, d3) <= 0.0) || !(this.dot4(fArray, d4, d5, d3) <= 0.0) || !(this.dot4(fArray, d, d2, d6) <= 0.0) || !(this.dot4(fArray, d4, d2, d6) <= 0.0) || !(this.dot4(fArray, d, d5, d6) <= 0.0) || !(this.dot4(fArray, d4, d5, d6) <= 0.0)) continue;
            return true;
        }
        return false;
    }

    private double dot4(float[] fArray, double d, double d2, double d3) {
        return (double)fArray[0] * d + (double)fArray[1] * d2 + (double)fArray[2] * d3 + (double)fArray[3];
    }

    private double dot3(float[] fArray, float[] fArray2) {
        return (double)fArray[0] * (double)fArray2[0] + (double)fArray[1] * (double)fArray2[1] + (double)fArray[2] * (double)fArray2[2];
    }

    public static ClippingHelper getInstance() {
        instance.init();
        return instance;
    }

    private void normalizePlane(float[] fArray) {
        float f = MathHelper.sqrt(fArray[0] * fArray[0] + fArray[1] * fArray[1] + fArray[2] * fArray[2]);
        fArray[0] = fArray[0] / f;
        fArray[1] = fArray[1] / f;
        fArray[2] = fArray[2] / f;
        fArray[3] = fArray[3] / f;
    }

    private void normalize3(float[] fArray) {
        float f = MathHelper.sqrt(fArray[0] * fArray[0] + fArray[1] * fArray[1] + fArray[2] * fArray[2]);
        if (f == 0.0f) {
            f = 1.0f;
        }
        fArray[0] = fArray[0] / f;
        fArray[1] = fArray[1] / f;
        fArray[2] = fArray[2] / f;
    }

    private void assignPlane(float[] fArray, float f, float f2, float f3, float f4) {
        float f5 = (float)Math.sqrt(f * f + f2 * f2 + f3 * f3);
        fArray[0] = f / f5;
        fArray[1] = f2 / f5;
        fArray[2] = f3 / f5;
        fArray[3] = f4 / f5;
    }

    private void copyPlane(float[] fArray, float[] fArray2) {
        fArray[0] = fArray2[0];
        fArray[1] = fArray2[1];
        fArray[2] = fArray2[2];
        fArray[3] = fArray2[3];
    }

    private void cross3(float[] fArray, float[] fArray2, float[] fArray3) {
        fArray[0] = fArray2[1] * fArray3[2] - fArray2[2] * fArray3[1];
        fArray[1] = fArray2[2] * fArray3[0] - fArray2[0] * fArray3[2];
        fArray[2] = fArray2[0] * fArray3[1] - fArray2[1] * fArray3[0];
    }

    private void addShadowClipPlane(float[] fArray) {
        this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], fArray);
    }

    private float length(float f, float f2, float f3) {
        return (float)Math.sqrt(f * f + f2 * f2 + f3 * f3);
    }

    private float distance(float f, float f2, float f3, float f4, float f5, float f6) {
        return this.length(f - f4, f2 - f5, f3 - f6);
    }

    private void makeShadowPlane(float[] fArray, float[] fArray2, float[] fArray3, float[] fArray4) {
        this.cross3(this.vecIntersection, fArray2, fArray3);
        this.cross3(fArray, this.vecIntersection, fArray4);
        this.normalize3(fArray);
        float f = (float)this.dot3(fArray2, fArray3);
        float f2 = (float)this.dot3(fArray, fArray3);
        float f3 = this.distance(fArray[0], fArray[1], fArray[2], fArray3[0] * f2, fArray3[1] * f2, fArray3[2] * f2);
        float f4 = this.distance(fArray2[0], fArray2[1], fArray2[2], fArray3[0] * f, fArray3[1] * f, fArray3[2] * f);
        float f5 = f3 / f4;
        float f6 = (float)this.dot3(fArray, fArray2);
        float f7 = this.distance(fArray[0], fArray[1], fArray[2], fArray2[0] * f6, fArray2[1] * f6, fArray2[2] * f6);
        float f8 = this.distance(fArray3[0], fArray3[1], fArray3[2], fArray2[0] * f, fArray2[1] * f, fArray2[2] * f);
        float f9 = f7 / f8;
        fArray[3] = fArray2[3] * f5 + fArray3[3] * f9;
    }

    public void init() {
    }
}

