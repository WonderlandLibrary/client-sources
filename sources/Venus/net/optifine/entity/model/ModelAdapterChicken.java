/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.ChickenModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterChicken
extends ModelAdapter {
    public ModelAdapterChicken() {
        super(EntityType.CHICKEN, "chicken", 0.3f);
    }

    @Override
    public Model makeModel() {
        return new ChickenModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof ChickenModel)) {
            return null;
        }
        ChickenModel chickenModel = (ChickenModel)model;
        if (string.equals("head")) {
            return (ModelRenderer)Reflector.ModelChicken_ModelRenderers.getValue(chickenModel, 0);
        }
        if (string.equals("body")) {
            return (ModelRenderer)Reflector.ModelChicken_ModelRenderers.getValue(chickenModel, 1);
        }
        if (string.equals("right_leg")) {
            return (ModelRenderer)Reflector.ModelChicken_ModelRenderers.getValue(chickenModel, 2);
        }
        if (string.equals("left_leg")) {
            return (ModelRenderer)Reflector.ModelChicken_ModelRenderers.getValue(chickenModel, 3);
        }
        if (string.equals("right_wing")) {
            return (ModelRenderer)Reflector.ModelChicken_ModelRenderers.getValue(chickenModel, 4);
        }
        if (string.equals("left_wing")) {
            return (ModelRenderer)Reflector.ModelChicken_ModelRenderers.getValue(chickenModel, 5);
        }
        if (string.equals("bill")) {
            return (ModelRenderer)Reflector.ModelChicken_ModelRenderers.getValue(chickenModel, 6);
        }
        return string.equals("chin") ? (ModelRenderer)Reflector.ModelChicken_ModelRenderers.getValue(chickenModel, 7) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"head", "body", "right_leg", "left_leg", "right_wing", "left_wing", "bill", "chin"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        ChickenRenderer chickenRenderer = new ChickenRenderer(entityRendererManager);
        chickenRenderer.entityModel = (ChickenModel)model;
        chickenRenderer.shadowSize = f;
        return chickenRenderer;
    }
}

