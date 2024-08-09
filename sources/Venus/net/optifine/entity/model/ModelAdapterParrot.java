/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.ParrotRenderer;
import net.minecraft.client.renderer.entity.model.ParrotModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterParrot
extends ModelAdapter {
    public ModelAdapterParrot() {
        super(EntityType.PARROT, "parrot", 0.3f);
    }

    @Override
    public Model makeModel() {
        return new ParrotModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof ParrotModel)) {
            return null;
        }
        ParrotModel parrotModel = (ParrotModel)model;
        if (string.equals("body")) {
            return (ModelRenderer)Reflector.getFieldValue(parrotModel, Reflector.ModelParrot_ModelRenderers, 0);
        }
        if (string.equals("tail")) {
            return (ModelRenderer)Reflector.getFieldValue(parrotModel, Reflector.ModelParrot_ModelRenderers, 1);
        }
        if (string.equals("left_wing")) {
            return (ModelRenderer)Reflector.getFieldValue(parrotModel, Reflector.ModelParrot_ModelRenderers, 2);
        }
        if (string.equals("right_wing")) {
            return (ModelRenderer)Reflector.getFieldValue(parrotModel, Reflector.ModelParrot_ModelRenderers, 3);
        }
        if (string.equals("head")) {
            return (ModelRenderer)Reflector.getFieldValue(parrotModel, Reflector.ModelParrot_ModelRenderers, 4);
        }
        if (string.equals("left_leg")) {
            return (ModelRenderer)Reflector.getFieldValue(parrotModel, Reflector.ModelParrot_ModelRenderers, 9);
        }
        return string.equals("right_leg") ? (ModelRenderer)Reflector.getFieldValue(parrotModel, Reflector.ModelParrot_ModelRenderers, 10) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"body", "tail", "left_wing", "right_wing", "head", "left_leg", "right_leg"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        ParrotRenderer parrotRenderer = new ParrotRenderer(entityRendererManager);
        parrotRenderer.entityModel = (ParrotModel)model;
        parrotRenderer.shadowSize = f;
        return parrotRenderer;
    }
}

