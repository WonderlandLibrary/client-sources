/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.RavagerModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.util.ResourceLocation;

public class RavagerRenderer
extends MobRenderer<RavagerEntity, RavagerModel> {
    private static final ResourceLocation RAVAGER_TEXTURES = new ResourceLocation("textures/entity/illager/ravager.png");

    public RavagerRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new RavagerModel(), 1.1f);
    }

    @Override
    public ResourceLocation getEntityTexture(RavagerEntity ravagerEntity) {
        return RAVAGER_TEXTURES;
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((RavagerEntity)entity2);
    }
}

