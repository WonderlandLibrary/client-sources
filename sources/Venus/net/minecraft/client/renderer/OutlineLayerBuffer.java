/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import com.mojang.blaze3d.vertex.DefaultColorVertexBuilder;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.vertex.VertexBuilderUtils;
import java.util.Optional;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;

public class OutlineLayerBuffer
implements IRenderTypeBuffer {
    private final IRenderTypeBuffer.Impl buffer;
    private final IRenderTypeBuffer.Impl outlineBuffer = IRenderTypeBuffer.getImpl(new BufferBuilder(256));
    private int red = 255;
    private int green = 255;
    private int blue = 255;
    private int alpha = 255;

    public OutlineLayerBuffer(IRenderTypeBuffer.Impl impl) {
        this.buffer = impl;
    }

    @Override
    public IVertexBuilder getBuffer(RenderType renderType) {
        if (renderType.isColoredOutlineBuffer()) {
            IVertexBuilder iVertexBuilder = this.outlineBuffer.getBuffer(renderType);
            return new ColoredOutline(iVertexBuilder, this.red, this.green, this.blue, this.alpha);
        }
        IVertexBuilder iVertexBuilder = this.buffer.getBuffer(renderType);
        Optional<RenderType> optional = renderType.getOutline();
        if (optional.isPresent()) {
            IVertexBuilder iVertexBuilder2 = this.outlineBuffer.getBuffer(optional.get());
            ColoredOutline coloredOutline = new ColoredOutline(iVertexBuilder2, this.red, this.green, this.blue, this.alpha);
            return VertexBuilderUtils.newDelegate(coloredOutline, iVertexBuilder);
        }
        return iVertexBuilder;
    }

    public void setColor(int n, int n2, int n3, int n4) {
        this.red = n;
        this.green = n2;
        this.blue = n3;
        this.alpha = n4;
    }

    public void finish() {
        this.outlineBuffer.finish();
    }

    static class ColoredOutline
    extends DefaultColorVertexBuilder {
        private final IVertexBuilder coloredBuffer;
        private double x;
        private double y;
        private double z;
        private float u;
        private float v;

        private ColoredOutline(IVertexBuilder iVertexBuilder, int n, int n2, int n3, int n4) {
            this.coloredBuffer = iVertexBuilder;
            super.setDefaultColor(n, n2, n3, n4);
        }

        @Override
        public void setDefaultColor(int n, int n2, int n3, int n4) {
        }

        @Override
        public IVertexBuilder pos(double d, double d2, double d3) {
            this.x = d;
            this.y = d2;
            this.z = d3;
            return this;
        }

        @Override
        public IVertexBuilder color(int n, int n2, int n3, int n4) {
            return this;
        }

        @Override
        public IVertexBuilder tex(float f, float f2) {
            this.u = f;
            this.v = f2;
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
        public void addVertex(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, int n, int n2, float f10, float f11, float f12) {
            this.coloredBuffer.pos(f, f2, f3).color(this.defaultRed, this.defaultGreen, this.defaultBlue, this.defaultAlpha).tex(f8, f9).endVertex();
        }

        @Override
        public void endVertex() {
            this.coloredBuffer.pos(this.x, this.y, this.z).color(this.defaultRed, this.defaultGreen, this.defaultBlue, this.defaultAlpha).tex(this.u, this.v).endVertex();
        }
    }
}

