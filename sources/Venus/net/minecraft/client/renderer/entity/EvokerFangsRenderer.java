/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EvokerFangsModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EvokerFangsEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class EvokerFangsRenderer
extends EntityRenderer<EvokerFangsEntity> {
    private static final ResourceLocation EVOKER_ILLAGER_FANGS = new ResourceLocation("textures/entity/illager/evoker_fangs.png");
    private final EvokerFangsModel<EvokerFangsEntity> model = new EvokerFangsModel();

    public EvokerFangsRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager);
    }

    @Override
    public void render(EvokerFangsEntity evokerFangsEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        float f3 = evokerFangsEntity.getAnimationProgress(f2);
        if (f3 != 0.0f) {
            float f4 = 2.0f;
            if (f3 > 0.9f) {
                f4 = (float)((double)f4 * ((1.0 - (double)f3) / (double)0.1f));
            }
            matrixStack.push();
            matrixStack.rotate(Vector3f.YP.rotationDegrees(90.0f - evokerFangsEntity.rotationYaw));
            matrixStack.scale(-f4, -f4, f4);
            float f5 = 0.03125f;
            matrixStack.translate(0.0, -0.626f, 0.0);
            matrixStack.scale(0.5f, 0.5f, 0.5f);
            this.model.setRotationAngles(evokerFangsEntity, f3, 0.0f, 0.0f, evokerFangsEntity.rotationYaw, evokerFangsEntity.rotationPitch);
            IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(this.model.getRenderType(EVOKER_ILLAGER_FANGS));
            this.model.render(matrixStack, iVertexBuilder, n, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
            matrixStack.pop();
            super.render(evokerFangsEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
        }
    }

    @Override
    public ResourceLocation getEntityTexture(EvokerFangsEntity evokerFangsEntity) {
        return EVOKER_ILLAGER_FANGS;
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((EvokerFangsEntity)entity2);
    }

    @Override
    public void render(Entity entity2, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((EvokerFangsEntity)entity2, f, f2, matrixStack, iRenderTypeBuffer, n);
    }
}

