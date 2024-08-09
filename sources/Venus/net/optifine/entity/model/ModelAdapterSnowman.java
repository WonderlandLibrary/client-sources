/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SnowManRenderer;
import net.minecraft.client.renderer.entity.model.SnowManModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterSnowman
extends ModelAdapter {
    public ModelAdapterSnowman() {
        super(EntityType.SNOW_GOLEM, "snow_golem", 0.5f);
    }

    @Override
    public Model makeModel() {
        return new SnowManModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof SnowManModel)) {
            return null;
        }
        SnowManModel snowManModel = (SnowManModel)model;
        if (string.equals("body")) {
            return (ModelRenderer)Reflector.ModelSnowman_ModelRenderers.getValue(snowManModel, 0);
        }
        if (string.equals("body_bottom")) {
            return (ModelRenderer)Reflector.ModelSnowman_ModelRenderers.getValue(snowManModel, 1);
        }
        if (string.equals("head")) {
            return (ModelRenderer)Reflector.ModelSnowman_ModelRenderers.getValue(snowManModel, 2);
        }
        if (string.equals("right_hand")) {
            return (ModelRenderer)Reflector.ModelSnowman_ModelRenderers.getValue(snowManModel, 3);
        }
        return string.equals("left_hand") ? (ModelRenderer)Reflector.ModelSnowman_ModelRenderers.getValue(snowManModel, 4) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"body", "body_bottom", "head", "right_hand", "left_hand"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        SnowManRenderer snowManRenderer = new SnowManRenderer(entityRendererManager);
        snowManRenderer.entityModel = (SnowManModel)model;
        snowManRenderer.shadowSize = f;
        return snowManRenderer;
    }
}

