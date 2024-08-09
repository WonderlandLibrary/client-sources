/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.BeeRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.BeeModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterBee
extends ModelAdapter {
    private static Map<String, Integer> mapParts = ModelAdapterBee.makeMapParts();

    public ModelAdapterBee() {
        super(EntityType.BEE, "bee", 0.4f);
    }

    @Override
    public Model makeModel() {
        return new BeeModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof BeeModel)) {
            return null;
        }
        BeeModel beeModel = (BeeModel)model;
        if (mapParts.containsKey(string)) {
            int n = mapParts.get(string);
            return (ModelRenderer)Reflector.getFieldValue(beeModel, Reflector.ModelBee_ModelRenderers, n);
        }
        return null;
    }

    @Override
    public String[] getModelRendererNames() {
        return mapParts.keySet().toArray(new String[0]);
    }

    private static Map<String, Integer> makeMapParts() {
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        hashMap.put("body", 0);
        hashMap.put("torso", 1);
        hashMap.put("right_wing", 2);
        hashMap.put("left_wing", 3);
        hashMap.put("front_legs", 4);
        hashMap.put("middle_legs", 5);
        hashMap.put("back_legs", 6);
        hashMap.put("stinger", 7);
        hashMap.put("left_antenna", 8);
        hashMap.put("right_antenna", 9);
        return hashMap;
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        BeeRenderer beeRenderer = new BeeRenderer(entityRendererManager);
        beeRenderer.entityModel = (BeeModel)model;
        beeRenderer.shadowSize = f;
        return beeRenderer;
    }
}

