/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.BoarModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.ZoglinEntity;
import net.minecraft.util.ResourceLocation;

public class ZoglinRenderer
extends MobRenderer<ZoglinEntity, BoarModel<ZoglinEntity>> {
    private static final ResourceLocation field_239399_a_ = new ResourceLocation("textures/entity/hoglin/zoglin.png");

    public ZoglinRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new BoarModel(), 0.7f);
    }

    @Override
    public ResourceLocation getEntityTexture(ZoglinEntity zoglinEntity) {
        return field_239399_a_;
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((ZoglinEntity)entity2);
    }
}

