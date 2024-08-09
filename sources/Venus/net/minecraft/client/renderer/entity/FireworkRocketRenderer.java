/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class FireworkRocketRenderer
extends EntityRenderer<FireworkRocketEntity> {
    private final ItemRenderer itemRenderer;

    public FireworkRocketRenderer(EntityRendererManager entityRendererManager, ItemRenderer itemRenderer) {
        super(entityRendererManager);
        this.itemRenderer = itemRenderer;
    }

    @Override
    public void render(FireworkRocketEntity fireworkRocketEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        matrixStack.push();
        matrixStack.rotate(this.renderManager.getCameraOrientation());
        matrixStack.rotate(Vector3f.YP.rotationDegrees(180.0f));
        if (fireworkRocketEntity.func_213889_i()) {
            matrixStack.rotate(Vector3f.ZP.rotationDegrees(180.0f));
            matrixStack.rotate(Vector3f.YP.rotationDegrees(180.0f));
            matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0f));
        }
        this.itemRenderer.renderItem(fireworkRocketEntity.getItem(), ItemCameraTransforms.TransformType.GROUND, n, OverlayTexture.NO_OVERLAY, matrixStack, iRenderTypeBuffer);
        matrixStack.pop();
        super.render(fireworkRocketEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    public ResourceLocation getEntityTexture(FireworkRocketEntity fireworkRocketEntity) {
        return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((FireworkRocketEntity)entity2);
    }

    @Override
    public void render(Entity entity2, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((FireworkRocketEntity)entity2, f, f2, matrixStack, iRenderTypeBuffer, n);
    }
}

