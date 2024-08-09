/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.BoatModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.ModelRendererUtils;
import net.optifine.reflect.Reflector;

public class ModelAdapterBoat
extends ModelAdapter {
    public ModelAdapterBoat() {
        super(EntityType.BOAT, "boat", 0.5f);
    }

    @Override
    public Model makeModel() {
        return new BoatModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof BoatModel)) {
            return null;
        }
        BoatModel boatModel = (BoatModel)model;
        Iterable iterable = boatModel.getParts();
        if (iterable != null) {
            if (string.equals("bottom")) {
                return ModelRendererUtils.getModelRenderer((ImmutableList<ModelRenderer>)iterable, 0);
            }
            if (string.equals("back")) {
                return ModelRendererUtils.getModelRenderer((ImmutableList<ModelRenderer>)iterable, 1);
            }
            if (string.equals("front")) {
                return ModelRendererUtils.getModelRenderer((ImmutableList<ModelRenderer>)iterable, 2);
            }
            if (string.equals("right")) {
                return ModelRendererUtils.getModelRenderer((ImmutableList<ModelRenderer>)iterable, 3);
            }
            if (string.equals("left")) {
                return ModelRendererUtils.getModelRenderer((ImmutableList<ModelRenderer>)iterable, 4);
            }
            if (string.equals("paddle_left")) {
                return ModelRendererUtils.getModelRenderer((ImmutableList<ModelRenderer>)iterable, 5);
            }
            if (string.equals("paddle_right")) {
                return ModelRendererUtils.getModelRenderer((ImmutableList<ModelRenderer>)iterable, 6);
            }
        }
        return string.equals("bottom_no_water") ? boatModel.func_228245_c_() : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"bottom", "back", "front", "right", "left", "paddle_left", "paddle_right", "bottom_no_water"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        BoatRenderer boatRenderer = new BoatRenderer(entityRendererManager);
        if (!Reflector.RenderBoat_modelBoat.exists()) {
            Config.warn("Field not found: RenderBoat.modelBoat");
            return null;
        }
        Reflector.setFieldValue(boatRenderer, Reflector.RenderBoat_modelBoat, model);
        boatRenderer.shadowSize = f;
        return boatRenderer;
    }
}

