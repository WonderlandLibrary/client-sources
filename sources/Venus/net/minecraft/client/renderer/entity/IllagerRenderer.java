/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.model.IllagerModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;

public abstract class IllagerRenderer<T extends AbstractIllagerEntity>
extends MobRenderer<T, IllagerModel<T>> {
    protected IllagerRenderer(EntityRendererManager entityRendererManager, IllagerModel<T> illagerModel, float f) {
        super(entityRendererManager, illagerModel, f);
        this.addLayer(new HeadLayer(this));
    }

    @Override
    protected void preRenderCallback(T t, MatrixStack matrixStack, float f) {
        float f2 = 0.9375f;
        matrixStack.scale(0.9375f, 0.9375f, 0.9375f);
    }

    @Override
    protected void preRenderCallback(LivingEntity livingEntity, MatrixStack matrixStack, float f) {
        this.preRenderCallback((T)((AbstractIllagerEntity)livingEntity), matrixStack, f);
    }
}

