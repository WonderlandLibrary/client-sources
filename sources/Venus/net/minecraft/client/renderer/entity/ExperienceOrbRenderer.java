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
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.optifine.Config;
import net.optifine.CustomColors;

public class ExperienceOrbRenderer
extends EntityRenderer<ExperienceOrbEntity> {
    private static final ResourceLocation EXPERIENCE_ORB_TEXTURES = new ResourceLocation("textures/entity/experience_orb.png");
    private static final RenderType RENDER_TYPE = RenderType.getItemEntityTranslucentCull(EXPERIENCE_ORB_TEXTURES);

    public ExperienceOrbRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager);
        this.shadowSize = 0.15f;
        this.shadowOpaque = 0.75f;
    }

    @Override
    protected int getBlockLight(ExperienceOrbEntity experienceOrbEntity, BlockPos blockPos) {
        return MathHelper.clamp(super.getBlockLight(experienceOrbEntity, blockPos) + 7, 0, 15);
    }

    @Override
    public void render(ExperienceOrbEntity experienceOrbEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        int n2;
        matrixStack.push();
        int n3 = experienceOrbEntity.getTextureByXP();
        float f3 = (float)(n3 % 4 * 16 + 0) / 64.0f;
        float f4 = (float)(n3 % 4 * 16 + 16) / 64.0f;
        float f5 = (float)(n3 / 4 * 16 + 0) / 64.0f;
        float f6 = (float)(n3 / 4 * 16 + 16) / 64.0f;
        float f7 = 1.0f;
        float f8 = 0.5f;
        float f9 = 0.25f;
        float f10 = 255.0f;
        float f11 = ((float)experienceOrbEntity.xpColor + f2) / 2.0f;
        if (Config.isCustomColors()) {
            f11 = CustomColors.getXpOrbTimer(f11);
        }
        int n4 = (int)((MathHelper.sin(f11 + 0.0f) + 1.0f) * 0.5f * 255.0f);
        int n5 = 255;
        int n6 = (int)((MathHelper.sin(f11 + 4.1887903f) + 1.0f) * 0.1f * 255.0f);
        matrixStack.translate(0.0, 0.1f, 0.0);
        matrixStack.rotate(this.renderManager.getCameraOrientation());
        matrixStack.rotate(Vector3f.YP.rotationDegrees(180.0f));
        float f12 = 0.3f;
        matrixStack.scale(0.3f, 0.3f, 0.3f);
        IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(RENDER_TYPE);
        MatrixStack.Entry entry = matrixStack.getLast();
        Matrix4f matrix4f = entry.getMatrix();
        Matrix3f matrix3f = entry.getNormal();
        int n7 = n4;
        int n8 = 255;
        int n9 = n6;
        if (Config.isCustomColors() && (n2 = CustomColors.getXpOrbColor(f11)) >= 0) {
            n7 = n2 >> 16 & 0xFF;
            n8 = n2 >> 8 & 0xFF;
            n9 = n2 >> 0 & 0xFF;
        }
        ExperienceOrbRenderer.vertex(iVertexBuilder, matrix4f, matrix3f, -0.5f, -0.25f, n7, n8, n9, f3, f6, n);
        ExperienceOrbRenderer.vertex(iVertexBuilder, matrix4f, matrix3f, 0.5f, -0.25f, n7, n8, n9, f4, f6, n);
        ExperienceOrbRenderer.vertex(iVertexBuilder, matrix4f, matrix3f, 0.5f, 0.75f, n7, n8, n9, f4, f5, n);
        ExperienceOrbRenderer.vertex(iVertexBuilder, matrix4f, matrix3f, -0.5f, 0.75f, n7, n8, n9, f3, f5, n);
        matrixStack.pop();
        super.render(experienceOrbEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    private static void vertex(IVertexBuilder iVertexBuilder, Matrix4f matrix4f, Matrix3f matrix3f, float f, float f2, int n, int n2, int n3, float f3, float f4, int n4) {
        iVertexBuilder.pos(matrix4f, f, f2, 0.0f).color(n, n2, n3, 128).tex(f3, f4).overlay(OverlayTexture.NO_OVERLAY).lightmap(n4).normal(matrix3f, 0.0f, 1.0f, 0.0f).endVertex();
    }

    @Override
    public ResourceLocation getEntityTexture(ExperienceOrbEntity experienceOrbEntity) {
        return EXPERIENCE_ORB_TEXTURES;
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((ExperienceOrbEntity)entity2);
    }

    @Override
    public void render(Entity entity2, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((ExperienceOrbEntity)entity2, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    protected int getBlockLight(Entity entity2, BlockPos blockPos) {
        return this.getBlockLight((ExperienceOrbEntity)entity2, blockPos);
    }
}

