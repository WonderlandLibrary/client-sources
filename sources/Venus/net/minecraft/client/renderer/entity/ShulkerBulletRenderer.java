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
import net.minecraft.client.renderer.entity.model.ShulkerBulletModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class ShulkerBulletRenderer
extends EntityRenderer<ShulkerBulletEntity> {
    private static final ResourceLocation SHULKER_SPARK_TEXTURE = new ResourceLocation("textures/entity/shulker/spark.png");
    private static final RenderType field_229123_e_ = RenderType.getEntityTranslucent(SHULKER_SPARK_TEXTURE);
    private final ShulkerBulletModel<ShulkerBulletEntity> model = new ShulkerBulletModel();

    public ShulkerBulletRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager);
    }

    @Override
    protected int getBlockLight(ShulkerBulletEntity shulkerBulletEntity, BlockPos blockPos) {
        return 0;
    }

    @Override
    public void render(ShulkerBulletEntity shulkerBulletEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        matrixStack.push();
        float f3 = MathHelper.rotLerp(shulkerBulletEntity.prevRotationYaw, shulkerBulletEntity.rotationYaw, f2);
        float f4 = MathHelper.lerp(f2, shulkerBulletEntity.prevRotationPitch, shulkerBulletEntity.rotationPitch);
        float f5 = (float)shulkerBulletEntity.ticksExisted + f2;
        matrixStack.translate(0.0, 0.15f, 0.0);
        matrixStack.rotate(Vector3f.YP.rotationDegrees(MathHelper.sin(f5 * 0.1f) * 180.0f));
        matrixStack.rotate(Vector3f.XP.rotationDegrees(MathHelper.cos(f5 * 0.1f) * 180.0f));
        matrixStack.rotate(Vector3f.ZP.rotationDegrees(MathHelper.sin(f5 * 0.15f) * 360.0f));
        matrixStack.scale(-0.5f, -0.5f, 0.5f);
        this.model.setRotationAngles(shulkerBulletEntity, 0.0f, 0.0f, 0.0f, f3, f4);
        IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(this.model.getRenderType(SHULKER_SPARK_TEXTURE));
        this.model.render(matrixStack, iVertexBuilder, n, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack.scale(1.5f, 1.5f, 1.5f);
        IVertexBuilder iVertexBuilder2 = iRenderTypeBuffer.getBuffer(field_229123_e_);
        this.model.render(matrixStack, iVertexBuilder2, n, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 0.15f);
        matrixStack.pop();
        super.render(shulkerBulletEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    public ResourceLocation getEntityTexture(ShulkerBulletEntity shulkerBulletEntity) {
        return SHULKER_SPARK_TEXTURE;
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((ShulkerBulletEntity)entity2);
    }

    @Override
    public void render(Entity entity2, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((ShulkerBulletEntity)entity2, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    protected int getBlockLight(Entity entity2, BlockPos blockPos) {
        return this.getBlockLight((ShulkerBulletEntity)entity2, blockPos);
    }
}

