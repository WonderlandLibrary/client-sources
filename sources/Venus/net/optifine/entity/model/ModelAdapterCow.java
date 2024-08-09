/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.CowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterQuadruped;

public class ModelAdapterCow
extends ModelAdapterQuadruped {
    public ModelAdapterCow() {
        super(EntityType.COW, "cow", 0.7f);
    }

    @Override
    public Model makeModel() {
        return new CowModel();
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        CowRenderer cowRenderer = new CowRenderer(entityRendererManager);
        cowRenderer.entityModel = (CowModel)model;
        cowRenderer.shadowSize = f;
        return cowRenderer;
    }
}

