/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.EvokerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IllagerModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterIllager;

public class ModelAdapterEvoker
extends ModelAdapterIllager {
    public ModelAdapterEvoker() {
        super(EntityType.EVOKER, "evoker", 0.5f, new String[]{"evocation_illager"});
    }

    @Override
    public Model makeModel() {
        return new IllagerModel(0.0f, 0.0f, 64, 64);
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        EvokerRenderer evokerRenderer = new EvokerRenderer(entityRendererManager);
        evokerRenderer.entityModel = (EntityModel)model;
        evokerRenderer.shadowSize = f;
        return evokerRenderer;
    }
}

