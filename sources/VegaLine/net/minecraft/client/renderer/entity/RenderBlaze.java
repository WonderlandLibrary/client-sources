/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBlaze;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.util.ResourceLocation;

public class RenderBlaze
extends RenderLiving<EntityBlaze> {
    private static final ResourceLocation BLAZE_TEXTURES = new ResourceLocation("textures/entity/blaze.png");

    public RenderBlaze(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelBlaze(), 0.5f);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityBlaze entity) {
        return BLAZE_TEXTURES;
    }
}

