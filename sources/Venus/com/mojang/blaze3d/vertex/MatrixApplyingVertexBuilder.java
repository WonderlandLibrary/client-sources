/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.blaze3d.vertex;

import com.mojang.blaze3d.vertex.DefaultColorVertexBuilder;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector4f;

public class MatrixApplyingVertexBuilder
extends DefaultColorVertexBuilder {
    private final IVertexBuilder vertexBuilder;
    private final Matrix4f currentTransformMatrixInverted;
    private final Matrix3f normalMatrixInverted;
    private float posX;
    private float posY;
    private float posZ;
    private int u;
    private int v;
    private int light;
    private float normalX;
    private float normalY;
    private float normalZ;

    public MatrixApplyingVertexBuilder(IVertexBuilder iVertexBuilder, Matrix4f matrix4f, Matrix3f matrix3f) {
        this.vertexBuilder = iVertexBuilder;
        this.currentTransformMatrixInverted = matrix4f.copy();
        this.currentTransformMatrixInverted.invert();
        this.normalMatrixInverted = matrix3f.copy();
        this.normalMatrixInverted.invert();
        this.reset();
    }

    private void reset() {
        this.posX = 0.0f;
        this.posY = 0.0f;
        this.posZ = 0.0f;
        this.u = 0;
        this.v = 10;
        this.light = 0xF000F0;
        this.normalX = 0.0f;
        this.normalY = 1.0f;
        this.normalZ = 0.0f;
    }

    @Override
    public void endVertex() {
        Vector3f vector3f = new Vector3f(this.normalX, this.normalY, this.normalZ);
        vector3f.transform(this.normalMatrixInverted);
        Direction direction = Direction.getFacingFromVector(vector3f.getX(), vector3f.getY(), vector3f.getZ());
        Vector4f vector4f = new Vector4f(this.posX, this.posY, this.posZ, 1.0f);
        vector4f.transform(this.currentTransformMatrixInverted);
        vector4f.transform(Vector3f.YP.rotationDegrees(180.0f));
        vector4f.transform(Vector3f.XP.rotationDegrees(-90.0f));
        vector4f.transform(direction.getRotation());
        float f = -vector4f.getX();
        float f2 = -vector4f.getY();
        this.vertexBuilder.pos(this.posX, this.posY, this.posZ).color(1.0f, 1.0f, 1.0f, 1.0f).tex(f, f2).overlay(this.u, this.v).lightmap(this.light).normal(this.normalX, this.normalY, this.normalZ).endVertex();
        this.reset();
    }

    @Override
    public IVertexBuilder pos(double d, double d2, double d3) {
        this.posX = (float)d;
        this.posY = (float)d2;
        this.posZ = (float)d3;
        return this;
    }

    @Override
    public IVertexBuilder color(int n, int n2, int n3, int n4) {
        return this;
    }

    @Override
    public IVertexBuilder tex(float f, float f2) {
        return this;
    }

    @Override
    public IVertexBuilder overlay(int n, int n2) {
        this.u = n;
        this.v = n2;
        return this;
    }

    @Override
    public IVertexBuilder lightmap(int n, int n2) {
        this.light = n | n2 << 16;
        return this;
    }

    @Override
    public IVertexBuilder normal(float f, float f2, float f3) {
        this.normalX = f;
        this.normalY = f2;
        this.normalZ = f3;
        return this;
    }
}

