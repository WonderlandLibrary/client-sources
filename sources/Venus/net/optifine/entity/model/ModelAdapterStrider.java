/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.StriderRenderer;
import net.minecraft.client.renderer.entity.model.StriderModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterStrider
extends ModelAdapter {
    private static Map<String, Integer> mapParts = ModelAdapterStrider.makeMapParts();

    public ModelAdapterStrider() {
        super(EntityType.STRIDER, "strider", 0.5f);
    }

    @Override
    public Model makeModel() {
        return new StriderModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof StriderModel)) {
            return null;
        }
        StriderModel striderModel = (StriderModel)model;
        if (mapParts.containsKey(string)) {
            int n = mapParts.get(string);
            return (ModelRenderer)Reflector.getFieldValue(striderModel, Reflector.ModelStrider_ModelRenderers, n);
        }
        return null;
    }

    @Override
    public String[] getModelRendererNames() {
        return mapParts.keySet().toArray(new String[0]);
    }

    private static Map<String, Integer> makeMapParts() {
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        hashMap.put("right_leg", 0);
        hashMap.put("left_leg", 1);
        hashMap.put("body", 2);
        hashMap.put("hair_right_bottom", 3);
        hashMap.put("hair_right_middle", 4);
        hashMap.put("hair_right_top", 5);
        hashMap.put("hair_left_top", 6);
        hashMap.put("hair_left_middle", 7);
        hashMap.put("hair_left_bottom", 8);
        return hashMap;
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        StriderRenderer striderRenderer = new StriderRenderer(entityRendererManager);
        striderRenderer.entityModel = (StriderModel)model;
        striderRenderer.shadowSize = f;
        return striderRenderer;
    }
}

