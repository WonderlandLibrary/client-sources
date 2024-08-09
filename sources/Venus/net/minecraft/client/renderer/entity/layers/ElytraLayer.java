/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.ElytraModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;
import net.optifine.CustomItems;

public class ElytraLayer<T extends LivingEntity, M extends EntityModel<T>>
extends LayerRenderer<T, M> {
    private static final ResourceLocation TEXTURE_ELYTRA = new ResourceLocation("textures/entity/elytra.png");
    private final ElytraModel<T> modelElytra = new ElytraModel();

    public ElytraLayer(IEntityRenderer<T, M> iEntityRenderer) {
        super(iEntityRenderer);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        ItemStack itemStack = ((LivingEntity)t).getItemStackFromSlot(EquipmentSlotType.CHEST);
        if (this.shouldRender(itemStack, t)) {
            ResourceLocation resourceLocation;
            Object object;
            if (t instanceof AbstractClientPlayerEntity) {
                object = (AbstractClientPlayerEntity)t;
                if (((AbstractClientPlayerEntity)object).isPlayerInfoSet() && ((AbstractClientPlayerEntity)object).getLocationElytra() != null) {
                    resourceLocation = ((AbstractClientPlayerEntity)object).getLocationElytra();
                } else if (((AbstractClientPlayerEntity)object).hasElytraCape() && ((AbstractClientPlayerEntity)object).hasPlayerInfo() && ((AbstractClientPlayerEntity)object).getLocationCape() != null && ((PlayerEntity)object).isWearing(PlayerModelPart.CAPE)) {
                    resourceLocation = ((AbstractClientPlayerEntity)object).getLocationCape();
                } else {
                    resourceLocation = this.getElytraTexture(itemStack, t);
                    if (Config.isCustomItems()) {
                        resourceLocation = CustomItems.getCustomElytraTexture(itemStack, resourceLocation);
                    }
                }
            } else {
                resourceLocation = this.getElytraTexture(itemStack, t);
                if (Config.isCustomItems()) {
                    resourceLocation = CustomItems.getCustomElytraTexture(itemStack, resourceLocation);
                }
            }
            matrixStack.push();
            matrixStack.translate(0.0, 0.0, 0.125);
            ((EntityModel)this.getEntityModel()).copyModelAttributesTo(this.modelElytra);
            this.modelElytra.setRotationAngles(t, f, f2, f4, f5, f6);
            object = ItemRenderer.getArmorVertexBuilder(iRenderTypeBuffer, RenderType.getArmorCutoutNoCull(resourceLocation), false, itemStack.hasEffect());
            this.modelElytra.render(matrixStack, (IVertexBuilder)object, n, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
            matrixStack.pop();
        }
    }

    public boolean shouldRender(ItemStack itemStack, T t) {
        return itemStack.getItem() == Items.ELYTRA;
    }

    public ResourceLocation getElytraTexture(ItemStack itemStack, T t) {
        return TEXTURE_ELYTRA;
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4, float f5, float f6) {
        this.render(matrixStack, iRenderTypeBuffer, n, (T)((LivingEntity)entity2), f, f2, f3, f4, f5, f6);
    }
}

