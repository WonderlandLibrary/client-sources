/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SalmonRenderer;
import net.minecraft.client.renderer.entity.model.SalmonModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterSalmon
extends ModelAdapter {
    public ModelAdapterSalmon() {
        super(EntityType.SALMON, "salmon", 0.3f);
    }

    @Override
    public Model makeModel() {
        return new SalmonModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        ModelRenderer modelRenderer;
        if (!(model instanceof SalmonModel)) {
            return null;
        }
        SalmonModel salmonModel = (SalmonModel)model;
        if (string.equals("body_front")) {
            return (ModelRenderer)Reflector.ModelSalmon_ModelRenderers.getValue(salmonModel, 0);
        }
        if (string.equals("body_back")) {
            return (ModelRenderer)Reflector.ModelSalmon_ModelRenderers.getValue(salmonModel, 1);
        }
        if (string.equals("head")) {
            return (ModelRenderer)Reflector.ModelSalmon_ModelRenderers.getValue(salmonModel, 2);
        }
        if (string.equals("fin_back_1") && (modelRenderer = (ModelRenderer)Reflector.ModelSalmon_ModelRenderers.getValue(salmonModel, 0)) != null) {
            return modelRenderer.getChild(0);
        }
        if (string.equals("fin_back_2") && (modelRenderer = (ModelRenderer)Reflector.ModelSalmon_ModelRenderers.getValue(salmonModel, 1)) != null) {
            return modelRenderer.getChild(1);
        }
        if (string.equals("tail") && (modelRenderer = (ModelRenderer)Reflector.ModelSalmon_ModelRenderers.getValue(salmonModel, 1)) != null) {
            return modelRenderer.getChild(0);
        }
        if (string.equals("fin_right")) {
            return (ModelRenderer)Reflector.ModelSalmon_ModelRenderers.getValue(salmonModel, 3);
        }
        return string.equals("fin_left") ? (ModelRenderer)Reflector.ModelSalmon_ModelRenderers.getValue(salmonModel, 4) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"body_front", "body_back", "head", "fin_back_1", "fin_back_2", "tail", "fin_right", "fin_left"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        SalmonRenderer salmonRenderer = new SalmonRenderer(entityRendererManager);
        salmonRenderer.entityModel = (SalmonModel)model;
        salmonRenderer.shadowSize = f;
        return salmonRenderer;
    }
}

