/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.HuskRenderer;
import net.minecraft.client.renderer.entity.model.ZombieModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterBiped;

public class ModelAdapterHusk
extends ModelAdapterBiped {
    public ModelAdapterHusk() {
        super(EntityType.HUSK, "husk", 0.5f);
    }

    @Override
    public Model makeModel() {
        return new ZombieModel(0.0f, false);
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        HuskRenderer huskRenderer = new HuskRenderer(entityRendererManager);
        huskRenderer.entityModel = (ZombieModel)model;
        huskRenderer.shadowSize = f;
        return huskRenderer;
    }
}

