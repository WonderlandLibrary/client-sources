/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.client.renderer.entity.model.StriderModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.StriderEntity;
import net.minecraft.util.ResourceLocation;

public class StriderRenderer
extends MobRenderer<StriderEntity, StriderModel<StriderEntity>> {
    private static final ResourceLocation field_239397_a_ = new ResourceLocation("textures/entity/strider/strider.png");
    private static final ResourceLocation field_239398_g_ = new ResourceLocation("textures/entity/strider/strider_cold.png");

    public StriderRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new StriderModel(), 0.5f);
        this.addLayer(new SaddleLayer(this, new StriderModel(), new ResourceLocation("textures/entity/strider/strider_saddle.png")));
    }

    @Override
    public ResourceLocation getEntityTexture(StriderEntity striderEntity) {
        return striderEntity.func_234315_eI_() ? field_239398_g_ : field_239397_a_;
    }

    @Override
    protected void preRenderCallback(StriderEntity striderEntity, MatrixStack matrixStack, float f) {
        if (striderEntity.isChild()) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
            this.shadowSize = 0.25f;
        } else {
            this.shadowSize = 0.5f;
        }
    }

    @Override
    protected boolean func_230495_a_(StriderEntity striderEntity) {
        return striderEntity.func_234315_eI_();
    }

    @Override
    protected void preRenderCallback(LivingEntity livingEntity, MatrixStack matrixStack, float f) {
        this.preRenderCallback((StriderEntity)livingEntity, matrixStack, f);
    }

    @Override
    protected boolean func_230495_a_(LivingEntity livingEntity) {
        return this.func_230495_a_((StriderEntity)livingEntity);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((StriderEntity)entity2);
    }
}

