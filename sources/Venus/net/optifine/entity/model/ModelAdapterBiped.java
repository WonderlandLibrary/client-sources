/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.ModelAdapter;

public abstract class ModelAdapterBiped
extends ModelAdapter {
    public ModelAdapterBiped(EntityType entityType, String string, float f) {
        super(entityType, string, f);
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof BipedModel)) {
            return null;
        }
        BipedModel bipedModel = (BipedModel)model;
        if (string.equals("head")) {
            return bipedModel.bipedHead;
        }
        if (string.equals("headwear")) {
            return bipedModel.bipedHeadwear;
        }
        if (string.equals("body")) {
            return bipedModel.bipedBody;
        }
        if (string.equals("left_arm")) {
            return bipedModel.bipedLeftArm;
        }
        if (string.equals("right_arm")) {
            return bipedModel.bipedRightArm;
        }
        if (string.equals("left_leg")) {
            return bipedModel.bipedLeftLeg;
        }
        return string.equals("right_leg") ? bipedModel.bipedRightLeg : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"head", "headwear", "body", "left_arm", "right_arm", "left_leg", "right_leg"};
    }
}

