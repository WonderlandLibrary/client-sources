/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LlamaRenderer;
import net.minecraft.client.renderer.entity.layers.LlamaDecorLayer;
import net.minecraft.client.renderer.entity.model.LlamaModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterLlama;
import net.optifine.reflect.Reflector;

public class ModelAdapterLlamaDecor
extends ModelAdapterLlama {
    public ModelAdapterLlamaDecor() {
        super(EntityType.LLAMA, "llama_decor", 0.7f);
    }

    @Override
    public Model makeModel() {
        return new LlamaModel(0.5f);
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        LlamaDecorLayer llamaDecorLayer;
        LlamaRenderer llamaRenderer;
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        EntityRenderer entityRenderer = entityRendererManager.getEntityRenderMap().get(EntityType.LLAMA);
        if (!(entityRenderer instanceof LlamaRenderer)) {
            Config.warn("Not a RenderLlama: " + entityRenderer);
            return null;
        }
        if (entityRenderer.getType() == null) {
            llamaRenderer = new LlamaRenderer(entityRendererManager);
            llamaRenderer.entityModel = new LlamaModel(0.0f);
            llamaRenderer.shadowSize = 0.7f;
            entityRenderer = llamaRenderer;
        }
        llamaRenderer = (LlamaRenderer)entityRenderer;
        List list = llamaRenderer.getLayerRenderers();
        Iterator iterator2 = list.iterator();
        while (iterator2.hasNext()) {
            llamaDecorLayer = iterator2.next();
            if (!(llamaDecorLayer instanceof LlamaDecorLayer)) continue;
            iterator2.remove();
        }
        llamaDecorLayer = new LlamaDecorLayer(llamaRenderer);
        if (!Reflector.LayerLlamaDecor_model.exists()) {
            Config.warn("Field not found: LayerLlamaDecor.model");
            return null;
        }
        Reflector.LayerLlamaDecor_model.setValue(llamaDecorLayer, model);
        llamaRenderer.addLayer(llamaDecorLayer);
        return llamaRenderer;
    }
}

