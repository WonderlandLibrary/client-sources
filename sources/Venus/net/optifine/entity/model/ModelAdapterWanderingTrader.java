/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.WanderingTraderRenderer;
import net.minecraft.client.renderer.entity.model.VillagerModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterVillager;

public class ModelAdapterWanderingTrader
extends ModelAdapterVillager {
    public ModelAdapterWanderingTrader() {
        super(EntityType.WANDERING_TRADER, "wandering_trader", 0.5f);
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        WanderingTraderRenderer wanderingTraderRenderer = new WanderingTraderRenderer(entityRendererManager);
        wanderingTraderRenderer.entityModel = (VillagerModel)model;
        wanderingTraderRenderer.shadowSize = f;
        return wanderingTraderRenderer;
    }
}

