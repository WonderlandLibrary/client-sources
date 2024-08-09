/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.OcelotRenderer;
import net.minecraft.client.renderer.entity.model.OcelotModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterOcelot
extends ModelAdapter {
    private static Map<String, Integer> mapPartFields = null;

    public ModelAdapterOcelot() {
        super(EntityType.OCELOT, "ocelot", 0.4f);
    }

    protected ModelAdapterOcelot(EntityType entityType, String string, float f) {
        super(entityType, string, f);
    }

    @Override
    public Model makeModel() {
        return new OcelotModel(0.0f);
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof OcelotModel)) {
            return null;
        }
        OcelotModel ocelotModel = (OcelotModel)model;
        Map<String, Integer> map = ModelAdapterOcelot.getMapPartFields();
        if (map.containsKey(string)) {
            int n = map.get(string);
            return (ModelRenderer)Reflector.getFieldValue(ocelotModel, Reflector.ModelOcelot_ModelRenderers, n);
        }
        return null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"back_left_leg", "back_right_leg", "front_left_leg", "front_right_leg", "tail", "tail2", "head", "body"};
    }

    private static Map<String, Integer> getMapPartFields() {
        if (mapPartFields != null) {
            return mapPartFields;
        }
        mapPartFields = new HashMap<String, Integer>();
        mapPartFields.put("back_left_leg", 0);
        mapPartFields.put("back_right_leg", 1);
        mapPartFields.put("front_left_leg", 2);
        mapPartFields.put("front_right_leg", 3);
        mapPartFields.put("tail", 4);
        mapPartFields.put("tail2", 5);
        mapPartFields.put("head", 6);
        mapPartFields.put("body", 7);
        return mapPartFields;
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        OcelotRenderer ocelotRenderer = new OcelotRenderer(entityRendererManager);
        ocelotRenderer.entityModel = (OcelotModel)model;
        ocelotRenderer.shadowSize = f;
        return ocelotRenderer;
    }
}

