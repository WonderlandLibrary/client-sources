/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.LlamaSpitModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.LlamaSpitEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class LlamaSpitRenderer
extends EntityRenderer<LlamaSpitEntity> {
    private static final ResourceLocation LLAMA_SPIT_TEXTURE = new ResourceLocation("textures/entity/llama/spit.png");
    private final LlamaSpitModel<LlamaSpitEntity> model = new LlamaSpitModel();

    public LlamaSpitRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager);
    }

    @Override
    public void render(LlamaSpitEntity llamaSpitEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        matrixStack.push();
        matrixStack.translate(0.0, 0.15f, 0.0);
        matrixStack.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(f2, llamaSpitEntity.prevRotationYaw, llamaSpitEntity.rotationYaw) - 90.0f));
        matrixStack.rotate(Vector3f.ZP.rotationDegrees(MathHelper.lerp(f2, llamaSpitEntity.prevRotationPitch, llamaSpitEntity.rotationPitch)));
        this.model.setRotationAngles(llamaSpitEntity, f2, 0.0f, -0.1f, 0.0f, 0.0f);
        IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(this.model.getRenderType(LLAMA_SPIT_TEXTURE));
        this.model.render(matrixStack, iVertexBuilder, n, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack.pop();
        super.render(llamaSpitEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    public ResourceLocation getEntityTexture(LlamaSpitEntity llamaSpitEntity) {
        return LLAMA_SPIT_TEXTURE;
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((LlamaSpitEntity)entity2);
    }

    @Override
    public void render(Entity entity2, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((LlamaSpitEntity)entity2, f, f2, matrixStack, iRenderTypeBuffer, n);
    }
}

