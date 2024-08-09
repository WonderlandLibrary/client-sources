/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;

public class ModelSprite {
    private ModelRenderer modelRenderer = null;
    private int textureOffsetX = 0;
    private int textureOffsetY = 0;
    private float posX = 0.0f;
    private float posY = 0.0f;
    private float posZ = 0.0f;
    private int sizeX = 0;
    private int sizeY = 0;
    private int sizeZ = 0;
    private float sizeAdd = 0.0f;
    private float minU = 0.0f;
    private float minV = 0.0f;
    private float maxU = 0.0f;
    private float maxV = 0.0f;

    public ModelSprite(ModelRenderer modelRenderer, int n, int n2, float f, float f2, float f3, int n3, int n4, int n5, float f4) {
        this.modelRenderer = modelRenderer;
        this.textureOffsetX = n;
        this.textureOffsetY = n2;
        this.posX = f;
        this.posY = f2;
        this.posZ = f3;
        this.sizeX = n3;
        this.sizeY = n4;
        this.sizeZ = n5;
        this.sizeAdd = f4;
        this.minU = (float)n / modelRenderer.textureWidth;
        this.minV = (float)n2 / modelRenderer.textureHeight;
        this.maxU = (float)(n + n3) / modelRenderer.textureWidth;
        this.maxV = (float)(n2 + n4) / modelRenderer.textureHeight;
    }

