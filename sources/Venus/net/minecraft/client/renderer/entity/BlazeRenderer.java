/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.BlazeModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class BlazeRenderer
extends MobRenderer<BlazeEntity, BlazeModel<BlazeEntity>> {
    private static final ResourceLocation BLAZE_TEXTURES = new ResourceLocation("textures/entity/blaze.png");

    public BlazeRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new BlazeModel(), 0.5f);
    }

    @Override
    protected int getBlockLight(BlazeEntity blazeEntity, BlockPos blockPos) {
        return 0;
    }

    @Override
    public ResourceLocation getEntityTexture(BlazeEntity blazeEntity) {
        return BLAZE_TEXTURES;
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((BlazeEntity)entity2);
    }

    @Override
    protected int getBlockLight(Entity entity2, BlockPos blockPos) {
        return this.getBlockLight((BlazeEntity)entity2, blockPos);
    }
}

