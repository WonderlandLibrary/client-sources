/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.CaveSpiderRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.SpiderModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterSpider;

public class ModelAdapterCaveSpider
extends ModelAdapterSpider {
    public ModelAdapterCaveSpider() {
        super(EntityType.CAVE_SPIDER, "cave_spider", 0.7f);
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        CaveSpiderRenderer caveSpiderRenderer = new CaveSpiderRenderer(entityRendererManager);
        caveSpiderRenderer.entityModel = (SpiderModel)model;
        caveSpiderRenderer.shadowSize = f;
        return caveSpiderRenderer;
    }
}

