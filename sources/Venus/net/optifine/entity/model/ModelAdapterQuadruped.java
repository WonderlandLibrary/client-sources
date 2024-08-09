/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public abstract class ModelAdapterQuadruped
extends ModelAdapter {
    public ModelAdapterQuadruped(EntityType entityType, String string, float f) {
        super(entityType, string, f);
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof QuadrupedModel)) {
            return null;
        }
        QuadrupedModel quadrupedModel = (QuadrupedModel)model;
        if (string.equals("head")) {
            return (ModelRenderer)Reflector.ModelQuadruped_ModelRenderers.getValue(quadrupedModel, 0);
        }
        if (string.equals("body")) {
            return (ModelRenderer)Reflector.ModelQuadruped_ModelRenderers.getValue(quadrupedModel, 1);
        }
        if (string.equals("leg1")) {
            return (ModelRenderer)Reflector.ModelQuadruped_ModelRenderers.getValue(quadrupedModel, 2);
        }
        if (string.equals("leg2")) {
            return (ModelRenderer)Reflector.ModelQuadruped_ModelRenderers.getValue(quadrupedModel, 3);
        }
        if (string.equals("leg3")) {
            return (ModelRenderer)Reflector.ModelQuadruped_ModelRenderers.getValue(quadrupedModel, 4);
        }
        return string.equals("leg4") ? (ModelRenderer)Reflector.ModelQuadruped_ModelRenderers.getValue(quadrupedModel, 5) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"head", "body", "leg1", "leg2", "leg3", "leg4"};
    }
}

