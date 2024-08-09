/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CrossedArmsItemLayer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.VillagerLevelPendantLayer;
import net.minecraft.client.renderer.entity.model.VillagerModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.util.ResourceLocation;

public class VillagerRenderer
extends MobRenderer<VillagerEntity, VillagerModel<VillagerEntity>> {
    private static final ResourceLocation VILLAGER_TEXTURES = new ResourceLocation("textures/entity/villager/villager.png");

    public VillagerRenderer(EntityRendererManager entityRendererManager, IReloadableResourceManager iReloadableResourceManager) {
        super(entityRendererManager, new VillagerModel(0.0f), 0.5f);
        this.addLayer(new HeadLayer<VillagerEntity, VillagerModel<VillagerEntity>>(this));
        this.addLayer(new VillagerLevelPendantLayer<VillagerEntity, VillagerModel<VillagerEntity>>(this, iReloadableResourceManager, "villager"));
        this.addLayer(new CrossedArmsItemLayer<VillagerEntity, VillagerModel<VillagerEntity>>(this));
    }

    @Override
    public ResourceLocation getEntityTexture(VillagerEntity villagerEntity) {
        return VILLAGER_TEXTURES;
    }

    @Override
    protected void preRenderCallback(VillagerEntity villagerEntity, MatrixStack matrixStack, float f) {
        float f2 = 0.9375f;
        if (villagerEntity.isChild()) {
            f2 = (float)((double)f2 * 0.5);
            this.shadowSize = 0.25f;
        } else {
            this.shadowSize = 0.5f;
        }
        matrixStack.scale(f2, f2, f2);
    }

    @Override
    protected void preRenderCallback(LivingEntity livingEntity, MatrixStack matrixStack, float f) {
        this.preRenderCallback((VillagerEntity)livingEntity, matrixStack, f);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((VillagerEntity)entity2);
    }
}

