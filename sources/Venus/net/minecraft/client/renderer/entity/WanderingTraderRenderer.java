/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CrossedArmsItemLayer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.model.VillagerModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.util.ResourceLocation;

public class WanderingTraderRenderer
extends MobRenderer<WanderingTraderEntity, VillagerModel<WanderingTraderEntity>> {
    private static final ResourceLocation field_217780_a = new ResourceLocation("textures/entity/wandering_trader.png");

    public WanderingTraderRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new VillagerModel(0.0f), 0.5f);
        this.addLayer(new HeadLayer<WanderingTraderEntity, VillagerModel<WanderingTraderEntity>>(this));
        this.addLayer(new CrossedArmsItemLayer<WanderingTraderEntity, VillagerModel<WanderingTraderEntity>>(this));
    }

    @Override
    public ResourceLocation getEntityTexture(WanderingTraderEntity wanderingTraderEntity) {
        return field_217780_a;
    }

    @Override
    protected void preRenderCallback(WanderingTraderEntity wanderingTraderEntity, MatrixStack matrixStack, float f) {
        float f2 = 0.9375f;
        matrixStack.scale(0.9375f, 0.9375f, 0.9375f);
    }

    @Override
    protected void preRenderCallback(LivingEntity livingEntity, MatrixStack matrixStack, float f) {
        this.preRenderCallback((WanderingTraderEntity)livingEntity, matrixStack, f);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((WanderingTraderEntity)entity2);
    }
}

