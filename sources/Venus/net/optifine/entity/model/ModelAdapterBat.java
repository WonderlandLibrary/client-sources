/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.BatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.BatModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterBat
extends ModelAdapter {
    public ModelAdapterBat() {
        super(EntityType.BAT, "bat", 0.25f);
    }

    @Override
    public Model makeModel() {
        return new BatModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof BatModel)) {
            return null;
        }
        BatModel batModel = (BatModel)model;
        if (string.equals("head")) {
            return (ModelRenderer)Reflector.getFieldValue(batModel, Reflector.ModelBat_ModelRenderers, 0);
        }
        if (string.equals("body")) {
            return (ModelRenderer)Reflector.getFieldValue(batModel, Reflector.ModelBat_ModelRenderers, 1);
        }
        if (string.equals("right_wing")) {
            return (ModelRenderer)Reflector.getFieldValue(batModel, Reflector.ModelBat_ModelRenderers, 2);
        }
        if (string.equals("left_wing")) {
            return (ModelRenderer)Reflector.getFieldValue(batModel, Reflector.ModelBat_ModelRenderers, 3);
        }
        if (string.equals("outer_right_wing")) {
            return (ModelRenderer)Reflector.getFieldValue(batModel, Reflector.ModelBat_ModelRenderers, 4);
        }
        return string.equals("outer_left_wing") ? (ModelRenderer)Reflector.getFieldValue(batModel, Reflector.ModelBat_ModelRenderers, 5) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"head", "body", "right_wing", "left_wing", "outer_right_wing", "outer_left_wing"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        BatRenderer batRenderer = new BatRenderer(entityRendererManager);
        batRenderer.entityModel = (BatModel)model;
        batRenderer.shadowSize = f;
        return batRenderer;
    }
}

