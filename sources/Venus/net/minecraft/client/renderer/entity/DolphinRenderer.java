/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.DolphinCarriedItemLayer;
import net.minecraft.client.renderer.entity.model.DolphinModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.util.ResourceLocation;

public class DolphinRenderer
extends MobRenderer<DolphinEntity, DolphinModel<DolphinEntity>> {
    private static final ResourceLocation DOLPHIN_LOCATION = new ResourceLocation("textures/entity/dolphin.png");

    public DolphinRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new DolphinModel(), 0.7f);
        this.addLayer(new DolphinCarriedItemLayer(this));
    }

    @Override
    public ResourceLocation getEntityTexture(DolphinEntity dolphinEntity) {
        return DOLPHIN_LOCATION;
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((DolphinEntity)entity2);
    }
}

