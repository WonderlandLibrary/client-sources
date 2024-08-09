/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.CaveSpiderEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.util.ResourceLocation;

public class CaveSpiderRenderer
extends SpiderRenderer<CaveSpiderEntity> {
    private static final ResourceLocation CAVE_SPIDER_TEXTURES = new ResourceLocation("textures/entity/spider/cave_spider.png");

    public CaveSpiderRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager);
        this.shadowSize *= 0.7f;
    }

    @Override
    protected void preRenderCallback(CaveSpiderEntity caveSpiderEntity, MatrixStack matrixStack, float f) {
        matrixStack.scale(0.7f, 0.7f, 0.7f);
    }

    @Override
    public ResourceLocation getEntityTexture(CaveSpiderEntity caveSpiderEntity) {
        return CAVE_SPIDER_TEXTURES;
    }

    @Override
    public ResourceLocation getEntityTexture(SpiderEntity spiderEntity) {
        return this.getEntityTexture((CaveSpiderEntity)spiderEntity);
    }

    @Override
    protected void preRenderCallback(LivingEntity livingEntity, MatrixStack matrixStack, float f) {
        this.preRenderCallback((CaveSpiderEntity)livingEntity, matrixStack, f);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((CaveSpiderEntity)entity2);
    }
}

