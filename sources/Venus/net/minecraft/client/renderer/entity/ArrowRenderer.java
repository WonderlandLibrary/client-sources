/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;

public abstract class ArrowRenderer<T extends AbstractArrowEntity>
extends EntityRenderer<T> {
    public ArrowRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager);
    }

    @Override
    public void render(T t, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        matrixStack.push();
        matrixStack.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(f2, ((AbstractArrowEntity)t).prevRotationYaw, ((AbstractArrowEntity)t).rotationYaw) - 90.0f));
        matrixStack.rotate(Vector3f.ZP.rotationDegrees(MathHelper.lerp(f2, ((AbstractArrowEntity)t).prevRotationPitch, ((AbstractArrowEntity)t).rotationPitch)));
        boolean bl = false;
        float f3 = 0.0f;
        float f4 = 0.5f;
        float f5 = 0.0f;
        float f6 = 0.15625f;
        float f7 = 0.0f;
        float f8 = 0.15625f;
        float f9 = 0.15625f;
        float f10 = 0.3125f;
        float f11 = 0.05625f;
        float f12 = (float)((AbstractArrowEntity)t).arrowShake - f2;
        if (f12 > 0.0f) {
            float f13 = -MathHelper.sin(f12 * 3.0f) * f12;
            matrixStack.rotate(Vector3f.ZP.rotationDegrees(f13));
        }
        matrixStack.rotate(Vector3f.XP.rotationDegrees(45.0f));
        matrixStack.scale(0.05625f, 0.05625f, 0.05625f);
        matrixStack.translate(-4.0, 0.0, 0.0);
        IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(RenderType.getEntityCutout(this.getEntityTexture(t)));
        MatrixStack.Entry entry = matrixStack.getLast();
        Matrix4f matrix4f = entry.getMatrix();
        Matrix3f matrix3f = entry.getNormal();
        this.drawVertex(matrix4f, matrix3f, iVertexBuilder, -7, -2, -2, 0.0f, 0.15625f, -1, 0, 0, n);
        this.drawVertex(matrix4f, matrix3f, iVertexBuilder, -7, -2, 2, 0.15625f, 0.15625f, -1, 0, 0, n);
        this.drawVertex(matrix4f, matrix3f, iVertexBuilder, -7, 2, 2, 0.15625f, 0.3125f, -1, 0, 0, n);
        this.drawVertex(matrix4f, matrix3f, iVertexBuilder, -7, 2, -2, 0.0f, 0.3125f, -1, 0, 0, n);
        this.drawVertex(matrix4f, matrix3f, iVertexBuilder, -7, 2, -2, 0.0f, 0.15625f, 1, 0, 0, n);
        this.drawVertex(matrix4f, matrix3f, iVertexBuilder, -7, 2, 2, 0.15625f, 0.15625f, 1, 0, 0, n);
        this.drawVertex(matrix4f, matrix3f, iVertexBuilder, -7, -2, 2, 0.15625f, 0.3125f, 1, 0, 0, n);
        this.drawVertex(matrix4f, matrix3f, iVertexBuilder, -7, -2, -2, 0.0f, 0.3125f, 1, 0, 0, n);
        for (int i = 0; i < 4; ++i) {
            matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0f));
            this.drawVertex(matrix4f, matrix3f, iVertexBuilder, -8, -2, 0, 0.0f, 0.0f, 0, 1, 0, n);
            this.drawVertex(matrix4f, matrix3f, iVertexBuilder, 8, -2, 0, 0.5f, 0.0f, 0, 1, 0, n);
            this.drawVertex(matrix4f, matrix3f, iVertexBuilder, 8, 2, 0, 0.5f, 0.15625f, 0, 1, 0, n);
            this.drawVertex(matrix4f, matrix3f, iVertexBuilder, -8, 2, 0, 0.0f, 0.15625f, 0, 1, 0, n);
        }
        matrixStack.pop();
        super.render(t, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    public void drawVertex(Matrix4f matrix4f, Matrix3f matrix3f, IVertexBuilder iVertexBuilder, int n, int n2, int n3, float f, float f2, int n4, int n5, int n6, int n7) {
        iVertexBuilder.pos(matrix4f, n, n2, n3).color(255, 255, 255, 255).tex(f, f2).overlay(OverlayTexture.NO_OVERLAY).lightmap(n7).normal(matrix3f, n4, n6, n5).endVertex();
    }

    @Override
    public void render(Entity entity2, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((T)((AbstractArrowEntity)entity2), f, f2, matrixStack, iRenderTypeBuffer, n);
    }
}

