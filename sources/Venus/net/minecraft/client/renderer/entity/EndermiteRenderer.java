/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.EndermiteModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EndermiteEntity;
import net.minecraft.util.ResourceLocation;

public class EndermiteRenderer
extends MobRenderer<EndermiteEntity, EndermiteModel<EndermiteEntity>> {
    private static final ResourceLocation ENDERMITE_TEXTURES = new ResourceLocation("textures/entity/endermite.png");

    public EndermiteRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new EndermiteModel(), 0.3f);
    }

    @Override
    protected float getDeathMaxRotation(EndermiteEntity endermiteEntity) {
        return 180.0f;
    }

    @Override
    public ResourceLocation getEntityTexture(EndermiteEntity endermiteEntity) {
        return ENDERMITE_TEXTURES;
    }

    @Override
    protected float getDeathMaxRotation(LivingEntity livingEntity) {
        return this.getDeathMaxRotation((EndermiteEntity)livingEntity);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((EndermiteEntity)entity2);
    }
}

