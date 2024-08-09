/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.util.ResourceLocation;

public class HuskRenderer
extends ZombieRenderer {
    private static final ResourceLocation HUSK_ZOMBIE_TEXTURES = new ResourceLocation("textures/entity/zombie/husk.png");

    public HuskRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager);
    }

    @Override
    protected void preRenderCallback(ZombieEntity zombieEntity, MatrixStack matrixStack, float f) {
        float f2 = 1.0625f;
        matrixStack.scale(1.0625f, 1.0625f, 1.0625f);
        super.preRenderCallback(zombieEntity, matrixStack, f);
    }

    @Override
    public ResourceLocation getEntityTexture(ZombieEntity zombieEntity) {
        return HUSK_ZOMBIE_TEXTURES;
    }

    @Override
    public ResourceLocation getEntityTexture(MobEntity mobEntity) {
        return this.getEntityTexture((ZombieEntity)mobEntity);
    }

    @Override
    protected void preRenderCallback(LivingEntity livingEntity, MatrixStack matrixStack, float f) {
        this.preRenderCallback((ZombieEntity)livingEntity, matrixStack, f);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((ZombieEntity)entity2);
    }
}

