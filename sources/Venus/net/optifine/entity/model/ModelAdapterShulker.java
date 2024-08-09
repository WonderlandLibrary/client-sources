/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.ShulkerRenderer;
import net.minecraft.client.renderer.entity.model.ShulkerModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterShulker
extends ModelAdapter {
    public ModelAdapterShulker() {
        super(EntityType.SHULKER, "shulker", 0.0f);
    }

    @Override
    public Model makeModel() {
        return new ShulkerModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof ShulkerModel)) {
            return null;
        }
        ShulkerModel shulkerModel = (ShulkerModel)model;
        if (string.equals("base")) {
            return (ModelRenderer)Reflector.ModelShulker_ModelRenderers.getValue(shulkerModel, 0);
        }
        if (string.equals("lid")) {
            return (ModelRenderer)Reflector.ModelShulker_ModelRenderers.getValue(shulkerModel, 1);
        }
        return string.equals("head") ? (ModelRenderer)Reflector.ModelShulker_ModelRenderers.getValue(shulkerModel, 2) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"base", "lid", "head"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        ShulkerRenderer shulkerRenderer = new ShulkerRenderer(entityRendererManager);
        shulkerRenderer.entityModel = (ShulkerModel)model;
        shulkerRenderer.shadowSize = f;
        return shulkerRenderer;
    }
}

