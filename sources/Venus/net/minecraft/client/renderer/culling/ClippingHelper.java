/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.culling;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector4f;
import net.optifine.render.ICamera;

public class ClippingHelper
implements ICamera {
    private final Vector4f[] frustum = new Vector4f[6];
    private double cameraX;
    private double cameraY;
    private double cameraZ;
    public boolean disabled = false;

    public ClippingHelper(Matrix4f matrix4f, Matrix4f matrix4f2) {
        this.calculateFrustum(matrix4f, matrix4f2);
    }

    @Override
    public void setCameraPosition(double d, double d2, double d3) {
        this.cameraX = d;
        this.cameraY = d2;
        this.cameraZ = d3;
    }

    private void calculateFrustum(Matrix4f matrix4f, Matrix4f matrix4f2) {
        Matrix4f matrix4f3 = matrix4f2.copy();
        matrix4f3.mul(matrix4f);
        matrix4f3.transpose();
        this.setFrustumPlane(matrix4f3, -1, 0, 0, 0);
        this.setFrustumPlane(matrix4f3, 1, 0, 0, 1);
        this.setFrustumPlane(matrix4f3, 0, -1, 0, 2);
        this.setFrustumPlane(matrix4f3, 0, 1, 0, 3);
        this.setFrustumPlane(matrix4f3, 0, 0, -1, 4);
        this.setFrustumPlane(matrix4f3, 0, 0, 1, 5);
    }

    private void setFrustumPlane(Matrix4f matrix4f, int n, int n2, int n3, int n4) {
        Vector4f vector4f = new Vector4f(n, n2, n3, 1.0f);
        vector4f.transform(matrix4f);
        vector4f.normalize();
        this.frustum[n4] = vector4f;
    }

    @Override
    public boolean isBoundingBoxInFrustum(AxisAlignedBB axisAlignedBB) {
        return this.isBoxInFrustum(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
    }

    private boolean isBoxInFrustum(double d, double d2, double d3, double d4, double d5, double d6) {
        if (this.disabled) {
            return false;
        }
        float f = (float)(d - this.cameraX);
        float f2 = (float)(d2 - this.cameraY);
        float f3 = (float)(d3 - this.cameraZ);
        float f4 = (float)(d4 - this.cameraX);
        float f5 = (float)(d5 - this.cameraY);
        float f6 = (float)(d6 - this.cameraZ);
        return this.isBoxInFrustumRaw(f, f2, f3, f4, f5, f6);
    }

    private boolean isBoxInFrustumRaw(float f, float f2, float f3, float f4, float f5, float f6) {
        for (int i = 0; i < 6; ++i) {
            float f7;
            float f8;
            float f9;
            Vector4f vector4f = this.frustum[i];
            float f10 = vector4f.getX();
            if (!(f10 * f + (f9 = vector4f.getY()) * f2 + (f8 = vector4f.getZ()) * f3 + (f7 = vector4f.getW()) <= 0.0f) || !(f10 * f4 + f9 * f2 + f8 * f3 + f7 <= 0.0f) || !(f10 * f + f9 * f5 + f8 * f3 + f7 <= 0.0f) || !(f10 * f4 + f9 * f5 + f8 * f3 + f7 <= 0.0f) || !(f10 * f + f9 * f2 + f8 * f6 + f7 <= 0.0f) || !(f10 * f4 + f9 * f2 + f8 * f6 + f7 <= 0.0f) || !(f10 * f + f9 * f5 + f8 * f6 + f7 <= 0.0f) || !(f10 * f4 + f9 * f5 + f8 * f6 + f7 <= 0.0f)) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean isBoxInFrustumFully(double d, double d2, double d3, double d4, double d5, double d6) {
        if (this.disabled) {
            return false;
        }
        float f = (float)d;
        float f2 = (float)d2;
        float f3 = (float)d3;
        float f4 = (float)d4;
        float f5 = (float)d5;
        float f6 = (float)d6;
        for (int i = 0; i < 6; ++i) {
            Vector4f vector4f = this.frustum[i];
            float f7 = vector4f.getX();
            float f8 = vector4f.getY();
            float f9 = vector4f.getZ();
            float f10 = vector4f.getW();
            if (!(i < 4 ? f7 * f + f8 * f2 + f9 * f3 + f10 <= 0.0f || f7 * f4 + f8 * f2 + f9 * f3 + f10 <= 0.0f || f7 * f + f8 * f5 + f9 * f3 + f10 <= 0.0f || f7 * f4 + f8 * f5 + f9 * f3 + f10 <= 0.0f || f7 * f + f8 * f2 + f9 * f6 + f10 <= 0.0f || f7 * f4 + f8 * f2 + f9 * f6 + f10 <= 0.0f || f7 * f + f8 * f5 + f9 * f6 + f10 <= 0.0f || f7 * f4 + f8 * f5 + f9 * f6 + f10 <= 0.0f : f7 * f + f8 * f2 + f9 * f3 + f10 <= 0.0f && f7 * f4 + f8 * f2 + f9 * f3 + f10 <= 0.0f && f7 * f + f8 * f5 + f9 * f3 + f10 <= 0.0f && f7 * f4 + f8 * f5 + f9 * f3 + f10 <= 0.0f && f7 * f + f8 * f2 + f9 * f6 + f10 <= 0.0f && f7 * f4 + f8 * f2 + f9 * f6 + f10 <= 0.0f && f7 * f + f8 * f5 + f9 * f6 + f10 <= 0.0f && f7 * f4 + f8 * f5 + f9 * f6 + f10 <= 0.0f)) continue;
            return true;
        }
        return false;
    }

    public Vector4f[] getFrustum() {
        return this.frustum;
    }
}

