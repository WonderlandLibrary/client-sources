/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.CodRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.CodModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterCod
extends ModelAdapter {
    public ModelAdapterCod() {
        super(EntityType.COD, "cod", 0.3f);
    }

    @Override
    public Model makeModel() {
        return new CodModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof CodModel)) {
            return null;
        }
        CodModel codModel = (CodModel)model;
        if (string.equals("body")) {
            return (ModelRenderer)Reflector.ModelCod_ModelRenderers.getValue(codModel, 0);
        }
        if (string.equals("fin_back")) {
            return (ModelRenderer)Reflector.ModelCod_ModelRenderers.getValue(codModel, 1);
        }
        if (string.equals("head")) {
            return (ModelRenderer)Reflector.ModelCod_ModelRenderers.getValue(codModel, 2);
        }
        if (string.equals("nose")) {
            return (ModelRenderer)Reflector.ModelCod_ModelRenderers.getValue(codModel, 3);
        }
        if (string.equals("fin_right")) {
            return (ModelRenderer)Reflector.ModelCod_ModelRenderers.getValue(codModel, 4);
        }
        if (string.equals("fin_left")) {
            return (ModelRenderer)Reflector.ModelCod_ModelRenderers.getValue(codModel, 5);
        }
        return string.equals("tail") ? (ModelRenderer)Reflector.ModelCod_ModelRenderers.getValue(codModel, 6) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"body", "fin_back", "head", "nose", "fin_right", "fin_left", "tail"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        CodRenderer codRenderer = new CodRenderer(entityRendererManager);
        codRenderer.entityModel = (CodModel)model;
        codRenderer.shadowSize = f;
        return codRenderer;
    }
}

