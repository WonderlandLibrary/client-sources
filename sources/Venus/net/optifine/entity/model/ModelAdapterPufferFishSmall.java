/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PufferfishRenderer;
import net.minecraft.client.renderer.entity.model.PufferFishSmallModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterPufferFishSmall
extends ModelAdapter {
    public ModelAdapterPufferFishSmall() {
        super(EntityType.PUFFERFISH, "puffer_fish_small", 0.2f);
    }

    @Override
    public Model makeModel() {
        return new PufferFishSmallModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof PufferFishSmallModel)) {
            return null;
        }
        PufferFishSmallModel pufferFishSmallModel = (PufferFishSmallModel)model;
        if (string.equals("body")) {
            return (ModelRenderer)Reflector.ModelPufferFishSmall_ModelRenderers.getValue(pufferFishSmallModel, 0);
        }
        if (string.equals("eye_right")) {
            return (ModelRenderer)Reflector.ModelPufferFishSmall_ModelRenderers.getValue(pufferFishSmallModel, 1);
        }
        if (string.equals("eye_left")) {
            return (ModelRenderer)Reflector.ModelPufferFishSmall_ModelRenderers.getValue(pufferFishSmallModel, 2);
        }
        if (string.equals("fin_right")) {
            return (ModelRenderer)Reflector.ModelPufferFishSmall_ModelRenderers.getValue(pufferFishSmallModel, 3);
        }
        if (string.equals("fin_left")) {
            return (ModelRenderer)Reflector.ModelPufferFishSmall_ModelRenderers.getValue(pufferFishSmallModel, 4);
        }
        return string.equals("tail") ? (ModelRenderer)Reflector.ModelPufferFishSmall_ModelRenderers.getValue(pufferFishSmallModel, 5) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"body", "eye_right", "eye_left", "tail", "fin_right", "fin_left"};
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
        if (!Reflector.RenderPufferfish_modelSmall.exists()) {
            Config.warn("Model field not found: RenderPufferfish.modelSmall");
            return null;
        }
        Reflector.RenderPufferfish_modelSmall.setValue(pufferfishRenderer, model);
        return pufferfishRenderer;
    }
}

