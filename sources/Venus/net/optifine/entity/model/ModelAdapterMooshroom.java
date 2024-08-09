/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MooshroomRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterQuadruped;

public class ModelAdapterMooshroom
extends ModelAdapterQuadruped {
    public ModelAdapterMooshroom() {
        super(EntityType.MOOSHROOM, "mooshroom", 0.7f);
    }

    @Override
    public Model makeModel() {
        return new CowModel();
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        MooshroomRenderer mooshroomRenderer = new MooshroomRenderer(entityRendererManager);
        mooshroomRenderer.entityModel = (CowModel)model;
        mooshroomRenderer.shadowSize = f;
        return mooshroomRenderer;
    }
}

