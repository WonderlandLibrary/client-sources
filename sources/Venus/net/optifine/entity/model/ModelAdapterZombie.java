/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.model.ZombieModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterBiped;

public class ModelAdapterZombie
extends ModelAdapterBiped {
    public ModelAdapterZombie() {
        super(EntityType.ZOMBIE, "zombie", 0.5f);
    }

    protected ModelAdapterZombie(EntityType entityType, String string, float f) {
        super(entityType, string, f);
    }

    @Override
    public Model makeModel() {
        return new ZombieModel(0.0f, false);
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        ZombieRenderer zombieRenderer = new ZombieRenderer(entityRendererManager);
        zombieRenderer.entityModel = (ZombieModel)model;
        zombieRenderer.shadowSize = f;
        return zombieRenderer;
    }
}

