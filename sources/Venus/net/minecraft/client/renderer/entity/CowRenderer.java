/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.util.ResourceLocation;

public class CowRenderer
extends MobRenderer<CowEntity, CowModel<CowEntity>> {
    private static final ResourceLocation COW_TEXTURES = new ResourceLocation("textures/entity/cow/cow.png");

    public CowRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new CowModel(), 0.7f);
    }

    @Override
    public ResourceLocation getEntityTexture(CowEntity cowEntity) {
        return COW_TEXTURES;
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((CowEntity)entity2);
    }
}

