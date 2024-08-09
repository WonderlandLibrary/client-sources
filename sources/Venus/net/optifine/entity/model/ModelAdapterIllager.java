/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.renderer.entity.model.IllagerModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public abstract class ModelAdapterIllager
extends ModelAdapter {
    public ModelAdapterIllager(EntityType entityType, String string, float f) {
        super(entityType, string, f);
    }

    public ModelAdapterIllager(EntityType entityType, String string, float f, String[] stringArray) {
        super(entityType, string, f, stringArray);
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        ModelRenderer modelRenderer;
        if (!(model instanceof IllagerModel)) {
            return null;
        }
        IllagerModel illagerModel = (IllagerModel)model;
        if (string.equals("head")) {
            return (ModelRenderer)Reflector.ModelIllager_ModelRenderers.getValue(illagerModel, 0);
        }
        if (string.equals("hat")) {
            return (ModelRenderer)Reflector.ModelIllager_ModelRenderers.getValue(illagerModel, 1);
        }
        if (string.equals("body")) {
            return (ModelRenderer)Reflector.ModelIllager_ModelRenderers.getValue(illagerModel, 2);
        }
        if (string.equals("arms")) {
            return (ModelRenderer)Reflector.ModelIllager_ModelRenderers.getValue(illagerModel, 3);
        }
        if (string.equals("right_leg")) {
            return (ModelRenderer)Reflector.ModelIllager_ModelRenderers.getValue(illagerModel, 4);
        }
        if (string.equals("left_leg")) {
            return (ModelRenderer)Reflector.ModelIllager_ModelRenderers.getValue(illagerModel, 5);
        }
        if (string.equals("nose") && (modelRenderer = (ModelRenderer)Reflector.ModelIllager_ModelRenderers.getValue(illagerModel, 0)) != null) {
            return modelRenderer.getChild(0);
        }
        if (string.equals("right_arm")) {
            return (ModelRenderer)Reflector.ModelIllager_ModelRenderers.getValue(illagerModel, 6);
        }
        return string.equals("left_arm") ? (ModelRenderer)Reflector.ModelIllager_ModelRenderers.getValue(illagerModel, 7) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"head", "hat", "body", "arms", "right_leg", "left_leg", "nose", "right_arm", "left_arm"};
    }
}

