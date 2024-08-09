/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.EvokerFangsRenderer;
import net.minecraft.client.renderer.entity.model.EvokerFangsModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterEvokerFangs
extends ModelAdapter {
    public ModelAdapterEvokerFangs() {
        super(EntityType.EVOKER_FANGS, "evoker_fangs", 0.0f, new String[]{"evocation_fangs"});
    }

    @Override
    public Model makeModel() {
        return new EvokerFangsModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof EvokerFangsModel)) {
            return null;
        }
        EvokerFangsModel evokerFangsModel = (EvokerFangsModel)model;
        if (string.equals("base")) {
            return (ModelRenderer)Reflector.getFieldValue(evokerFangsModel, Reflector.ModelEvokerFangs_ModelRenderers, 0);
        }
        if (string.equals("upper_jaw")) {
            return (ModelRenderer)Reflector.getFieldValue(evokerFangsModel, Reflector.ModelEvokerFangs_ModelRenderers, 1);
        }
        return string.equals("lower_jaw") ? (ModelRenderer)Reflector.getFieldValue(evokerFangsModel, Reflector.ModelEvokerFangs_ModelRenderers, 2) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"base", "upper_jaw", "lower_jaw"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        EvokerFangsRenderer evokerFangsRenderer = new EvokerFangsRenderer(entityRendererManager);
        if (!Reflector.RenderEvokerFangs_model.exists()) {
            Config.warn("Field not found: RenderEvokerFangs.model");
            return null;
        }
        Reflector.setFieldValue(evokerFangsRenderer, Reflector.RenderEvokerFangs_model, model);
        evokerFangsRenderer.shadowSize = f;
        return evokerFangsRenderer;
    }
}

