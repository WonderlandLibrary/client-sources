/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.GiantZombieRenderer;
import net.minecraft.client.renderer.entity.model.GiantModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterZombie;

public class ModelAdapterGiant
extends ModelAdapterZombie {
    public ModelAdapterGiant() {
        super(EntityType.GIANT, "giant", 3.0f);
    }

    @Override
    public Model makeModel() {
        return new GiantModel();
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        GiantZombieRenderer giantZombieRenderer = new GiantZombieRenderer(entityRendererManager, 6.0f);
        giantZombieRenderer.entityModel = (GiantModel)model;
        giantZombieRenderer.shadowSize = f;
        return giantZombieRenderer;
    }
}

