/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.blaze3d.vertex;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.optifine.render.VertexBuilderWrapper;

public class VertexBuilderUtils {
    public static IVertexBuilder newDelegate(IVertexBuilder iVertexBuilder, IVertexBuilder iVertexBuilder2) {
        return new DelegatingVertexBuilder(iVertexBuilder, iVertexBuilder2);
    }

    static class DelegatingVertexBuilder
    extends VertexBuilderWrapper
    implements IVertexBuilder {
        private final IVertexBuilder vertexBuilder;
        private final IVertexBuilder delegateBuilder;
        private boolean fixMultitextureUV;

        public DelegatingVertexBuilder(IVertexBuilder iVertexBuilder, IVertexBuilder iVertexBuilder2) {
            super(iVertexBuilder2);
            if (iVertexBuilder == iVertexBuilder2) {
                throw new IllegalArgumentException("Duplicate delegates");
            }
            this.vertexBuilder = iVertexBuilder;
            this.delegateBuilder = iVertexBuilder2;
            this.updateFixMultitextureUv();
        }

        @Override
        public IVertexBuilder pos(double d, double d2, double d3) {
            this.vertexBuilder.pos(d, d2, d3);
            this.delegateBuilder.pos(d, d2, d3);
            return this;
        }

        @Override
        public IVertexBuilder color(int n, int n2, int n3, int n4) {
            this.vertexBuilder.color(n, n2, n3, n4);
            this.delegateBuilder.color(n, n2, n3, n4);
            return this;
        }

        @Override
        public IVertexBuilder tex(float f, float f2) {
            this.vertexBuilder.tex(f, f2);
            this.delegateBuilder.tex(f, f2);
            return this;
        }

        @Override
        public IVertexBuilder overlay(int n, int n2) {
            this.vertexBuilder.overlay(n, n2);
            this.delegateBuilder.overlay(n, n2);
            return this;
        }

        @Override
        public IVertexBuilder lightmap(int n, int n2) {
            this.vertexBuilder.lightmap(n, n2);
            this.delegateBuilder.lightmap(n, n2);
            return this;
        }

        @Override
        public IVertexBuilder normal(float f, float f2, float f3) {
            this.vertexBuilder.normal(f, f2, f3);
            this.delegateBuilder.normal(f, f2, f3);
            return this;
        }

        @Override
        public void addVertex(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, int n, int n2, float f10, float f11, float f12) {
            if (this.fixMultitextureUV) {
                this.vertexBuilder.addVertex(f, f2, f3, f4, f5, f6, f7, f8 / 32.0f, f9 / 32.0f, n, n2, f10, f11, f12);
            } else {
                this.vertexBuilder.addVertex(f, f2, f3, f4, f5, f6, f7, f8, f9, n, n2, f10, f11, f12);
            }
            this.delegateBuilder.addVertex(f, f2, f3, f4, f5, f6, f7, f8, f9, n, n2, f10, f11, f12);
        }

        @Override
        public void endVertex() {
            this.vertexBuilder.endVertex();
            this.delegateBuilder.endVertex();
        }

        @Override
        public void setRenderBlocks(boolean bl) {
            super.setRenderBlocks(bl);
            this.updateFixMultitextureUv();
        }

        private void updateFixMultitextureUv() {
            this.fixMultitextureUV = !this.vertexBuilder.isMultiTexture() && this.delegateBuilder.isMultiTexture();
        }

        @Override
        public IVertexBuilder getSecondaryBuilder() {
            return this.vertexBuilder;
        }
    }
}

