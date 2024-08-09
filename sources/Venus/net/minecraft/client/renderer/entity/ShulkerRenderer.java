/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ShulkerColorLayer;
import net.minecraft.client.renderer.entity.model.ShulkerModel;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.ShulkerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class ShulkerRenderer
extends MobRenderer<ShulkerEntity, ShulkerModel<ShulkerEntity>> {
    public static final ResourceLocation field_204402_a = new ResourceLocation("textures/" + Atlases.DEFAULT_SHULKER_TEXTURE.getTextureLocation().getPath() + ".png");
    public static final ResourceLocation[] SHULKER_ENDERGOLEM_TEXTURE = (ResourceLocation[])Atlases.SHULKER_TEXTURES.stream().map(ShulkerRenderer::lambda$static$0).toArray(ShulkerRenderer::lambda$static$1);

    public ShulkerRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new ShulkerModel(), 0.0f);
        this.addLayer(new ShulkerColorLayer(this));
    }

    @Override
    public Vector3d getRenderOffset(ShulkerEntity shulkerEntity, float f) {
        int n = shulkerEntity.getClientTeleportInterp();
        if (n > 0 && shulkerEntity.isAttachedToBlock()) {
            BlockPos blockPos = shulkerEntity.getAttachmentPos();
            BlockPos blockPos2 = shulkerEntity.getOldAttachPos();
            double d = (double)((float)n - f) / 6.0;
            d *= d;
            double d2 = (double)(blockPos.getX() - blockPos2.getX()) * d;
            double d3 = (double)(blockPos.getY() - blockPos2.getY()) * d;
            double d4 = (double)(blockPos.getZ() - blockPos2.getZ()) * d;
            return new Vector3d(-d2, -d3, -d4);
        }
        return super.getRenderOffset(shulkerEntity, f);
    }

    @Override
    public boolean shouldRender(ShulkerEntity shulkerEntity, ClippingHelper clippingHelper, double d, double d2, double d3) {
        if (super.shouldRender(shulkerEntity, clippingHelper, d, d2, d3)) {
            return false;
        }
        if (shulkerEntity.getClientTeleportInterp() > 0 && shulkerEntity.isAttachedToBlock()) {
            Vector3d vector3d = Vector3d.copy(shulkerEntity.getAttachmentPos());
            Vector3d vector3d2 = Vector3d.copy(shulkerEntity.getOldAttachPos());
            if (clippingHelper.isBoundingBoxInFrustum(new AxisAlignedBB(vector3d2.x, vector3d2.y, vector3d2.z, vector3d.x, vector3d.y, vector3d.z))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ResourceLocation getEntityTexture(ShulkerEntity shulkerEntity) {
        return shulkerEntity.getColor() == null ? field_204402_a : SHULKER_ENDERGOLEM_TEXTURE[shulkerEntity.getColor().getId()];
    }

    @Override
    protected void applyRotations(ShulkerEntity shulkerEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        super.applyRotations(shulkerEntity, matrixStack, f, f2 + 180.0f, f3);
        matrixStack.translate(0.0, 0.5, 0.0);
        matrixStack.rotate(shulkerEntity.getAttachmentFacing().getOpposite().getRotation());
        matrixStack.translate(0.0, -0.5, 0.0);
    }

    @Override
    public boolean shouldRender(MobEntity mobEntity, ClippingHelper clippingHelper, double d, double d2, double d3) {
        return this.shouldRender((ShulkerEntity)mobEntity, clippingHelper, d, d2, d3);
    }

    @Override
    protected void applyRotations(LivingEntity livingEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        this.applyRotations((ShulkerEntity)livingEntity, matrixStack, f, f2, f3);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((ShulkerEntity)entity2);
    }

    @Override
    public Vector3d getRenderOffset(Entity entity2, float f) {
        return this.getRenderOffset((ShulkerEntity)entity2, f);
    }

    @Override
    public boolean shouldRender(Entity entity2, ClippingHelper clippingHelper, double d, double d2, double d3) {
        return this.shouldRender((ShulkerEntity)entity2, clippingHelper, d, d2, d3);
    }

    private static ResourceLocation[] lambda$static$1(int n) {
        return new ResourceLocation[n];
    }

    private static ResourceLocation lambda$static$0(RenderMaterial renderMaterial) {
        return new ResourceLocation("textures/" + renderMaterial.getTextureLocation().getPath() + ".png");
    }
}

