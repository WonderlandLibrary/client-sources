/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.HoglinRenderer;
import net.minecraft.client.renderer.entity.model.BoarModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterHoglin
extends ModelAdapter {
    private static Map<String, Integer> mapParts = ModelAdapterHoglin.makeMapParts();

    public ModelAdapterHoglin() {
        super(EntityType.HOGLIN, "hoglin", 0.7f);
    }

    public ModelAdapterHoglin(EntityType entityType, String string, float f) {
        super(entityType, string, f);
    }

    @Override
    public Model makeModel() {
        return new BoarModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof BoarModel)) {
            return null;
        }
        BoarModel boarModel = (BoarModel)model;
        if (mapParts.containsKey(string)) {
            int n = mapParts.get(string);
            return (ModelRenderer)Reflector.getFieldValue(boarModel, Reflector.ModelBoar_ModelRenderers, n);
        }
        return null;
    }

    @Override
    public String[] getModelRendererNames() {
        return mapParts.keySet().toArray(new String[0]);
    }

    private static Map<String, Integer> makeMapParts() {
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        hashMap.put("head", 0);
        hashMap.put("right_ear", 1);
        hashMap.put("left_ear", 2);
        hashMap.put("body", 3);
        hashMap.put("front_right_leg", 4);
        hashMap.put("front_left_leg", 5);
        hashMap.put("back_right_leg", 6);
        hashMap.put("back_left_leg", 7);
        hashMap.put("mane", 8);
        return hashMap;
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        HoglinRenderer hoglinRenderer = new HoglinRenderer(entityRendererManager);
        hoglinRenderer.entityModel = (BoarModel)model;
        hoglinRenderer.shadowSize = f;
        return hoglinRenderer;
    }
}

