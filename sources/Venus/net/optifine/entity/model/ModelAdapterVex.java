/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.VexRenderer;
import net.minecraft.client.renderer.entity.model.VexModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterBiped;
import net.optifine.reflect.Reflector;

public class ModelAdapterVex
extends ModelAdapterBiped {
    public ModelAdapterVex() {
        super(EntityType.VEX, "vex", 0.3f);
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof VexModel)) {
            return null;
        }
        ModelRenderer modelRenderer = super.getModelRenderer(model, string);
        if (modelRenderer != null) {
            return modelRenderer;
        }
        VexModel vexModel = (VexModel)model;
        if (string.equals("left_wing")) {
            return (ModelRenderer)Reflector.getFieldValue(vexModel, Reflector.ModelVex_leftWing);
        }
        return string.equals("right_wing") ? (ModelRenderer)Reflector.getFieldValue(vexModel, Reflector.ModelVex_rightWing) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        Object[] objectArray = super.getModelRendererNames();
        return (String[])Config.addObjectsToArray(objectArray, new String[]{"left_wing", "right_wing"});
    }

    @Override
    public Model makeModel() {
        return new VexModel();
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        VexRenderer vexRenderer = new VexRenderer(entityRendererManager);
        vexRenderer.entityModel = (VexModel)model;
        vexRenderer.shadowSize = f;
        return vexRenderer;
    }
}

