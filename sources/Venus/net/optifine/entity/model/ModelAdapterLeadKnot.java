/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LeashKnotRenderer;
import net.minecraft.client.renderer.entity.model.LeashKnotModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterLeadKnot
extends ModelAdapter {
    public ModelAdapterLeadKnot() {
        super(EntityType.LEASH_KNOT, "lead_knot", 0.0f);
    }

    @Override
    public Model makeModel() {
        return new LeashKnotModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof LeashKnotModel)) {
            return null;
        }
        LeashKnotModel leashKnotModel = (LeashKnotModel)model;
        return string.equals("knot") ? (ModelRenderer)Reflector.ModelLeashKnot_knotRenderer.getValue(leashKnotModel) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"knot"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        LeashKnotRenderer leashKnotRenderer = new LeashKnotRenderer(entityRendererManager);
        if (!Reflector.RenderLeashKnot_leashKnotModel.exists()) {
            Config.warn("Field not found: RenderLeashKnot.leashKnotModel");
            return null;
        }
        Reflector.setFieldValue(leashKnotRenderer, Reflector.RenderLeashKnot_leashKnotModel, model);
        leashKnotRenderer.shadowSize = f;
        return leashKnotRenderer;
    }
}

