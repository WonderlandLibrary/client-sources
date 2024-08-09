/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.fonts;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.math.vector.Matrix4f;
import net.optifine.util.MathUtils;

public class TexturedGlyph {
    private final RenderType normalType;
    private final RenderType seeThroughType;
    private final float u0;
    private final float u1;
    private final float v0;
    private final float v1;
    private final float minX;
    private final float maxX;
    private final float minY;
    private final float maxY;
    public static final Matrix4f MATRIX_IDENTITY = MathUtils.makeMatrixIdentity();

    public TexturedGlyph(RenderType renderType, RenderType renderType2, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        this.normalType = renderType;
        this.seeThroughType = renderType2;
        this.u0 = f;
        this.u1 = f2;
        this.v0 = f3;
        this.v1 = f4;
        this.minX = f5;
        this.maxX = f6;
        this.minY = f7;
        this.maxY = f8;
    }

    public void render(boolean bl, float f, float f2, Matrix4f matrix4f, IVertexBuilder iVertexBuilder, float f3, float f4, float f5, float f6, int n) {
        float f7;
        int n2 = 3;
        float f8 = f + this.minX;
        float f9 = f + this.maxX;
        float f10 = this.minY - 3.0f;
        float f11 = this.maxY - 3.0f;
        float f12 = f2 + f10;
        float f13 = f2 + f11;
        float f14 = bl ? 1.0f - 0.25f * f10 : 0.0f;
        float f15 = f7 = bl ? 1.0f - 0.25f * f11 : 0.0f;
        if (iVertexBuilder instanceof BufferBuilder && matrix4f == MATRIX_IDENTITY) {
            BufferBuilder bufferBuilder = (BufferBuilder)iVertexBuilder;
            int n3 = (int)(f3 * 255.0f);
            int n4 = (int)(f4 * 255.0f);
            int n5 = (int)(f5 * 255.0f);
            int n6 = (int)(f6 * 255.0f);
            int n7 = n & 0xFFFF;
            int n8 = n >> 16 & 0xFFFF;
            bufferBuilder.addVertexText(f8 + f14, f12, 0.0f, n3, n4, n5, n6, this.u0, this.v0, n7, n8);
            bufferBuilder.addVertexText(f8 + f7, f13, 0.0f, n3, n4, n5, n6, this.u0, this.v1, n7, n8);
            bufferBuilder.addVertexText(f9 + f7, f13, 0.0f, n3, n4, n5, n6, this.u1, this.v1, n7, n8);
            bufferBuilder.addVertexText(f9 + f14, f12, 0.0f, n3, n4, n5, n6, this.u1, this.v0, n7, n8);
        } else {
            iVertexBuilder.pos(matrix4f, f8 + f14, f12, 0.0f).color(f3, f4, f5, f6).tex(this.u0, this.v0).lightmap(n).endVertex();
            iVertexBuilder.pos(matrix4f, f8 + f7, f13, 0.0f).color(f3, f4, f5, f6).tex(this.u0, this.v1).lightmap(n).endVertex();
            iVertexBuilder.pos(matrix4f, f9 + f7, f13, 0.0f).color(f3, f4, f5, f6).tex(this.u1, this.v1).lightmap(n).endVertex();
            iVertexBuilder.pos(matrix4f, f9 + f14, f12, 0.0f).color(f3, f4, f5, f6).tex(this.u1, this.v0).lightmap(n).endVertex();
        }
    }

    public void renderEffect(Effect effect, Matrix4f matrix4f, IVertexBuilder iVertexBuilder, int n) {
        iVertexBuilder.pos(matrix4f, effect.x0, effect.y0, effect.depth).color(effect.r, effect.g, effect.b, effect.a).tex(this.u0, this.v0).lightmap(n).endVertex();
        iVertexBuilder.pos(matrix4f, effect.x1, effect.y0, effect.depth).color(effect.r, effect.g, effect.b, effect.a).tex(this.u0, this.v1).lightmap(n).endVertex();
        iVertexBuilder.pos(matrix4f, effect.x1, effect.y1, effect.depth).color(effect.r, effect.g, effect.b, effect.a).tex(this.u1, this.v1).lightmap(n).endVertex();
        iVertexBuilder.pos(matrix4f, effect.x0, effect.y1, effect.depth).color(effect.r, effect.g, effect.b, effect.a).tex(this.u1, this.v0).lightmap(n).endVertex();
    }

    public RenderType getRenderType(boolean bl) {
        return bl ? this.seeThroughType : this.normalType;
    }

    public static class Effect {
        protected final float x0;
        protected final float y0;
        protected final float x1;
        protected final float y1;
        protected final float depth;
        protected final float r;
        protected final float g;
        protected final float b;
        protected final float a;

        public Effect(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
            this.x0 = f;
            this.y0 = f2;
            this.x1 = f3;
            this.y1 = f4;
            this.depth = f5;
            this.r = f6;
            this.g = f7;
            this.b = f8;
            this.a = f9;
        }
    }
}

