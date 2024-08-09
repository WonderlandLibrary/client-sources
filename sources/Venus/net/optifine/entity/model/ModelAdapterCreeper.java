/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.CreeperModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterCreeper
extends ModelAdapter {
    public ModelAdapterCreeper() {
        super(EntityType.CREEPER, "creeper", 0.5f);
    }

    @Override
    public Model makeModel() {
        return new CreeperModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof CreeperModel)) {
            return null;
        }
        CreeperModel creeperModel = (CreeperModel)model;
        if (string.equals("head")) {
            return (ModelRenderer)Reflector.ModelCreeper_ModelRenderers.getValue(creeperModel, 0);
        }
        if (string.equals("armor")) {
            return (ModelRenderer)Reflector.ModelCreeper_ModelRenderers.getValue(creeperModel, 1);
        }
        if (string.equals("body")) {
            return (ModelRenderer)Reflector.ModelCreeper_ModelRenderers.getValue(creeperModel, 2);
        }
        if (string.equals("leg1")) {
            return (ModelRenderer)Reflector.ModelCreeper_ModelRenderers.getValue(creeperModel, 3);
        }
        if (string.equals("leg2")) {
            return (ModelRenderer)Reflector.ModelCreeper_ModelRenderers.getValue(creeperModel, 4);
        }
        if (string.equals("leg3")) {
            return (ModelRenderer)Reflector.ModelCreeper_ModelRenderers.getValue(creeperModel, 5);
        }
        return string.equals("leg4") ? (ModelRenderer)Reflector.ModelCreeper_ModelRenderers.getValue(creeperModel, 6) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"head", "armor", "body", "leg1", "leg2", "leg3", "leg4"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        CreeperRenderer creeperRenderer = new CreeperRenderer(entityRendererManager);
        creeperRenderer.entityModel = (CreeperModel)model;
        creeperRenderer.shadowSize = f;
        return creeperRenderer;
    }
}

