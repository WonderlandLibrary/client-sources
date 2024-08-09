/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.GenericHeadModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class WitherSkullRenderer
extends EntityRenderer<WitherSkullEntity> {
    private static final ResourceLocation INVULNERABLE_WITHER_TEXTURES = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
    private static final ResourceLocation WITHER_TEXTURES = new ResourceLocation("textures/entity/wither/wither.png");
    private final GenericHeadModel skeletonHeadModel = new GenericHeadModel();

    public WitherSkullRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager);
    }

    @Override
    protected int getBlockLight(WitherSkullEntity witherSkullEntity, BlockPos blockPos) {
        return 0;
    }

    @Override
    public void render(WitherSkullEntity witherSkullEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        matrixStack.push();
        matrixStack.scale(-1.0f, -1.0f, 1.0f);
        float f3 = MathHelper.rotLerp(witherSkullEntity.prevRotationYaw, witherSkullEntity.rotationYaw, f2);
        float f4 = MathHelper.lerp(f2, witherSkullEntity.prevRotationPitch, witherSkullEntity.rotationPitch);
        IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(this.skeletonHeadModel.getRenderType(this.getEntityTexture(witherSkullEntity)));
        this.skeletonHeadModel.func_225603_a_(0.0f, f3, f4);
        this.skeletonHeadModel.render(matrixStack, iVertexBuilder, n, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack.pop();
        super.render(witherSkullEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    public ResourceLocation getEntityTexture(WitherSkullEntity witherSkullEntity) {
        return witherSkullEntity.isSkullInvulnerable() ? INVULNERABLE_WITHER_TEXTURES : WITHER_TEXTURES;
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((WitherSkullEntity)entity2);
    }

    @Override
    public void render(Entity entity2, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((WitherSkullEntity)entity2, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    protected int getBlockLight(Entity entity2, BlockPos blockPos) {
        return this.getBlockLight((WitherSkullEntity)entity2, blockPos);
    }
}

