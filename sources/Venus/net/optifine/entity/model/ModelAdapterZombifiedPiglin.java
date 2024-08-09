/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PiglinRenderer;
import net.minecraft.client.renderer.entity.model.PiglinModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterPiglin;

public class ModelAdapterZombifiedPiglin
extends ModelAdapterPiglin {
    public ModelAdapterZombifiedPiglin() {
        super(EntityType.ZOMBIFIED_PIGLIN, "zombified_piglin", 0.5f);
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        PiglinRenderer piglinRenderer = new PiglinRenderer(entityRendererManager, true);
        piglinRenderer.entityModel = (PiglinModel)model;
        piglinRenderer.shadowSize = f;
        return piglinRenderer;
    }
}

