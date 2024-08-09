/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.DrownedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.DrownedModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterZombie;

public class ModelAdapterDrowned
extends ModelAdapterZombie {
    public ModelAdapterDrowned() {
        super(EntityType.DROWNED, "drowned", 0.5f);
    }

    @Override
    public Model makeModel() {
        return new DrownedModel(0.0f, 0.0f, 64, 64);
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        DrownedRenderer drownedRenderer = new DrownedRenderer(entityRendererManager);
        drownedRenderer.entityModel = (DrownedModel)model;
        drownedRenderer.shadowSize = f;
        return drownedRenderer;
    }
}

