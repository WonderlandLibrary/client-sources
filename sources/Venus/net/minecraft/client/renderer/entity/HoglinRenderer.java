/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.BoarModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.HoglinEntity;
import net.minecraft.util.ResourceLocation;

public class HoglinRenderer
extends MobRenderer<HoglinEntity, BoarModel<HoglinEntity>> {
    private static final ResourceLocation field_239382_a_ = new ResourceLocation("textures/entity/hoglin/hoglin.png");

    public HoglinRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new BoarModel(), 0.7f);
    }

    @Override
    public ResourceLocation getEntityTexture(HoglinEntity hoglinEntity) {
        return field_239382_a_;
    }

    @Override
    protected boolean func_230495_a_(HoglinEntity hoglinEntity) {
        return hoglinEntity.func_234364_eK_();
    }

    @Override
    protected boolean func_230495_a_(LivingEntity livingEntity) {
        return this.func_230495_a_((HoglinEntity)livingEntity);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((HoglinEntity)entity2);
    }
}

