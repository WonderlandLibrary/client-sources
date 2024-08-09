/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.client.renderer.entity.model.WolfModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterWolf
extends ModelAdapter {
    public ModelAdapterWolf() {
        super(EntityType.WOLF, "wolf", 0.5f);
    }

    @Override
    public Model makeModel() {
        return new WolfModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof WolfModel)) {
            return null;
        }
        WolfModel wolfModel = (WolfModel)model;
        if (string.equals("head")) {
            return (ModelRenderer)Reflector.ModelWolf_ModelRenderers.getValue(wolfModel, 0);
        }
        if (string.equals("body")) {
            return (ModelRenderer)Reflector.ModelWolf_ModelRenderers.getValue(wolfModel, 2);
        }
        if (string.equals("leg1")) {
            return (ModelRenderer)Reflector.ModelWolf_ModelRenderers.getValue(wolfModel, 3);
        }
        if (string.equals("leg2")) {
            return (ModelRenderer)Reflector.ModelWolf_ModelRenderers.getValue(wolfModel, 4);
        }
        if (string.equals("leg3")) {
            return (ModelRenderer)Reflector.ModelWolf_ModelRenderers.getValue(wolfModel, 5);
        }
        if (string.equals("leg4")) {
            return (ModelRenderer)Reflector.ModelWolf_ModelRenderers.getValue(wolfModel, 6);
        }
        if (string.equals("tail")) {
            return (ModelRenderer)Reflector.ModelWolf_ModelRenderers.getValue(wolfModel, 7);
        }
        return string.equals("mane") ? (ModelRenderer)Reflector.ModelWolf_ModelRenderers.getValue(wolfModel, 9) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"head", "body", "leg1", "leg2", "leg3", "leg4", "tail", "mane"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        WolfRenderer wolfRenderer = new WolfRenderer(entityRendererManager);
        wolfRenderer.entityModel = (WolfModel)model;
        wolfRenderer.shadowSize = f;
        return wolfRenderer;
    }
}

