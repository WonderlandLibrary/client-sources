/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.ZoglinRenderer;
import net.minecraft.client.renderer.entity.model.BoarModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterHoglin;

public class ModelAdapterZoglin
extends ModelAdapterHoglin {
    public ModelAdapterZoglin() {
        super(EntityType.ZOGLIN, "zoglin", 0.7f);
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        ZoglinRenderer zoglinRenderer = new ZoglinRenderer(entityRendererManager);
        zoglinRenderer.entityModel = (BoarModel)model;
        zoglinRenderer.shadowSize = f;
        return zoglinRenderer;
    }
}

