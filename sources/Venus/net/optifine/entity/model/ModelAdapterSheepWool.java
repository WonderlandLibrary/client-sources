/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.client.renderer.entity.layers.SheepWoolLayer;
import net.minecraft.client.renderer.entity.model.SheepModel;
import net.minecraft.client.renderer.entity.model.SheepWoolModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterQuadruped;

public class ModelAdapterSheepWool
extends ModelAdapterQuadruped {
    public ModelAdapterSheepWool() {
        super(EntityType.SHEEP, "sheep_wool", 0.7f);
    }

    @Override
    public Model makeModel() {
        return new SheepWoolModel();
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        SheepWoolLayer sheepWoolLayer;
        SheepRenderer sheepRenderer;
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        EntityRenderer entityRenderer = entityRendererManager.getEntityRenderMap().get(EntityType.SHEEP);
        if (!(entityRenderer instanceof SheepRenderer)) {
            Config.warn("Not a RenderSheep: " + entityRenderer);
            return null;
        }
        if (entityRenderer.getType() == null) {
            sheepRenderer = new SheepRenderer(entityRendererManager);
            sheepRenderer.entityModel = new SheepModel();
            sheepRenderer.shadowSize = 0.7f;
            entityRenderer = sheepRenderer;
        }
        sheepRenderer = (SheepRenderer)entityRenderer;
        List list = sheepRenderer.getLayerRenderers();
        Iterator iterator2 = list.iterator();
        while (iterator2.hasNext()) {
            sheepWoolLayer = iterator2.next();
            if (!(sheepWoolLayer instanceof SheepWoolLayer)) continue;
            iterator2.remove();
        }
        sheepWoolLayer = new SheepWoolLayer(sheepRenderer);
        sheepWoolLayer.sheepModel = (SheepWoolModel)model;
        sheepRenderer.addLayer(sheepWoolLayer);
        return sheepRenderer;
    }
}

