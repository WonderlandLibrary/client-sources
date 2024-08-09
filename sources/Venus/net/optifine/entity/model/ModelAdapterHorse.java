/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.HorseRenderer;
import net.minecraft.client.renderer.entity.model.HorseModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterHorse
extends ModelAdapter {
    private static Map<String, Integer> mapParts = ModelAdapterHorse.makeMapParts();
    private static Map<String, Integer> mapPartsNeck = ModelAdapterHorse.makeMapPartsNeck();
    private static Map<String, Integer> mapPartsBody = ModelAdapterHorse.makeMapPartsBody();

    public ModelAdapterHorse() {
        super(EntityType.HORSE, "horse", 0.75f);
    }

    protected ModelAdapterHorse(EntityType entityType, String string, float f) {
        super(entityType, string, f);
    }

    @Override
    public Model makeModel() {
        return new HorseModel(0.0f);
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof HorseModel)) {
            return null;
        }
        HorseModel horseModel = (HorseModel)model;
        if (mapParts.containsKey(string)) {
            int n = mapParts.get(string);
            return (ModelRenderer)Reflector.getFieldValue(horseModel, Reflector.ModelHorse_ModelRenderers, n);
        }
        if (mapPartsNeck.containsKey(string)) {
            ModelRenderer modelRenderer = this.getModelRenderer(horseModel, "neck");
            int n = mapPartsNeck.get(string);
            return modelRenderer.getChild(n);
        }
        if (mapPartsBody.containsKey(string)) {
            ModelRenderer modelRenderer = this.getModelRenderer(horseModel, "body");
            int n = mapPartsBody.get(string);
            return modelRenderer.getChild(n);
        }
        return null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"body", "neck", "back_left_leg", "back_right_leg", "front_left_leg", "front_right_leg", "child_back_left_leg", "child_back_right_leg", "child_front_left_leg", "child_front_right_leg", "tail", "saddle", "head", "mane", "mouth", "left_ear", "right_ear", "left_bit", "right_bit", "left_rein", "right_rein", "headpiece", "noseband"};
    }

    private static Map<String, Integer> makeMapParts() {
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        hashMap.put("body", 0);
        hashMap.put("neck", 1);
        hashMap.put("back_left_leg", 2);
        hashMap.put("back_right_leg", 3);
        hashMap.put("front_left_leg", 4);
        hashMap.put("front_right_leg", 5);
        hashMap.put("child_back_left_leg", 6);
        hashMap.put("child_back_right_leg", 7);
        hashMap.put("child_front_left_leg", 8);
        hashMap.put("child_front_right_leg", 9);
        return hashMap;
    }

    private static Map<String, Integer> makeMapPartsNeck() {
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        hashMap.put("head", 0);
        hashMap.put("mane", 1);
        hashMap.put("mouth", 2);
        hashMap.put("left_ear", 3);
        hashMap.put("right_ear", 4);
        hashMap.put("left_bit", 5);
        hashMap.put("right_bit", 6);
        hashMap.put("left_rein", 7);
        hashMap.put("right_rein", 8);
        hashMap.put("headpiece", 9);
        hashMap.put("noseband", 10);
        return hashMap;
    }

    private static Map<String, Integer> makeMapPartsBody() {
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        hashMap.put("tail", 0);
        hashMap.put("saddle", 1);
        return hashMap;
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        HorseRenderer horseRenderer = new HorseRenderer(entityRendererManager);
        horseRenderer.entityModel = (HorseModel)model;
        horseRenderer.shadowSize = f;
        return horseRenderer;
    }
}

