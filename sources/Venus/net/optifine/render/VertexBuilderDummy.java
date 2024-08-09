/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.render;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;

public class VertexBuilderDummy
implements IVertexBuilder {
    private IRenderTypeBuffer.Impl renderTypeBuffer = null;

    public VertexBuilderDummy(IRenderTypeBuffer.Impl impl) {
        this.renderTypeBuffer = impl;
    }

    @Override
    public IRenderTypeBuffer.Impl getRenderTypeBuffer() {
        return this.renderTypeBuffer;
    }

    @Override
    public IVertexBuilder pos(double d, double d2, double d3) {
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
        return this;
    }

    @Override
    public IVertexBuilder lightmap(int n, int n2) {
        return this;
    }

    @Override
    public IVertexBuilder normal(float f, float f2, float f3) {
        return this;
    }

    @Override
    public void endVertex() {
    }
}