    public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2, float f, float f2, float f3, float f4) {
        float f5 = 0.0625f;
        matrixStack.translate(this.posX * f5, this.posY * f5, this.posZ * f5);
        float f6 = this.minU;
        float f7 = this.maxU;
        float f8 = this.minV;
        float f9 = this.maxV;
        if (this.modelRenderer.mirror) {
            f6 = this.maxU;
            f7 = this.minU;
        }
        if (this.modelRenderer.mirrorV) {
            f8 = this.maxV;
            f9 = this.minV;
        }
        ModelSprite.renderItemIn2D(matrixStack, iVertexBuilder, f6, f8, f7, f9, this.sizeX, this.sizeY, f5 * (float)this.sizeZ, this.modelRenderer.textureWidth, this.modelRenderer.textureHeight, n, n2, f, f2, f3, f4);
        matrixStack.translate(-this.posX * f5, -this.posY * f5, -this.posZ * f5);
    }

    public static void renderItemIn2D(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, float f, float f2, float f3, float f4, int n, int n2, float f5, float f6, float f7, int n3, int n4, float f8, float f9, float f10, float f11) {
        float f12;
        float f13;
        float f14;
        int n5;
        if (f5 < 6.25E-4f) {
            f5 = 6.25E-4f;
        }
        float f15 = f3 - f;
        float f16 = f4 - f2;
        float f17 = MathHelper.abs(f15) * (f6 / 16.0f);
        float f18 = MathHelper.abs(f16) * (f7 / 16.0f);
        float f19 = 0.0f;
        float f20 = 0.0f;
        float f21 = -1.0f;
        ModelSprite.addVertex(matrixStack, iVertexBuilder, 0.0f, f18, 0.0f, f8, f9, f10, f11, f, f4, n4, n3, f19, f20, f21);
        ModelSprite.addVertex(matrixStack, iVertexBuilder, f17, f18, 0.0f, f8, f9, f10, f11, f3, f4, n4, n3, f19, f20, f21);
        ModelSprite.addVertex(matrixStack, iVertexBuilder, f17, 0.0f, 0.0f, f8, f9, f10, f11, f3, f2, n4, n3, f19, f20, f21);
        ModelSprite.addVertex(matrixStack, iVertexBuilder, 0.0f, 0.0f, 0.0f, f8, f9, f10, f11, f, f2, n4, n3, f19, f20, f21);
        f19 = 0.0f;
        f20 = 0.0f;
        f21 = 1.0f;
        ModelSprite.addVertex(matrixStack, iVertexBuilder, 0.0f, 0.0f, f5, f8, f9, f10, f11, f, f2, n4, n3, f19, f20, f21);
        ModelSprite.addVertex(matrixStack, iVertexBuilder, f17, 0.0f, f5, f8, f9, f10, f11, f3, f2, n4, n3, f19, f20, f21);
        ModelSprite.addVertex(matrixStack, iVertexBuilder, f17, f18, f5, f8, f9, f10, f11, f3, f4, n4, n3, f19, f20, f21);
        ModelSprite.addVertex(matrixStack, iVertexBuilder, 0.0f, f18, f5, f8, f9, f10, f11, f, f4, n4, n3, f19, f20, f21);
        float f22 = 0.5f * f15 / (float)n;
        float f23 = 0.5f * f16 / (float)n2;
        f19 = -1.0f;
        f20 = 0.0f;
        f21 = 0.0f;
        for (n5 = 0; n5 < n; ++n5) {
            f14 = (float)n5 / (float)n;
            f13 = f + f15 * f14 + f22;
            ModelSprite.addVertex(matrixStack, iVertexBuilder, f14 * f17, f18, f5, f8, f9, f10, f11, f13, f4, n4, n3, f19, f20, f21);
            ModelSprite.addVertex(matrixStack, iVertexBuilder, f14 * f17, f18, 0.0f, f8, f9, f10, f11, f13, f4, n4, n3, f19, f20, f21);
            ModelSprite.addVertex(matrixStack, iVertexBuilder, f14 * f17, 0.0f, 0.0f, f8, f9, f10, f11, f13, f2, n4, n3, f19, f20, f21);
            ModelSprite.addVertex(matrixStack, iVertexBuilder, f14 * f17, 0.0f, f5, f8, f9, f10, f11, f13, f2, n4, n3, f19, f20, f21);
        }
        f19 = 1.0f;
        f20 = 0.0f;
        f21 = 0.0f;
        for (n5 = 0; n5 < n; ++n5) {
            f14 = (float)n5 / (float)n;
            f13 = f + f15 * f14 + f22;
            f12 = f14 + 1.0f / (float)n;
            ModelSprite.addVertex(matrixStack, iVertexBuilder, f12 * f17, 0.0f, f5, f8, f9, f10, f11, f13, f2, n4, n3, f19, f20, f21);
            ModelSprite.addVertex(matrixStack, iVertexBuilder, f12 * f17, 0.0f, 0.0f, f8, f9, f10, f11, f13, f2, n4, n3, f19, f20, f21);
            ModelSprite.addVertex(matrixStack, iVertexBuilder, f12 * f17, f18, 0.0f, f8, f9, f10, f11, f13, f4, n4, n3, f19, f20, f21);
            ModelSprite.addVertex(matrixStack, iVertexBuilder, f12 * f17, f18, f5, f8, f9, f10, f11, f13, f4, n4, n3, f19, f20, f21);
        }
        f19 = 0.0f;
        f20 = 1.0f;
        f21 = 0.0f;
        for (n5 = 0; n5 < n2; ++n5) {
            f14 = (float)n5 / (float)n2;
            f13 = f2 + f16 * f14 + f23;
            f12 = f14 + 1.0f / (float)n2;
            ModelSprite.addVertex(matrixStack, iVertexBuilder, 0.0f, f12 * f18, f5, f8, f9, f10, f11, f, f13, n4, n3, f19, f20, f21);
            ModelSprite.addVertex(matrixStack, iVertexBuilder, f17, f12 * f18, f5, f8, f9, f10, f11, f3, f13, n4, n3, f19, f20, f21);
            ModelSprite.addVertex(matrixStack, iVertexBuilder, f17, f12 * f18, 0.0f, f8, f9, f10, f11, f3, f13, n4, n3, f19, f20, f21);
            ModelSprite.addVertex(matrixStack, iVertexBuilder, 0.0f, f12 * f18, 0.0f, f8, f9, f10, f11, f, f13, n4, n3, f19, f20, f21);
        }
        f19 = 0.0f;
        f20 = -1.0f;
        f21 = 0.0f;
        for (n5 = 0; n5 < n2; ++n5) {
            f14 = (float)n5 / (float)n2;
            f13 = f2 + f16 * f14 + f23;
            ModelSprite.addVertex(matrixStack, iVertexBuilder, f17, f14 * f18, f5, f8, f9, f10, f11, f3, f13, n4, n3, f19, f20, f21);
            ModelSprite.addVertex(matrixStack, iVertexBuilder, 0.0f, f14 * f18, f5, f8, f9, f10, f11, f, f13, n4, n3, f19, f20, f21);
            ModelSprite.addVertex(matrixStack, iVertexBuilder, 0.0f, f14 * f18, 0.0f, f8, f9, f10, f11, f, f13, n4, n3, f19, f20, f21);
            ModelSprite.addVertex(matrixStack, iVertexBuilder, f17, f14 * f18, 0.0f, f8, f9, f10, f11, f3, f13, n4, n3, f19, f20, f21);
        }
    }

    static void addVertex(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, int n, int n2, float f10, float f11, float f12) {
        MatrixStack.Entry entry = matrixStack.getLast();
        Matrix4f matrix4f = entry.getMatrix();
        Matrix3f matrix3f = entry.getNormal();
        float f13 = matrix3f.getTransformX(f10, f11, f12);
        float f14 = matrix3f.getTransformY(f10, f11, f12);
        float f15 = matrix3f.getTransformZ(f10, f11, f12);
        float f16 = matrix4f.getTransformX(f, f2, f3, 1.0f);
        float f17 = matrix4f.getTransformY(f, f2, f3, 1.0f);
        float f18 = matrix4f.getTransformZ(f, f2, f3, 1.0f);
        iVertexBuilder.addVertex(f16, f17, f18, f4, f5, f6, f7, f8, f9, n, n2, f13, f14, f15);
    }
}

