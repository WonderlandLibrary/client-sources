/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PufferfishRenderer;
import net.minecraft.client.renderer.entity.model.PufferFishBigModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterPufferFishBig
extends ModelAdapter {
    public ModelAdapterPufferFishBig() {
        super(EntityType.PUFFERFISH, "puffer_fish_big", 0.2f);
    }

    @Override
    public Model makeModel() {
        return new PufferFishBigModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof PufferFishBigModel)) {
            return null;
        }
        PufferFishBigModel pufferFishBigModel = (PufferFishBigModel)model;
        if (string.equals("body")) {
            return (ModelRenderer)Reflector.ModelPufferFishBig_ModelRenderers.getValue(pufferFishBigModel, 0);
        }
        if (string.equals("fin_right")) {
            return (ModelRenderer)Reflector.ModelPufferFishBig_ModelRenderers.getValue(pufferFishBigModel, 1);
        }
        if (string.equals("fin_left")) {
            return (ModelRenderer)Reflector.ModelPufferFishBig_ModelRenderers.getValue(pufferFishBigModel, 2);
        }
        if (string.equals("spikes_front_top")) {
            return (ModelRenderer)Reflector.ModelPufferFishBig_ModelRenderers.getValue(pufferFishBigModel, 3);
        }
        if (string.equals("spikes_middle_top")) {
            return (ModelRenderer)Reflector.ModelPufferFishBig_ModelRenderers.getValue(pufferFishBigModel, 4);
        }
        if (string.equals("spikes_back_top")) {
            return (ModelRenderer)Reflector.ModelPufferFishBig_ModelRenderers.getValue(pufferFishBigModel, 5);
        }
        if (string.equals("spikes_front_right")) {
            return (ModelRenderer)Reflector.ModelPufferFishBig_ModelRenderers.getValue(pufferFishBigModel, 6);
        }
        if (string.equals("spikes_front_left")) {
            return (ModelRenderer)Reflector.ModelPufferFishBig_ModelRenderers.getValue(pufferFishBigModel, 7);
        }
        if (string.equals("spikes_front_bottom")) {
            return (ModelRenderer)Reflector.ModelPufferFishBig_ModelRenderers.getValue(pufferFishBigModel, 8);
        }
        if (string.equals("spikes_middle_bottom")) {
            return (ModelRenderer)Reflector.ModelPufferFishBig_ModelRenderers.getValue(pufferFishBigModel, 9);
        }
        if (string.equals("spikes_back_bottom")) {
            return (ModelRenderer)Reflector.ModelPufferFishBig_ModelRenderers.getValue(pufferFishBigModel, 10);
        }
        if (string.equals("spikes_back_right")) {
            return (ModelRenderer)Reflector.ModelPufferFishBig_ModelRenderers.getValue(pufferFishBigModel, 11);
        }
        return string.equals("spikes_back_left") ? (ModelRenderer)Reflector.ModelPufferFishBig_ModelRenderers.getValue(pufferFishBigModel, 12) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"body", "fin_right", "fin_left", "spikes_front_top", "spikes_middle_top", "spikes_back_top", "spikes_front_right", "spikes_front_left", "spikes_front_bottom", "spikes_middle_bottom", "spikes_back_bottom", "spikes_back_right", "spikes_back_left"};
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
        if (!Reflector.RenderPufferfish_modelBig.exists()) {
            Config.warn("Model field not found: RenderPufferfish.modelBig");
            return null;
        }
        Reflector.RenderPufferfish_modelBig.setValue(pufferfishRenderer, model);
        return pufferfishRenderer;
    }
}

