/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.TropicalFishRenderer;
import net.minecraft.client.renderer.entity.model.TropicalFishBModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterTropicalFishB
extends ModelAdapter {
    public ModelAdapterTropicalFishB() {
        super(EntityType.TROPICAL_FISH, "tropical_fish_b", 0.2f);
    }

    @Override
    public Model makeModel() {
        return new TropicalFishBModel(0.0f);
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof TropicalFishBModel)) {
            return null;
        }
        TropicalFishBModel tropicalFishBModel = (TropicalFishBModel)model;
        if (string.equals("body")) {
            return (ModelRenderer)Reflector.ModelTropicalFishB_ModelRenderers.getValue(tropicalFishBModel, 0);
        }
        if (string.equals("tail")) {
            return (ModelRenderer)Reflector.ModelTropicalFishB_ModelRenderers.getValue(tropicalFishBModel, 1);
        }
        if (string.equals("fin_right")) {
            return (ModelRenderer)Reflector.ModelTropicalFishB_ModelRenderers.getValue(tropicalFishBModel, 2);
        }
        if (string.equals("fin_left")) {
            return (ModelRenderer)Reflector.ModelTropicalFishB_ModelRenderers.getValue(tropicalFishBModel, 3);
        }
        if (string.equals("fin_top")) {
            return (ModelRenderer)Reflector.ModelTropicalFishB_ModelRenderers.getValue(tropicalFishBModel, 4);
        }
        return string.equals("fin_bottom") ? (ModelRenderer)Reflector.ModelTropicalFishB_ModelRenderers.getValue(tropicalFishBModel, 5) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"body", "tail", "fin_right", "fin_left", "fin_top", "fin_bottom"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        TropicalFishRenderer tropicalFishRenderer;
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        EntityRenderer entityRenderer = entityRendererManager.getEntityRenderMap().get(EntityType.TROPICAL_FISH);
        if (!(entityRenderer instanceof TropicalFishRenderer)) {
            Config.warn("Not a TropicalFishRenderer: " + entityRenderer);
            return null;
        }
        if (entityRenderer.getType() == null) {
            tropicalFishRenderer = new TropicalFishRenderer(entityRendererManager);
            tropicalFishRenderer.shadowSize = f;
            entityRenderer = tropicalFishRenderer;
        }
        tropicalFishRenderer = (TropicalFishRenderer)entityRenderer;
        if (!Reflector.RenderTropicalFish_modelB.exists()) {
            Config.warn("Model field not found: RenderTropicalFish.modelB");
            return null;
        }
        Reflector.RenderTropicalFish_modelB.setValue(tropicalFishRenderer, model);
        return tropicalFishRenderer;
    }
}

