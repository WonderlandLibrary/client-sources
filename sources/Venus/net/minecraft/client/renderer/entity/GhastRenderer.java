/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.GhastModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.util.ResourceLocation;

public class GhastRenderer
extends MobRenderer<GhastEntity, GhastModel<GhastEntity>> {
    private static final ResourceLocation GHAST_TEXTURES = new ResourceLocation("textures/entity/ghast/ghast.png");
    private static final ResourceLocation GHAST_SHOOTING_TEXTURES = new ResourceLocation("textures/entity/ghast/ghast_shooting.png");

    public GhastRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new GhastModel(), 1.5f);
    }

    @Override
    public ResourceLocation getEntityTexture(GhastEntity ghastEntity) {
        return ghastEntity.isAttacking() ? GHAST_SHOOTING_TEXTURES : GHAST_TEXTURES;
    }

    @Override
    protected void preRenderCallback(GhastEntity ghastEntity, MatrixStack matrixStack, float f) {
        float f2 = 1.0f;
        float f3 = 4.5f;
        float f4 = 4.5f;
        matrixStack.scale(4.5f, 4.5f, 4.5f);
    }

    @Override
    protected void preRenderCallback(LivingEntity livingEntity, MatrixStack matrixStack, float f) {
        this.preRenderCallback((GhastEntity)livingEntity, matrixStack, f);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((GhastEntity)entity2);
    }
}

