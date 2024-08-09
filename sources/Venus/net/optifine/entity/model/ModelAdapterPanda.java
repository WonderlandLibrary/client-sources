/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PandaRenderer;
import net.minecraft.client.renderer.entity.model.PandaModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterQuadruped;

public class ModelAdapterPanda
extends ModelAdapterQuadruped {
    public ModelAdapterPanda() {
        super(EntityType.PANDA, "panda", 0.9f);
    }

    @Override
    public Model makeModel() {
        return new PandaModel(9, 0.0f);
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        PandaRenderer pandaRenderer = new PandaRenderer(entityRendererManager);
        pandaRenderer.entityModel = (PandaModel)model;
        pandaRenderer.shadowSize = f;
        return pandaRenderer;
    }
}

