/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PolarBearRenderer;
import net.minecraft.client.renderer.entity.model.PolarBearModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterQuadruped;

public class ModelAdapterPolarBear
extends ModelAdapterQuadruped {
    public ModelAdapterPolarBear() {
        super(EntityType.POLAR_BEAR, "polar_bear", 0.7f);
    }

    @Override
    public Model makeModel() {
        return new PolarBearModel();
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        PolarBearRenderer polarBearRenderer = new PolarBearRenderer(entityRendererManager);
        polarBearRenderer.entityModel = (PolarBearModel)model;
        polarBearRenderer.shadowSize = f;
        return polarBearRenderer;
    }
}

