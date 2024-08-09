/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.RavagerRenderer;
import net.minecraft.client.renderer.entity.model.RavagerModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterRavager
extends ModelAdapter {
    private static Map<String, Integer> mapPartFields = null;

    public ModelAdapterRavager() {
        super(EntityType.RAVAGER, "ravager", 1.1f);
    }

    @Override
    public Model makeModel() {
        return new RavagerModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof RavagerModel)) {
            return null;
        }
        RavagerModel ravagerModel = (RavagerModel)model;
        Map<String, Integer> map = ModelAdapterRavager.getMapPartFields();
        if (map.containsKey(string)) {
            int n = map.get(string);
            return (ModelRenderer)Reflector.getFieldValue(ravagerModel, Reflector.ModelRavager_ModelRenderers, n);
        }
        return null;
    }

    @Override
    public String[] getModelRendererNames() {
        return ModelAdapterRavager.getMapPartFields().keySet().toArray(new String[0]);
    }

    private static Map<String, Integer> getMapPartFields() {
        if (mapPartFields != null) {
            return mapPartFields;
        }
        mapPartFields = new HashMap<String, Integer>();
        mapPartFields.put("head", 0);
        mapPartFields.put("jaw", 1);
        mapPartFields.put("body", 2);
        mapPartFields.put("leg1", 3);
        mapPartFields.put("leg2", 4);
        mapPartFields.put("leg3", 5);
        mapPartFields.put("leg4", 6);
        mapPartFields.put("neck", 7);
        return mapPartFields;
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        RavagerRenderer ravagerRenderer = new RavagerRenderer(entityRendererManager);
        ravagerRenderer.entityModel = (RavagerModel)model;
        ravagerRenderer.shadowSize = f;
        return ravagerRenderer;
    }
}

