/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.RabbitRenderer;
import net.minecraft.client.renderer.entity.model.RabbitModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterRabbit
extends ModelAdapter {
    private static Map<String, Integer> mapPartFields = null;

    public ModelAdapterRabbit() {
        super(EntityType.RABBIT, "rabbit", 0.3f);
    }

    @Override
    public Model makeModel() {
        return new RabbitModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof RabbitModel)) {
            return null;
        }
        RabbitModel rabbitModel = (RabbitModel)model;
        Map<String, Integer> map = ModelAdapterRabbit.getMapPartFields();
        if (map.containsKey(string)) {
            int n = map.get(string);
            return (ModelRenderer)Reflector.getFieldValue(rabbitModel, Reflector.ModelRabbit_ModelRenderers, n);
        }
        return null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"left_foot", "right_foot", "left_thigh", "right_thigh", "body", "left_arm", "right_arm", "head", "right_ear", "left_ear", "tail", "nose"};
    }

    private static Map<String, Integer> getMapPartFields() {
        if (mapPartFields != null) {
            return mapPartFields;
        }
        mapPartFields = new HashMap<String, Integer>();
        mapPartFields.put("left_foot", 0);
        mapPartFields.put("right_foot", 1);
        mapPartFields.put("left_thigh", 2);
        mapPartFields.put("right_thigh", 3);
        mapPartFields.put("body", 4);
        mapPartFields.put("left_arm", 5);
        mapPartFields.put("right_arm", 6);
        mapPartFields.put("head", 7);
        mapPartFields.put("right_ear", 8);
        mapPartFields.put("left_ear", 9);
        mapPartFields.put("tail", 10);
        mapPartFields.put("nose", 11);
        return mapPartFields;
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        RabbitRenderer rabbitRenderer = new RabbitRenderer(entityRendererManager);
        rabbitRenderer.entityModel = (RabbitModel)model;
        rabbitRenderer.shadowSize = f;
        return rabbitRenderer;
    }
}

