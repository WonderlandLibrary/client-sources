/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.Random;
import mpp.venusfr.functions.api.FunctionRegistry;
import mpp.venusfr.functions.impl.render.ItemPhysic;
import mpp.venusfr.venusfr;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class ItemRenderer
extends EntityRenderer<ItemEntity> {
    private final net.minecraft.client.renderer.ItemRenderer itemRenderer;
    private final Random random = new Random();

    public ItemRenderer(EntityRendererManager entityRendererManager, net.minecraft.client.renderer.ItemRenderer itemRenderer) {
        super(entityRendererManager);
        this.itemRenderer = itemRenderer;
        this.shadowSize = 0.15f;
        this.shadowOpaque = 0.75f;
    }

    private int getModelCount(ItemStack itemStack) {
        int n = 1;
        if (itemStack.getCount() > 48) {
            n = 5;
        } else if (itemStack.getCount() > 32) {
            n = 4;
        } else if (itemStack.getCount() > 16) {
            n = 3;
        } else if (itemStack.getCount() > 1) {
            n = 2;
        }
        return n;
    }

    @Override
    public void render(ItemEntity itemEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        float f3;
        float f4;
        matrixStack.push();
        ItemStack itemStack = itemEntity.getItem();
        int n2 = itemStack.isEmpty() ? 187 : Item.getIdFromItem(itemStack.getItem()) + itemStack.getDamage();
        this.random.setSeed(n2);
        IBakedModel iBakedModel = this.itemRenderer.getItemModelWithOverrides(itemStack, itemEntity.world, null);
        boolean bl = iBakedModel.isGui3d();
        int n3 = this.getModelCount(itemStack);
        float f5 = 0.25f;
        float f6 = MathHelper.sin(((float)itemEntity.getAge() + f2) / 10.0f + itemEntity.hoverStart) * 0.1f + 0.1f;
        if (!this.shouldBob()) {
            f6 = 0.0f;
        }
        FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
        ItemPhysic itemPhysic = functionRegistry.getItemPhysic();
        boolean bl2 = itemPhysic.isState();
        float f7 = iBakedModel.getItemCameraTransforms().getTransform((ItemCameraTransforms.TransformType)ItemCameraTransforms.TransformType.GROUND).scale.getY();
        if (!bl2) {
            matrixStack.translate(0.0, f6 + 0.25f * f7, 0.0);
        }
        float f8 = itemEntity.getItemHover(f2);
        if (!bl2) {
            matrixStack.rotate(Vector3f.YP.rotation(f8));
        }
        if (bl2) {
            matrixStack.rotate(Vector3f.XP.rotationDegrees(itemEntity.isOnGround() ? 90.0f : f8 * 300.0f));
        }
        float f9 = iBakedModel.getItemCameraTransforms().ground.scale.getX();
        float f10 = iBakedModel.getItemCameraTransforms().ground.scale.getY();
        float f11 = iBakedModel.getItemCameraTransforms().ground.scale.getZ();
        if (!bl) {
            float f12 = -0.0f * (float)(n3 - 1) * 0.5f * f9;
            f4 = -0.0f * (float)(n3 - 1) * 0.5f * f10;
            f3 = -0.09375f * (float)(n3 - 1) * 0.5f * f11;
            matrixStack.translate(f12, f4, f3);
        }
        for (int i = 0; i < n3; ++i) {
            matrixStack.push();
            if (i > 0) {
                if (bl) {
                    f4 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.15f;
                    f3 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.15f;
                    float f13 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.15f;
                    if (!this.shouldSpreadItems()) {
                        f4 = 0.0f;
                        f3 = 0.0f;
                    }
                    matrixStack.translate(f4, f3, f13);
                } else {
                    f4 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.15f * 0.5f;
                    f3 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.15f * 0.5f;
                    matrixStack.translate(f4, f3, 0.0);
                }
            }
            this.itemRenderer.renderItem(itemStack, ItemCameraTransforms.TransformType.GROUND, false, matrixStack, iRenderTypeBuffer, n, OverlayTexture.NO_OVERLAY, iBakedModel);
            matrixStack.pop();
            if (bl) continue;
            matrixStack.translate(0.0f * f9, 0.0f * f10, 0.09375f * f11);
        }
        matrixStack.pop();
        super.render(itemEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    public ResourceLocation getEntityTexture(ItemEntity itemEntity) {
        return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    }

    public boolean shouldSpreadItems() {
        return false;
    }

    public boolean shouldBob() {
        return false;
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((ItemEntity)entity2);
    }

    @Override
    public void render(Entity entity2, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((ItemEntity)entity2, f, f2, matrixStack, iRenderTypeBuffer, n);
    }
}

