/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.BeeModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.util.ResourceLocation;

public class BeeRenderer
extends MobRenderer<BeeEntity, BeeModel<BeeEntity>> {
    private static final ResourceLocation field_229040_a_ = new ResourceLocation("textures/entity/bee/bee_angry.png");
    private static final ResourceLocation field_229041_g_ = new ResourceLocation("textures/entity/bee/bee_angry_nectar.png");
    private static final ResourceLocation field_229042_h_ = new ResourceLocation("textures/entity/bee/bee.png");
    private static final ResourceLocation field_229043_i_ = new ResourceLocation("textures/entity/bee/bee_nectar.png");

    public BeeRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new BeeModel(), 0.4f);
    }

    @Override
    public ResourceLocation getEntityTexture(BeeEntity beeEntity) {
        if (beeEntity.func_233678_J__()) {
            return beeEntity.hasNectar() ? field_229041_g_ : field_229040_a_;
        }
        return beeEntity.hasNectar() ? field_229043_i_ : field_229042_h_;
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((BeeEntity)entity2);
    }
}

