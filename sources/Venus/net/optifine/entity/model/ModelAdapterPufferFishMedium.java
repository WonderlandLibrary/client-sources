/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PufferfishRenderer;
import net.minecraft.client.renderer.entity.model.PufferFishMediumModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterPufferFishMedium
extends ModelAdapter {
    public ModelAdapterPufferFishMedium() {
        super(EntityType.PUFFERFISH, "puffer_fish_medium", 0.2f);
    }

    @Override
    public Model makeModel() {
        return new PufferFishMediumModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof PufferFishMediumModel)) {
            return null;
        }
        PufferFishMediumModel pufferFishMediumModel = (PufferFishMediumModel)model;
        if (string.equals("body")) {
            return (ModelRenderer)Reflector.ModelPufferFishMedium_ModelRenderers.getValue(pufferFishMediumModel, 0);
        }
        if (string.equals("fin_right")) {
            return (ModelRenderer)Reflector.ModelPufferFishMedium_ModelRenderers.getValue(pufferFishMediumModel, 1);
        }
        if (string.equals("fin_left")) {
            return (ModelRenderer)Reflector.ModelPufferFishMedium_ModelRenderers.getValue(pufferFishMediumModel, 2);
        }
        if (string.equals("spikes_front_top")) {
            return (ModelRenderer)Reflector.ModelPufferFishMedium_ModelRenderers.getValue(pufferFishMediumModel, 3);
        }
        if (string.equals("spikes_back_top")) {
            return (ModelRenderer)Reflector.ModelPufferFishMedium_ModelRenderers.getValue(pufferFishMediumModel, 4);
        }
        if (string.equals("spikes_front_right")) {
            return (ModelRenderer)Reflector.ModelPufferFishMedium_ModelRenderers.getValue(pufferFishMediumModel, 5);
        }
        if (string.equals("spikes_back_right")) {
            return (ModelRenderer)Reflector.ModelPufferFishMedium_ModelRenderers.getValue(pufferFishMediumModel, 6);
        }
        if (string.equals("spikes_back_left")) {
            return (ModelRenderer)Reflector.ModelPufferFishMedium_ModelRenderers.getValue(pufferFishMediumModel, 7);
        }
        if (string.equals("spikes_front_left")) {
            return (ModelRenderer)Reflector.ModelPufferFishMedium_ModelRenderers.getValue(pufferFishMediumModel, 8);
        }
        if (string.equals("spikes_back_bottom")) {
            return (ModelRenderer)Reflector.ModelPufferFishMedium_ModelRenderers.getValue(pufferFishMediumModel, 9);
        }
        return string.equals("spikes_front_bottom") ? (ModelRenderer)Reflector.ModelPufferFishMedium_ModelRenderers.getValue(pufferFishMediumModel, 10) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"body", "fin_right", "fin_left", "spikes_front_top", "spikes_back_top", "spikes_front_right", "spikes_back_right", "spikes_back_left", "spikes_front_left", "spikes_back_bottom", "spikes_front_bottom"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        PufferfishRenderer pufferfishRenderer;
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        EntityRenderer entityRenderer = entityRendererManager.getEntityRenderMap().get(EntityType.PUFFERFISH);
        if (!(entityRenderer instanceof PufferfishRenderer)) {
            Config.warn("Not a PufferfishRenderer: " + entityRenderer);
            return null;
        }
        if (entityRenderer.getType() == null) {
            pufferfishRenderer = new PufferfishRenderer(entityRendererManager);
            pufferfishRenderer.shadowSize = f;
            entityRenderer = pufferfishRenderer;
        }
        pufferfishRenderer = (PufferfishRenderer)entityRenderer;
        if (!Reflector.RenderPufferfish_modelMedium.exists()) {
            Config.warn("Model field not found: RenderPufferfish.modelMedium");
            return null;
        }
        Reflector.RenderPufferfish_modelMedium.setValue(pufferfishRenderer, model);
        return pufferfishRenderer;
    }
}

