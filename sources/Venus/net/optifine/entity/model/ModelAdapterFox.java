/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.FoxRenderer;
import net.minecraft.client.renderer.entity.model.FoxModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterFox
extends ModelAdapter {
    private static Map<String, Integer> mapPartFields = null;

    public ModelAdapterFox() {
        super(EntityType.FOX, "fox", 0.4f);
    }

    @Override
    public Model makeModel() {
        return new FoxModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof FoxModel)) {
            return null;
        }
        FoxModel foxModel = (FoxModel)model;
        Map<String, Integer> map = ModelAdapterFox.getMapPartFields();
        if (map.containsKey(string)) {
            int n = map.get(string);
            return (ModelRenderer)Reflector.getFieldValue(foxModel, Reflector.ModelFox_ModelRenderers, n);
        }
        return null;
    }

    @Override
    public String[] getModelRendererNames() {
        return ModelAdapterFox.getMapPartFields().keySet().toArray(new String[0]);
    }

    private static Map<String, Integer> getMapPartFields() {
        if (mapPartFields != null) {
            return mapPartFields;
        }
        mapPartFields = new HashMap<String, Integer>();
        mapPartFields.put("head", 0);
        mapPartFields.put("body", 4);
        mapPartFields.put("leg1", 5);
        mapPartFields.put("leg2", 6);
        mapPartFields.put("leg3", 7);
        mapPartFields.put("leg4", 8);
        mapPartFields.put("tail", 9);
        return mapPartFields;
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        FoxRenderer foxRenderer = new FoxRenderer(entityRendererManager);
        foxRenderer.entityModel = (FoxModel)model;
        foxRenderer.shadowSize = f;
        return foxRenderer;
    }
}

