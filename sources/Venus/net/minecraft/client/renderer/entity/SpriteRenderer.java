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
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;

public class SpriteRenderer<T extends Entity>
extends EntityRenderer<T> {
    private final ItemRenderer itemRenderer;
    private final float scale;
    private final boolean field_229126_f_;

    public SpriteRenderer(EntityRendererManager entityRendererManager, ItemRenderer itemRenderer, float f, boolean bl) {
        super(entityRendererManager);
        this.itemRenderer = itemRenderer;
        this.scale = f;
        this.field_229126_f_ = bl;
    }

    public SpriteRenderer(EntityRendererManager entityRendererManager, ItemRenderer itemRenderer) {
        this(entityRendererManager, itemRenderer, 1.0f, false);
    }

    @Override
    protected int getBlockLight(T t, BlockPos blockPos) {
        return this.field_229126_f_ ? 15 : super.getBlockLight(t, blockPos);
    }

    @Override
    public void render(T t, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        if (((Entity)t).ticksExisted >= 2 || !(this.renderManager.info.getRenderViewEntity().getDistanceSq((Entity)t) < 12.25)) {
            matrixStack.push();
            matrixStack.scale(this.scale, this.scale, this.scale);
            matrixStack.rotate(this.renderManager.getCameraOrientation());
            matrixStack.rotate(Vector3f.YP.rotationDegrees(180.0f));
            this.itemRenderer.renderItem(((IRendersAsItem)t).getItem(), ItemCameraTransforms.TransformType.GROUND, n, OverlayTexture.NO_OVERLAY, matrixStack, iRenderTypeBuffer);
            matrixStack.pop();
            super.render(t, f, f2, matrixStack, iRenderTypeBuffer, n);
        }
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    }
}

