/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SheepWoolLayer;
import net.minecraft.client.renderer.entity.model.SheepModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.ResourceLocation;

public class SheepRenderer
extends MobRenderer<SheepEntity, SheepModel<SheepEntity>> {
    private static final ResourceLocation SHEARED_SHEEP_TEXTURES = new ResourceLocation("textures/entity/sheep/sheep.png");

    public SheepRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new SheepModel(), 0.7f);
        this.addLayer(new SheepWoolLayer(this));
    }

    @Override
    public ResourceLocation getEntityTexture(SheepEntity sheepEntity) {
        return SHEARED_SHEEP_TEXTURES;
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((SheepEntity)entity2);
    }
}

