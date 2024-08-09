/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.CatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.CatModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterOcelot;

public class ModelAdapterCat
extends ModelAdapterOcelot {
    public ModelAdapterCat() {
        super(EntityType.CAT, "cat", 0.4f);
    }

    @Override
    public Model makeModel() {
        return new CatModel(0.0f);
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        CatRenderer catRenderer = new CatRenderer(entityRendererManager);
        catRenderer.entityModel = (CatModel)model;
        catRenderer.shadowSize = f;
        return catRenderer;
    }
}

