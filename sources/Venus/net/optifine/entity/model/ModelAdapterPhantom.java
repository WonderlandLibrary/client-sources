/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PhantomRenderer;
import net.minecraft.client.renderer.entity.model.PhantomModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterPhantom
extends ModelAdapter {
    private static Map<String, Integer> mapPartFields = null;

    public ModelAdapterPhantom() {
        super(EntityType.PHANTOM, "phantom", 0.75f);
    }

    @Override
    public Model makeModel() {
        return new PhantomModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        int n;
        ModelRenderer modelRenderer;
        if (!(model instanceof PhantomModel)) {
            return null;
        }
        PhantomModel phantomModel = (PhantomModel)model;
        Map<String, Integer> map = ModelAdapterPhantom.getMapPartFields();
        if (map.containsKey(string)) {
            int n2 = map.get(string);
            return (ModelRenderer)Reflector.getFieldValue(phantomModel, Reflector.ModelPhantom_ModelRenderers, n2);
        }
        if (string.equals("head") && (modelRenderer = (ModelRenderer)Reflector.getFieldValue(phantomModel, Reflector.ModelPhantom_ModelRenderers, n = map.get("body").intValue())) != null) {
            return modelRenderer.getChild(1);
        }
        return null;
    }

    @Override
    public String[] getModelRendererNames() {
        return ModelAdapterPhantom.getMapPartFields().keySet().toArray(new String[0]);
    }

    private static Map<String, Integer> getMapPartFields() {
        if (mapPartFields != null) {
            return mapPartFields;
        }
        mapPartFields = new HashMap<String, Integer>();
        mapPartFields.put("body", 0);
        mapPartFields.put("left_wing", 1);
        mapPartFields.put("left_wing_tip", 2);
        mapPartFields.put("right_wing", 3);
        mapPartFields.put("right_wing_tip", 4);
        mapPartFields.put("tail", 5);
        mapPartFields.put("tail2", 6);
        return mapPartFields;
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        PhantomRenderer phantomRenderer = new PhantomRenderer(entityRendererManager);
        phantomRenderer.entityModel = (PhantomModel)model;
        phantomRenderer.shadowSize = f;
        return phantomRenderer;
    }
}

