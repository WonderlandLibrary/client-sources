/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IronGolemRenderer;
import net.minecraft.client.renderer.entity.model.IronGolemModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterIronGolem
extends ModelAdapter {
    public ModelAdapterIronGolem() {
        super(EntityType.IRON_GOLEM, "iron_golem", 0.5f);
    }

    @Override
    public Model makeModel() {
        return new IronGolemModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof IronGolemModel)) {
            return null;
        }
        IronGolemModel ironGolemModel = (IronGolemModel)model;
        if (string.equals("head")) {
            return (ModelRenderer)Reflector.ModelIronGolem_ModelRenderers.getValue(ironGolemModel, 0);
        }
        if (string.equals("body")) {
            return (ModelRenderer)Reflector.ModelIronGolem_ModelRenderers.getValue(ironGolemModel, 1);
        }
        if (string.equals("right_arm")) {
            return (ModelRenderer)Reflector.ModelIronGolem_ModelRenderers.getValue(ironGolemModel, 2);
        }
        if (string.equals("left_arm")) {
            return (ModelRenderer)Reflector.ModelIronGolem_ModelRenderers.getValue(ironGolemModel, 3);
        }
        if (string.equals("left_leg")) {
            return (ModelRenderer)Reflector.ModelIronGolem_ModelRenderers.getValue(ironGolemModel, 4);
        }
        return string.equals("right_leg") ? (ModelRenderer)Reflector.ModelIronGolem_ModelRenderers.getValue(ironGolemModel, 5) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"head", "body", "right_arm", "left_arm", "left_leg", "right_leg"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        IronGolemRenderer ironGolemRenderer = new IronGolemRenderer(entityRendererManager);
        ironGolemRenderer.entityModel = (IronGolemModel)model;
        ironGolemRenderer.shadowSize = f;
        return ironGolemRenderer;
    }
}

