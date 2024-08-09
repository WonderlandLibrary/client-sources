/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.HorseModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;

public abstract class AbstractHorseRenderer<T extends AbstractHorseEntity, M extends HorseModel<T>>
extends MobRenderer<T, M> {
    private final float scale;

    public AbstractHorseRenderer(EntityRendererManager entityRendererManager, M m, float f) {
        super(entityRendererManager, m, 0.75f);
        this.scale = f;
    }

    @Override
    protected void preRenderCallback(T t, MatrixStack matrixStack, float f) {
        matrixStack.scale(this.scale, this.scale, this.scale);
        super.preRenderCallback(t, matrixStack, f);
    }

    @Override
    protected void preRenderCallback(LivingEntity livingEntity, MatrixStack matrixStack, float f) {
        this.preRenderCallback((T)((AbstractHorseEntity)livingEntity), matrixStack, f);
    }
}

