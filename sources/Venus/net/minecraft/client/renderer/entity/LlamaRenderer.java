/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.LlamaDecorLayer;
import net.minecraft.client.renderer.entity.model.LlamaModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.util.ResourceLocation;

public class LlamaRenderer
extends MobRenderer<LlamaEntity, LlamaModel<LlamaEntity>> {
    private static final ResourceLocation[] LLAMA_TEXTURES = new ResourceLocation[]{new ResourceLocation("textures/entity/llama/creamy.png"), new ResourceLocation("textures/entity/llama/white.png"), new ResourceLocation("textures/entity/llama/brown.png"), new ResourceLocation("textures/entity/llama/gray.png")};

    public LlamaRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new LlamaModel(0.0f), 0.7f);
        this.addLayer(new LlamaDecorLayer(this));
    }

    @Override
    public ResourceLocation getEntityTexture(LlamaEntity llamaEntity) {
        return LLAMA_TEXTURES[llamaEntity.getVariant()];
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((LlamaEntity)entity2);
    }
}

