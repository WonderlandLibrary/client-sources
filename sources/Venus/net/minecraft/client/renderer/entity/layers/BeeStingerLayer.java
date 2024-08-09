/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.StuckInBodyLayer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;

public class BeeStingerLayer<T extends LivingEntity, M extends PlayerModel<T>>
extends StuckInBodyLayer<T, M> {
    private static final ResourceLocation field_229131_a_ = new ResourceLocation("textures/entity/bee/bee_stinger.png");

    public BeeStingerLayer(LivingRenderer<T, M> livingRenderer) {
        super(livingRenderer);
    }

    @Override
    protected int func_225631_a_(T t) {
        return ((LivingEntity)t).getBeeStingCount();
    }

    @Override
    protected void func_225632_a_(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4) {
        float f5 = MathHelper.sqrt(f * f + f3 * f3);
        float f6 = (float)(Math.atan2(f, f3) * 57.2957763671875);
        float f7 = (float)(Math.atan2(f2, f5) * 57.2957763671875);
        matrixStack.translate(0.0, 0.0, 0.0);
        matrixStack.rotate(Vector3f.YP.rotationDegrees(f6 - 90.0f));
        matrixStack.rotate(Vector3f.ZP.rotationDegrees(f7));
        float f8 = 0.0f;
        float f9 = 0.125f;
        float f10 = 0.0f;
        float f11 = 0.0625f;
        float f12 = 0.03125f;
        matrixStack.rotate(Vector3f.XP.rotationDegrees(45.0f));
        matrixStack.scale(0.03125f, 0.03125f, 0.03125f);
        matrixStack.translate(2.5, 0.0, 0.0);
        IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(RenderType.getEntityCutoutNoCull(field_229131_a_));
        for (int i = 0; i < 4; ++i) {
            matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0f));
            MatrixStack.Entry entry = matrixStack.getLast();
            Matrix4f matrix4f = entry.getMatrix();
            Matrix3f matrix3f = entry.getNormal();
            BeeStingerLayer.func_229132_a_(iVertexBuilder, matrix4f, matrix3f, -4.5f, -1, 0.0f, 0.0f, n);
            BeeStingerLayer.func_229132_a_(iVertexBuilder, matrix4f, matrix3f, 4.5f, -1, 0.125f, 0.0f, n);
            BeeStingerLayer.func_229132_a_(iVertexBuilder, matrix4f, matrix3f, 4.5f, 1, 0.125f, 0.0625f, n);
            BeeStingerLayer.func_229132_a_(iVertexBuilder, matrix4f, matrix3f, -4.5f, 1, 0.0f, 0.0625f, n);
        }
    }

    private static void func_229132_a_(IVertexBuilder iVertexBuilder, Matrix4f matrix4f, Matrix3f matrix3f, float f, int n, float f2, float f3, int n2) {
        iVertexBuilder.pos(matrix4f, f, n, 0.0f).color(255, 255, 255, 255).tex(f2, f3).overlay(OverlayTexture.NO_OVERLAY).lightmap(n2).normal(matrix3f, 0.0f, 1.0f, 0.0f).endVertex();
    }
}

