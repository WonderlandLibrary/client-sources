/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.TropicalFishRenderer;
import net.minecraft.client.renderer.entity.model.TropicalFishAModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterTropicalFishA
extends ModelAdapter {
    public ModelAdapterTropicalFishA() {
        super(EntityType.TROPICAL_FISH, "tropical_fish_a", 0.2f);
    }

    @Override
    public Model makeModel() {
        return new TropicalFishAModel(0.0f);
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof TropicalFishAModel)) {
            return null;
        }
        TropicalFishAModel tropicalFishAModel = (TropicalFishAModel)model;
        if (string.equals("body")) {
            return (ModelRenderer)Reflector.ModelTropicalFishA_ModelRenderers.getValue(tropicalFishAModel, 0);
        }
        if (string.equals("tail")) {
            return (ModelRenderer)Reflector.ModelTropicalFishA_ModelRenderers.getValue(tropicalFishAModel, 1);
        }
        if (string.equals("fin_right")) {
            return (ModelRenderer)Reflector.ModelTropicalFishA_ModelRenderers.getValue(tropicalFishAModel, 2);
        }
        if (string.equals("fin_left")) {
            return (ModelRenderer)Reflector.ModelTropicalFishA_ModelRenderers.getValue(tropicalFishAModel, 3);
        }
        return string.equals("fin_top") ? (ModelRenderer)Reflector.ModelTropicalFishA_ModelRenderers.getValue(tropicalFishAModel, 4) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"body", "tail", "fin_right", "fin_left", "fin_top"};
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
        if (!Reflector.RenderTropicalFish_modelA.exists()) {
            Config.warn("Model field not found: RenderTropicalFish.modelA");
            return null;
        }
        Reflector.RenderTropicalFish_modelA.setValue(tropicalFishRenderer, model);
        return tropicalFishRenderer;
    }
}

